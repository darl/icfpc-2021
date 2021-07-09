package icfpc21.classified
package optimizer

import icfpc21.classified.model.{Figure, Hole}

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

  def checkStretchingIsOk(
      origF: Figure,
      currentF: Figure,
      eps: Int
  ): Boolean = {
    val allowedEpsDiff = eps.toDouble / 1_000_000
    origF.edges.forall { edge =>
      val origLength = (origF.vertices(edge.bIndex) - origF.vertices(edge.aIndex)).length
      val curLength = (currentF.vertices(edge.bIndex) - currentF.vertices(edge.aIndex)).length
      Math.abs(origLength / curLength - 1) <= allowedEpsDiff
    }
  }

  def score(figure: Figure, hole: Hole)(implicit eps: Int): Double = {
    var result = 0d
    if (figure.isValid) result += 10000
    if (checkFits(figure, hole)) result += 1000
    result += scoreDislikes(figure, hole)
    result
  }

}
