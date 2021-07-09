package icfpc21.classified
package optimizer

import icfpc21.classified.model.{Figure, Hole, Problem}

import java.awt.Polygon

object Scorer {
  def scoreDislikes(figure: Figure, hole: Hole): Double = {
    hole.points.foldLeft(0) {
      case (sum, point) =>
        val minDistance = figure.vertices.map(v => (v - point).squaredLength).min
        sum + minDistance
    }
  }

  def checkFits(figure: Figure, hole: Hole): Boolean = {
    val poly = new Polygon(
      hole.points.map(_.x).toArray,
      hole.points.map(_.y).toArray,
      hole.points.size
    )
    figure.vertices.forall(p => poly.contains(p.x, p.y))
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
    if (checkStretchingIsOk(figure, problem)) result += 10000
    if (checkFits(figure, problem.hole)) result += 1000
    result += scoreDislikes(figure, problem.hole)
    result
  }

}
