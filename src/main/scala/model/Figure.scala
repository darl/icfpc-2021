package icfpc21.classified
package model

case class Figure(vertices: Seq[Vector], edges: Seq[Edge]) {
  def segments: Seq[(Vector, Vector)] = edges.map { e =>
    val a = vertices(e.aIndex)
    val b = vertices(e.bIndex)
    (a, b)
  }
}
