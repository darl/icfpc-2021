package icfpc21.classified
package model

import java.awt.Polygon
import java.awt.geom.Area

case class Figure(vertices: IndexedSeq[Vector], edges: Edges) {
  def segments: Seq[(Vector, Vector)] =
    edges.values.map { e =>
      val a = vertices(e.aIndex)
      val b = vertices(e.bIndex)
      (a, b)
    }

  def updateVertex(idx: Int, update: Vector => Vector): Figure = {
    copy(vertices = vertices.updated(idx, update(vertices(idx))))
  }

  lazy val polygons: Seq[Polygon] = edges.analysis.polygons.map { poly =>
    new Polygon(
      poly.vertexIndices.map(vertices(_).x).toArray,
      poly.vertexIndices.map(vertices(_).y).toArray,
      poly.vertexIndices.size
    )
  }

  lazy val area: Area = {
    val a = new Area
    polygons.foreach(p => a.add(new Area(p)))
    a
  }
}
