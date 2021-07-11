package icfpc21.classified
package model

import java.awt.Polygon
import java.awt.geom.Area

case class Hole(points: Seq[Vector]) {
  lazy val asPolygon = new Polygon(
    points.map(_.x).toArray,
    points.map(_.y).toArray,
    points.size
  )

  val width: Int = points.map(_.x).max - points.map(_.x).min
  val height: Int = points.map(_.y).max - points.map(_.y).min

  lazy val asArea = new Area(asPolygon)

  private val pointsPositions = scala.collection.mutable.HashMap[Vector, Boolean]()

  def setInside(point: Vector): Unit = pointsPositions.put(point, true)

  def isInside(point: Vector): Boolean = {
    pointsPositions.getOrElseUpdate(point, asPolygon.contains(point.x, point.y))
  }

  lazy val segments: Seq[(Vector, Vector)] =
    (points.sliding(2) ++ Iterator(Seq(points.last, points.head)))
      .map(p => p.head -> p.last)
      .toVector
}
