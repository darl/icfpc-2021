package icfpc21.classified
package utils

import java.awt.geom.Area
import Double.NaN

object AreaUtils {

  import java.awt.geom.AffineTransform

  private val identity = AffineTransform.getQuadrantRotateInstance(0)

  def calcArea(area: Area): Int = {
    val b = area.getBounds
    var sum = 0
    for {
      x <- b.x until b.x + b.width
      y <- b.y until b.y + b.height
    } {
      if (area.contains(x, y)) sum += 1
    }
    sum
  }

  import java.awt.geom.Area
  import java.awt.geom.FlatteningPathIterator
  import java.awt.geom.Line2D
  import java.awt.geom.PathIterator

  def approxArea(area: Area, flatness: Double, limit: Int): Double = {
    val i = new FlatteningPathIterator(area.getPathIterator(identity), flatness, limit)
    approxArea(i)
  }

  def approxArea(area: Area, flatness: Double): Double = {
    val i = area.getPathIterator(identity, flatness)
    approxArea(i)
  }

  def approxArea(i: PathIterator): Double = {
    var a = 0.0
    val coords = new Array[Double](6)
    var startX = NaN
    var startY = NaN
    val segment = new Line2D.Double(NaN, NaN, NaN, NaN)
    while (!i.isDone) {
      val segType = i.currentSegment(coords)
      val x = coords(0)
      val y = coords(1)
      segType match {
        case PathIterator.SEG_CLOSE =>
          segment.setLine(segment.getX2, segment.getY2, startX, startY)
          a += hexArea(segment)
          startX = NaN
          startY = NaN
          segment.setLine(NaN, NaN, NaN, NaN)

        case PathIterator.SEG_LINETO =>
          segment.setLine(segment.getX2, segment.getY2, x, y)
          a += hexArea(segment)

        case PathIterator.SEG_MOVETO =>
          startX = x
          startY = y
          segment.setLine(NaN, NaN, x, y)

        case _ =>
          throw new IllegalArgumentException("PathIterator contains curved segments")
      }
      i.next()
    }
    if (a.isNaN) throw new IllegalArgumentException("PathIterator contains an open path")
    else 0.5 * Math.abs(a)
  }

  private def hexArea(seg: Line2D) = seg.getX1 * seg.getY2 - seg.getX2 * seg.getY1
}
