package icfpc21.classified
package model

case class Edges(values: Seq[Edge]) {
  override lazy val hashCode: Int = super.hashCode()
  val analysis: GraphAnalyzer =  GraphAnalyzer(values)
}
