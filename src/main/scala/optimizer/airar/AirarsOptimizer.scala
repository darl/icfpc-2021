package icfpc21.classified
package optimizer.airar

import model.{Vector, Problem}

import java.awt.Polygon
import java.awt.geom.Line2D
import scala.util.Random

object AirarsOptimizer {

  def optimize(problem: Problem): Option[List[(Int, Vector)]] = {
    val holePolygon = new Polygon(
      problem.hole.points.map(_.x).toArray,
      problem.hole.points.map(_.y).toArray,
      problem.hole.points.size
    )
    val polyLines = problem.hole.points
      .zip(problem.hole.points.drop(1) :+ problem.hole.points.head)
      .map { case (p1, p2) => new Line2D.Double(p1.x, p1.y, p2.x, p2.y) }

    val maxHoleX = problem.hole.points.maxBy(_.x).x
    val maxHoleY = problem.hole.points.maxBy(_.y).y

    val inPoints = for {
      x <- Seq.range(0, maxHoleX)
      y <- Seq.range(0, maxHoleY)
      if holePolygon.contains(x, y)
    } yield Vector(x, y)

    def isIntersectPolygon(from: Vector, to: Vector) = {
      polyLines.exists(_.intersectsLine(from.x, from.y, to.x, to.y))
    }

    val distancesSeq = for {
      p1 <- inPoints
      p2 <- inPoints
      if p1 != p2
      if !isIntersectPolygon(p1, p2)
    } yield (p1, p2, (p2 - p1).squaredLength)

    val holeCoorsWithDist: Map[Vector, Seq[(Vector, Int)]] =
      distancesSeq
        .groupMap(_._1) { case (_, to, distance) => (to, distance) }
        .view
        .mapValues(_.sortBy(_._2))
        .toMap

//    println(s"holeCoorsWithDist: $holeCoorsWithDist")

    type Solution = List[(Int, Vector)]

    val vertToVertWithDist: Map[Int, Seq[Int]] =
      problem.figure.edges
        .flatMap { edge =>
          Seq(
            (
              edge.aIndex,
              edge.bIndex
            ),
            (
              edge.bIndex,
              edge.aIndex
            )
          )
        }
        .groupBy(_._1)
        .mapValues(_.map { case (_, to) => (to) }.sorted)
        .toMap

    println(
      s"vertToVerts: ${vertToVertWithDist.toList.sortBy(_._1).map { case (k, v) => s"${k} -> ${v.mkString(",")}" }.mkString("\n")}"
    )

    def putFigure(
        nextIndToTry: Seq[Int],
        pointLimits: Map[Int, Set[Vector]],
        currentSolution: Solution
    ): Option[Solution] = {
//      if (Random.nextInt(1) == ) {
      //      println(s"nextIndToTry: ${nextIndToTry}")
      //        println(s"pointLimits: ${pointLimits.map(x => x._1 -> x._2.size).toList.sorted}")
      println(s"currentSolution: (${currentSolution.size}): ${currentSolution}")
//      } else ()

      assert(currentSolution.toSet.size == currentSolution.size)
      nextIndToTry match {
        case curIndToProcess :: tail =>
//          println(s"curIndToProcess: $curIndToProcess")
          pointLimits(curIndToProcess).view
            .map { candidatePoint =>
              val neibIndices = vertToVertWithDist(curIndToProcess)
              val neibsLimits = neibIndices.map { neibInd =>
                val originalPoint = problem.figure.vertices(curIndToProcess)
                val originalDistance =
                  (originalPoint - problem.figure.vertices(neibInd)).squaredLength
                val minAllowedDist = (-problem.epsilon.toDouble / 1_000_000 + 1) * originalDistance
                val maxAllowedDist = (+problem.epsilon.toDouble / 1_000_000 + 1) * originalDistance

//                println(s"minAllowedDist: ${minAllowedDist}, maxAllowedDist: ${maxAllowedDist}")

                neibInd -> holeCoorsWithDist
                  .getOrElse(candidatePoint, Set.empty)
                  .collect {
                    case (p, dist) if dist > minAllowedDist && dist < maxAllowedDist => p
                  }
                  .map { v =>
                    val tension = Math.abs(
                      (candidatePoint - v).squaredLength.toDouble / originalDistance - 1
                    )
                    val allowedTension = problem.epsilon.toDouble / 1_000_000

                    assert(tension < allowedTension, s"tension: ${tension}, allowedtension: ${allowedTension}}")

                    v
                  }
                  .toSet
              }.toMap

              val nextLimits = pointLimits.map {
                case (k, v) => (k, if (neibsLimits.contains(k)) v.intersect(neibsLimits(k)) else v)
              }
//              println(s"nextLimits: ${nextLimits.map(x => x._1 -> x._2.size).toList.sorted}")

              if (nextLimits.exists { case (_, points) => points.isEmpty }) {
//                println(s"return None on point: ${candidatePoint}")
                None
              } else {
//                println("call recursively")
                val nextSolution =
                  currentSolution.appended(curIndToProcess -> model.Vector(candidatePoint.x, candidatePoint.y))
                putFigure(
//                  (nextLimits.keys ++ tail.filterNot(neibsLimits.contains))
                  tail
                    .filterNot(ind => nextSolution.exists(_._1 == ind))
                    .toList,
                  nextLimits,
                  nextSolution
                )
              }
            }
            .collectFirst { case Some(v) => v }
        case Nil => Some(currentSolution)
      }

    }

//    println(s"inPoints: $inPoints")

    val allIndices = problem.figure.edges.flatMap(v => Seq(v.aIndex, v.bIndex)).toSet

    val allPointsSet = inPoints.toSet

    putFigure(
      allIndices.toList.sortBy(ind => -vertToVertWithDist(ind).size),
      allIndices.map(ind => ind -> allPointsSet).toMap,
      List.empty
    )
  }

}
