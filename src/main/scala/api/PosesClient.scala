package icfpc21.classified
package api

import icfpc21.classified.model.{Edge, Figure, Hole, Point, Problem}
import java.net.{HttpURLConnection, URI}
import java.net.http.{HttpClient, HttpRequest, HttpResponse}

import io.circe._
import io.circe.generic.auto._
import io.circe.syntax._

trait PosesClient {

  def getProblem(problemId: Int): Problem
  def submitSolution(problemId: Int, resultFigure: Figure): Unit
}

object PosesClient {

  class Live(serverUrl: String, apiKey: String) extends PosesClient {

    private val client = HttpClient.newBuilder().build()

    override def getProblem(problemId: Int): Problem = {
      val request = HttpRequest.newBuilder
        .uri(URI.create(s"$serverUrl/api/problems/$problemId"))
        .header("Authorization", s"Bearer $apiKey")
        .version(HttpClient.Version.HTTP_1_1)
        .GET()
        .build()
      val response = client.send(request, HttpResponse.BodyHandlers.ofString())
      val status = response.statusCode()
      if (status != HttpURLConnection.HTTP_OK) {
        println("Unexpected server response:")
        println("HTTP code: " + status)
        println("Response body: " + response.body)
        System.exit(2)
      }
      Json.fromString(response.body()).as[ProblemDto].map { result =>
        Problem(
          hole = Hole(result.hole.map(point => Point(point.head, point.last))),
          figure = Figure(
            vertices = result.figure.vertices.map(point => Point(point.head, point.last)),
            edges = result.figure.edges.map(edge => Edge(edge.head, edge.last))
          ),
          epsilon = result.epsilon
        )
      }.getOrElse(throw new IllegalStateException("Parsing error"))
    }

    override def submitSolution(problemId: Int, resultFigure: Figure): Unit = {
      val body = SolutionDto(
        vertices = resultFigure.vertices.map(p => Seq(p.x, p.y))
      ).asJson
      val request = HttpRequest.newBuilder
        .uri(URI.create(s"$serverUrl/api/problems/$problemId/solutions"))
        .header("Authorization", s"Bearer $apiKey")
        .version(HttpClient.Version.HTTP_1_1)
        .POST(HttpRequest.BodyPublishers.ofString(body.asString.getOrElse("")))
        .build()
      val response = client.send(request, HttpResponse.BodyHandlers.discarding())
      val status = response.statusCode()
      if (status != HttpURLConnection.HTTP_OK) {
        println("Unexpected server response:")
        println("HTTP code: " + status)
        System.exit(2)
      }
    }
  }

  case class FigureDto(edges: Seq[Seq[Int]], vertices: Seq[Seq[Int]])
  case class ProblemDto(hole: Seq[Seq[Int]], figure: FigureDto, epsilon: Int)

  case class SolutionDto(vertices: Seq[Seq[Int]])
}
