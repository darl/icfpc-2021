package icfpc21.classified
package model

case class Figure(vertices: Seq[Vector], edges: Seq[Edge]) {
  lazy val calcedEdges: Seq[CalcedEdge] = edges.map { edge =>
    val a = vertices(edge.aIndex)
    val b = vertices(edge.bIndex)
    CalcedEdge(a, b, originSquaredLength = (a - b).squaredLength)
  }

//  def isValid(implicit eps: Int) = edges.forall(_.isValid)
}
