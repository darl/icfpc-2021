package icfpc21.classified
package optimizer

import model._
import utils.AreaUtils

import java.awt.geom.Area

object Scorer {
  def scoreDislikes(figure: Figure, hole: Hole): Double = {
    hole.points.foldLeft(0) {
      case (sum, point) =>
        val minDistance = figure.vertices.map(v => (v - point).squaredLength).min
        sum + minDistance
    }
  }

  def scoreOutsidePoints(figure: Figure, hole: Hole): Double = {
    figure.vertices
      .filterNot(p => hole.isInside(p))
      .map(p => hole.segments.foldLeft(Double.MaxValue)((m, pair) => m min p.distanceToLine(pair._1, pair._2)))
      .sum
  }

  private def contains(segment: (VectorD, VectorD), c: VectorD): Boolean = {
    val (a, b) = segment
    val crossProduct = (c.y - a.y) * (b.x - a.x) - (c.x - a.x) * (b.y - a.y)

    // compare versus epsilon for floating point values, or != 0 if using integers
    if (crossProduct == 0) {
      false
    } else {
      val dotproduct = (c.x - a.x) * (b.x - a.x) + (c.y - a.y) * (b.y - a.y)
      if (dotproduct < 0) {
        false
      } else {
        val squaredlengthba = (b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y)
        dotproduct <= squaredlengthba
      }
    }
  }

  def checkFits(figure: Figure, hole: Hole): Boolean = {
    val segments = hole.segments.map(s => (s._1.toDouble, s._2.toDouble)).zipWithIndex

    val intersections = figure.edges.values.forall { edge =>
      val q = figure.vertices(edge.aIndex).toDouble
      val s = figure.vertices(edge.bIndex).toDouble - q
      if (q.x == 75 && q.y == 25 || q.x == 65 && q.y == 28) {
        println("")
      }
      segments.forall {
        case ((a, b), i) =>
          val p = a
          val r = b - p

          // https://stackoverflow.com/a/565282
          val p1 = (q - p).product(r)
          val p2 = r.product(s)

          (p1, p2) match {
            //collinear
            case (0, 0) =>
              val t0 = ((q - p) |*| r) / (r |*| r)
              val t1 = t0 + ((s |*| r) / (r |*| r))

              if (t0 >= 0 && t0 <= 1) {
                hole.setInside(figure.vertices(edge.aIndex))
              }
              if (t1 >= 0 && t1 <= 1) {
                hole.setInside(figure.vertices(edge.bIndex))
              }
              val min = t0.min(t1)
              val max = t0.max(t1)

              if (min >= 0 && min <= 1) {
                if (max < 0 || max > 1) {
                  var pisechka = q + (s * min)
                  pisechka = pisechka + pisechka.widthLength(0.01)
                  hole.asPolygon.contains(pisechka.x, pisechka.y) || {
                    val n = q + s.widthLength(0.01)
                    contains(
                      segments((i + 1) % segments.size)._1,
                      figure.vertices(edge.bIndex).toDouble
                    ) && hole.asPolygon.contains(n.x, n.y)
                  }
                } else true
              } else if (max >= 0 && max <= 1) {
                var pisechka = q + (s * max)
                pisechka = pisechka + pisechka.widthLength(0.01)
                hole.asPolygon.contains(pisechka.x, pisechka.y) || {
                  val n = q + s.widthLength(0.01)
                  contains(segments((i + 1) % segments.size)._1, q) && hole.asPolygon.contains(n.x, n.y)
                }
              } else false
            case (_, 0) => true
            case _ =>
              val u = p1 / p2
              if (u == 0) hole.setInside(figure.vertices(edge.aIndex))
              if (u == 1) hole.setInside(figure.vertices(edge.bIndex))
              (u <= 0 || u >= 1) || {
                val t = (q - p).product(s) / r.product(s)
                t <= 0 || t >= 1
              }
          }
      }
    }

    intersections && figure.vertices.forall(v => hole.isInside(v))

  }

  def findInvalidEdges(figure: Figure, problem: Problem): Seq[Edge] = {
    val allowedEpsDiff = problem.epsilon.toDouble / 1_000_000
    problem.figure.edges.values.filter { edge =>
      val curLength = (figure.vertices(edge.bIndex) - figure.vertices(edge.aIndex)).squaredLength
      val origLength = (problem.figure.vertices(edge.bIndex) - problem.figure.vertices(edge.aIndex)).squaredLength
      Math.abs((curLength / origLength.toDouble) - 1) > allowedEpsDiff
    }
  }

  def checkStretchingIsOk(currentF: Figure, problem: Problem): Boolean = {
    findInvalidEdges(currentF, problem).isEmpty
  }

  def scoreOutsideArea(figure: Figure, problem: Problem): Double = {
//    figure.polygons.map { poly =>
//      val area = new Area(poly)
//      area.subtract(new Area(problem.hole.asPolygon))
//      if (area.isEmpty) 0
//      else AreaUtils.approxArea(area, 1d)
//    }.sum
    val area = new Area(figure.area)
    area.subtract(new Area(problem.hole.asPolygon))
    AreaUtils.approxArea(area, 1d)
  }

  def closestToBonus(bonuses: Seq[BonusPoint], figure: Figure): Double = {
    bonuses.map { bonus =>
      figure.vertices.map(_.distanceTo(bonus.center)).min
    }.sum
  }

  case class Score(figure: Figure, problem: Problem, skipArea: Boolean) {
    val valid = checkStretchingIsOk(figure, problem)
    val fits = checkFits(figure, problem.hole)
    val dislikes = scoreDislikes(figure, problem.hole)
    val outsideArea: Double = if (skipArea || fits) 0d else scoreOutsideArea(figure, problem)
    val bonus: Double = closestToBonus(problem.bonuses, figure)
    val bonusPoints = -50 * bonus

    val stretchingPoints: Double = if (valid) 1000000000d else 0d
    val outsidePoints: Double = -10000d * scoreOutsidePoints(figure, problem.hole)
    val outsideAreaPoints: Double = outsideArea * -100000d
    val fitsPoints = if (fits) 100000d else -100000d
    val dislikePoints: Double = if (fits) -dislikes else -4 * dislikes

    val total: Double =
      (stretchingPoints + outsidePoints + outsideAreaPoints + fitsPoints + dislikePoints + bonusPoints)

    val totalDebug =
      s"$stretchingPoints + $outsidePoints + $outsideAreaPoints + $fitsPoints + $dislikePoints + $bonusPoints"
  }

  def score(figure: Figure, problem: Problem, skipArea: Boolean): Score = {
    Score(figure, problem, skipArea)
  }

}
