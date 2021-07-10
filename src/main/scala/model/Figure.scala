package icfpc21.classified
package model

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
}
