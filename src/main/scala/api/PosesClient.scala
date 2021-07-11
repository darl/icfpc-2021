package icfpc21.classified
package api

import model._

import io.circe._
import io.circe.generic.semiauto._
import io.circe.parser._
import io.circe.syntax._
import org.jsoup.Jsoup

import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.net.{HttpURLConnection, URI}
import java.time.Instant
import scala.jdk.CollectionConverters._
import scala.util.Try

trait PosesClient {

  def getProblem(problemId: Int): Problem
  def submitSolution(problemId: Int, solution: Solution): Unit
  def getLastSubmission(problemId: Int): Option[Submission]
}

object PosesClient {

  class Live(serverUrl: String, apiKey: String, session: String) extends PosesClient {

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
            .toVector
          Problem(
            hole = Hole(result.hole.map(point => Vector(point.head, point.last))),
            figure = Figure(
              vertices = figurePoints,
              edges = Edges(result.figure.edges.map(edge => Edge(edge.head, edge.last)))
            ),
            bonuses = result.bonuses.map(b => BonusPoint(center = Vector(b.position.head, b.position.last))),
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
        .POST(HttpRequest.BodyPublishers.ofString(body.toString))
        .build()
      val response =
        client.send(request, HttpResponse.BodyHandlers.discarding())
      val status = response.statusCode()
      if (status != HttpURLConnection.HTTP_OK) {
        sys.error("Unexpected server response: " + "HTTP code: " + status)
      }
    }

    def getLastSubmission(problemId: Int): Option[Submission] = {
      val doc = Jsoup
        .connect(s"$serverUrl/problems/$problemId")
        .cookie("session", session)
        .timeout(4000)
        .get()

      doc
        .body()
        .getElementsByTag("table")
        .first()
        .getElementsByTag("tr")
        .asScala
        .drop(1)
        .headOption
        .map { row =>
          val dateStr = row.getElementsByTag("td").first().text()
          val date = Instant.parse(dateStr)
          val href = row.getElementsByTag("a").first().attr("href")
          val id = href.stripPrefix("/solutions/")
          val dislikesText = row.getElementsByTag("td").last().text()
          val dislikes = Try(dislikesText.toInt).toOption
          Submission(id, date, dislikes.nonEmpty, dislikes)
        }
    }

  }

  case class FigureDto(edges: List[List[Int]], vertices: List[List[Int]])
  case class BonusPointDto(position: List[Int])
  case class ProblemDto(hole: List[List[Int]], figure: FigureDto, bonuses: Seq[BonusPointDto], epsilon: Int)

  case class SolutionDto(vertices: Seq[Seq[Int]])

  implicit val figureDecoder: Decoder[FigureDto] = deriveDecoder[FigureDto]
  implicit val bonusPointDtoDecoder: Decoder[BonusPointDto] = deriveDecoder[BonusPointDto]
  implicit val problemDecoder: Decoder[ProblemDto] = deriveDecoder[ProblemDto]

  implicit val solutionEncoder: Encoder[SolutionDto] =
    deriveEncoder[SolutionDto]
}
