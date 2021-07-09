package icfpc21.classified
package optimizer

import icfpc21.classified.model.{Figure, Hole}

import java.awt.Polygon

object Scorer {
  def scoreDislikes(figure: Figure, hole: Hole): Double = {
    hole.points.foldLeft(0) { case (sum, point) =>
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

}
