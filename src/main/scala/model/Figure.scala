package icfpc21.classified
package model

case class Figure(vertices: Seq[Vector], edges: Edges) {
  def segments: Seq[(Vector, Vector)] = edges.values.map { e =>
    val a = vertices(e.aIndex)
    val b = vertices(e.bIndex)
    (a, b)
  }
}
