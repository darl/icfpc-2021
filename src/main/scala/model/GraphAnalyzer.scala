package icfpc21.classified
package model

import scala.collection.SortedMap

case class GraphAnalyzer(edges: Seq[Edge]) {
  val links: SortedMap[Int, Seq[Int]] = edges
    .flatMap(e => Seq(e.aIndex -> e.bIndex, e.bIndex -> e.aIndex))
    .groupBy(_._1)
    .map { case (key, value) => key -> value.map(_._2).toVector.sorted }
    .to(SortedMap)

  // Закрытая фигура, полигон
  case class Poly(vertexIndices: Seq[Int]) {
    def size: Int = vertexIndices.size
  }

  def polyFromPoint(start: Int, current: Int, visited: Seq[Int]): Seq[Poly] = {
    val outerPoints = links.getOrElse(current, Seq.empty)
    outerPoints
      .flatMap { outer =>
        if (outer == start) List(Poly(visited))
        else if (visited.contains(outer)) List.empty
        else if (visited.size > 10) List.empty
        else polyFromPoint(start, outer, visited :+ outer)
      }
  }

  // TODO too slow for big figures (eg #77)
  lazy val polygons: Seq[Poly] = for {
    start <- links.keys.toVector
    poly <- polyFromPoint(start, start, Seq(start))
      .filter(p => p.vertexIndices(0) < p.vertexIndices(1))
      .filter(_.size > 2)
  } yield poly
}
