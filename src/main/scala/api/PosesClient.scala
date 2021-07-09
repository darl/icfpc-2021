package icfpc21.classified
package api

import icfpc21.classified.model._
import java.net.{HttpURLConnection, URI}
import java.net.http.{HttpClient, HttpRequest, HttpResponse}

import io.circe._
import io.circe.syntax._
import io.circe.parser._
import io.circe.generic.semiauto._

trait PosesClient {

  def getProblem(problemId: Int): Problem
  def submitSolution(problemId: Int, solution: Solution): Unit
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
      val body = response.body
      decode[ProblemDto](body)
        .map { result =>
          val figurePoints = result.figure.vertices
            .map(point => Vector(point.head, point.last))
          Problem(
            hole =
              Hole(result.hole.map(point => Vector(point.head, point.last))),
            figure = Figure(
              vertices = figurePoints,
              edges = result.figure.edges.map(edge =>
                Edge(
                  edge.head,
                  edge.last,
                  originSquareDistance = (figurePoints(
                    edge.head
                  ) - figurePoints(edge.last)).squaredLength
                )
              )
            ),
            epsilon = result.epsilon
          )
        }
        .fold(fail => throw new RuntimeException(fail), res => res)
    }

    override def submitSolution(problemId: Int, solution: Solution): Unit = {
      val body = SolutionDto(
        vertices = solution.vertices.map(p => Seq(p.x, p.y))
      ).asJson
      val request = HttpRequest.newBuilder
        .uri(URI.create(s"$serverUrl/api/problems/$problemId/solutions"))
        .header("Authorization", s"Bearer $apiKey")
        .version(HttpClient.Version.HTTP_1_1)
        .POST(HttpRequest.BodyPublishers.ofString(body.asString.getOrElse("")))
        .build()
      val response =
        client.send(request, HttpResponse.BodyHandlers.discarding())
      val status = response.statusCode()
      if (status != HttpURLConnection.HTTP_OK) {
        println("Unexpected server response:")
        println("HTTP code: " + status)
        System.exit(2)
      }
    }
  }

  case class FigureDto(edges: List[List[Int]], vertices: List[List[Int]])
  case class ProblemDto(hole: List[List[Int]], figure: FigureDto, epsilon: Int)

  case class SolutionDto(vertices: Seq[Seq[Int]])

  implicit val figureDecoder: Decoder[FigureDto] = deriveDecoder[FigureDto]
  implicit val problemDecoder: Decoder[ProblemDto] = deriveDecoder[ProblemDto]

  implicit val solutionEncoder: Encoder[SolutionDto] =
    deriveEncoder[SolutionDto]
}