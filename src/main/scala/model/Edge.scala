package icfpc21.classified
package model

case class Edge(aIndex: Int, bIndex: Int) {
  def swap = Edge(bIndex, aIndex)
  def containsPoint(idx: Int): Boolean = aIndex == idx || bIndex == idx
}
