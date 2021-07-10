package icfpc21.classified
package optimizer

import icfpc21.classified.model._

import java.awt.Polygon

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
      .map(p => hole.segments.map(pair => p.distanceToLine(pair._1, pair._2)).min)
      .sum
  }

  def checkFits(figure: Figure, hole: Hole): Boolean = {
    val holeEdges = hole.points.indices.dropRight(1).map { i =>
      (i, i + 1)
    } :+ (0, hole.points.size - 1)

    val onTheEdge = scala.collection.mutable.HashSet[Vector]()
    val poly = new Polygon(hole.points.map(_.x).toArray, hole.points.map(_.y).toArray, hole.points.size)

    val intersections = figure.edges.values.forall { edge =>
      val q = figure.vertices(edge.aIndex).toDouble
      val s = figure.vertices(edge.bIndex).toDouble - q
      holeEdges.forall {
        case (pIndex, rIndex) =>
          val p = hole.points(pIndex).toDouble
          val r = hole.points(rIndex).toDouble - p

          // https://stackoverflow.com/a/565282
          val p1 = (q - p).product(r)
          val p2 = r.product(s)

          (p1, p2) match {
            //collinear
            case (0, 0) =>
              val t0 = ((q - p) |*| r) / (r |*| r)
              val t1 = t0 + ((s |*| r) / (r |*| r))

              if (t0 >= 0 && t0 <= 1) {
                onTheEdge.add(figure.vertices(edge.aIndex))
              }
              if (t1 >= 0 && t1 <= 1) {
                onTheEdge.add(figure.vertices(edge.bIndex))
              }
              val min = t0.min(t1)
              val max = t0.max(t1)

              if (min >= 0 && min <= 1) {
                if (max < 0 || max > 1) {
                  var pisechka = q + (s * min)
                  pisechka = pisechka + pisechka.widthLength(0.01)
                  poly.contains(pisechka.x, pisechka.y)
                } else true
              } else if (max >= 0 && max <= 1) {
                var pisechka = q + (s * max)
                pisechka = pisechka + pisechka.widthLength(0.01)
                poly.contains(pisechka.x, pisechka.y)
              } else false
            case (_, 0) => true
            case _ =>
              val u = p1.toDouble / p2
              if (u == 0) onTheEdge.add(figure.vertices(edge.aIndex))
              if (u == 1) onTheEdge.add(figure.vertices(edge.bIndex))
              (u <= 0 || u >= 1) || {
                val t = (q - p).product(s).toDouble / r.product(s)
                t <= 0 || t >= 1
              }
          }
      }
    }

    intersections && figure.vertices.forall(v => onTheEdge.contains(v) || poly.contains(v.x, v.y))

  }

  def checkStretchingIsOk(currentF: Figure, problem: Problem): Boolean = {
    val allowedEpsDiff = problem.epsilon.toDouble / 1_000_000
    problem.figure.edges.values.forall { edge =>
      val origLength = (problem.figure.vertices(edge.bIndex) - problem.figure.vertices(edge.aIndex)).squaredLength
      val curLength = (currentF.vertices(edge.bIndex) - currentF.vertices(edge.aIndex)).squaredLength
      Math.abs((origLength.toDouble / curLength) - 1) <= allowedEpsDiff
    }
  }

  def score(figure: Figure, problem: Problem): Double = {
    var result = 0d
    if (checkStretchingIsOk(figure, problem)) result += 1000000000d
    val fits = checkFits(figure, problem.hole)
    result -= 10000 * scoreOutsidePoints(figure, problem.hole)

    if (fits) result += 10000
    if (fits) result -= scoreDislikes(figure, problem.hole)

    result
  }

}
