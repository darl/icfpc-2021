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
      .filterNot(p => hole.asPolygon.contains(p.x, p.y))
      .map(p => hole.segments.map(pair => p.distanceToLine(pair._1, pair._2)).min)
      .sum
  }

  def checkFits(figure: Figure, hole: Hole): Boolean = {
    val holeEdges = hole.points.indices.dropRight(1).map { i =>
      (i, i + 1)
    } :+ (0, hole.points.size - 1)

    val onTheEdge = scala.collection.mutable.HashMap[Vector, Boolean]()

    val intersections = figure.edges.forall { edge =>
      val q = figure.vertices(edge.aIndex)
      val s = figure.vertices(edge.bIndex) - q
      holeEdges.forall {
        case (pIndex, rIndex) =>
          val p = hole.points(pIndex)
          val r = hole.points(rIndex) - p

          // https://stackoverflow.com/a/565282
          val p1 = (q - p).product(r)
          val p2 = r.product(s)

          (p1, p2) match {
            //collinear
            case (0, 0) =>
              val t0 = ((q - p) |*| r) / (r |*| r)
              val t1 = t0 + ((s |*| r) / (r |*| r))

              if (t0 >= 0 && t0 <= 1) {
                onTheEdge.put(q, true)
                true
              } else if (t1 >= 0 && t1 <= 1) {
                onTheEdge.put(figure.vertices(edge.bIndex), true)
                true
              } else false
            case (_, 0) => true
            case _ =>
              val u = p1.toDouble / p2
              if (u == 0) onTheEdge.put(q, true)
              if (u == 1) onTheEdge.put(figure.vertices(edge.bIndex), true)
              (u <= 0 || u >= 1) || {
                val t = (q - p).product(s).toDouble / r.product(s)
                t <= 0 || t >= 1
              }
          }
      }
    }

    val poly = new Polygon(hole.points.map(_.x).toArray, hole.points.map(_.y).toArray, hole.points.size)
    intersections && figure.vertices.forall(v => onTheEdge.getOrElse(v, poly.contains(v.x, v.y)))

  }

  def checkStretchingIsOk(currentF: Figure, problem: Problem): Boolean = {
    val allowedEpsDiff = problem.epsilon.toDouble / 1_000_000
    problem.figure.edges.forall { edge =>
      val origLength = (problem.figure.vertices(edge.bIndex) - problem.figure.vertices(edge.aIndex)).squaredLength
      val curLength = (currentF.vertices(edge.bIndex) - currentF.vertices(edge.aIndex)).squaredLength
      Math.abs((origLength.toDouble / curLength) - 1) <= allowedEpsDiff
    }
  }

  def score(figure: Figure, problem: Problem): Double = {
    var result = 0d
    if (checkStretchingIsOk(figure, problem)) result += 100000
    if (checkFits(figure, problem.hole)) result += 10000
    result -= 1000 * scoreOutsidePoints(figure, problem.hole)
    result -= scoreDislikes(figure, problem.hole)
    result
  }

}
