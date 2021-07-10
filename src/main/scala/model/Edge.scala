package icfpc21.classified
package model

case class Edge(aIndex: Int, bIndex: Int) {
  def containsPoint(idx: Int): Boolean = aIndex == idx || bIndex == idx
}
