package icfpc21.classified
package model

import java.awt.Polygon

case class Hole(points: Seq[Vector]) {
  lazy val asPolygon = new Polygon(
    points.map(_.x).toArray,
    points.map(_.y).toArray,
    points.size
  )

  def isInside(point: Vector): Boolean = asPolygon.contains(point.x, point.y)

  def segments: Seq[(Vector, Vector)] =
    (points.sliding(2) ++ Iterator(Seq(points.last, points.head)))
      .map(p => p.head -> p.last)
      .toVector
}
