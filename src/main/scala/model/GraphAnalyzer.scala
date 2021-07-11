package icfpc21.classified
package model

import icfpc21.classified.utils.DisjointSet

import scala.collection.{SortedMap, mutable}

case class GraphAnalyzer(edges: Seq[Edge]) {
  val links: SortedMap[Int, Seq[Int]] = edges
    .flatMap(e => Seq(e.aIndex -> e.bIndex, e.bIndex -> e.aIndex))
    .groupBy(_._1)
    .map { case (key, value) => key -> value.map(_._2).toVector.sorted }
    .to(SortedMap)

  // Закрытая фигура, полигон
  case class Poly(vertexIndices: Seq[Int]) {
    def size: Int = vertexIndices.size
    def toSet: Set[Int] = vertexIndices.toSet
    def isInside(other: Poly): Boolean = (toSet -- other.toSet).isEmpty
  }

  object Poly {
    implicit val ord: Ordering[Poly] = Ordering.by(p => (p.size, p.vertexIndices.mkString("z")))
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

  private def removeDuplicates(polys: Seq[Poly]) = {
    val out = new mutable.ListBuffer[Poly]
    for (poly <- polys.distinct.sorted) {
      if (!out.exists(_.isInside(poly))) {
        out += poly
      }
    }
    out.toVector
  }

  // TODO too slow for big figures (eg #77)
  lazy val polygons: Seq[Poly] = removeDuplicates(for {
    start <- links.keys.toVector
    poly <- polyFromPoint(start, start, Seq(start))
      .filter(p => p.vertexIndices(0) < p.vertexIndices(1))
      .filter(_.size > 2)
  } yield poly)

  lazy val joints: Seq[Joint] = {
    if (links.size == 1) {
      Seq.empty
    } else {
      for {
        idx <- links.keys
        disjoint = {
          val d = new DisjointSet
          for (edge <- edges) {
            if (edge.aIndex == idx) d.join(edge.bIndex, edge.bIndex)
            else if (edge.bIndex == idx) d.join(edge.aIndex, edge.aIndex)
            else {
              d.join(edge.aIndex, edge.bIndex)
            }
          }
          d
        }
        if disjoint.connectedRegions.sizeIs > 1

      } yield Joint(idx, disjoint.connectedRegions)
    }.toVector
  }

  lazy val axes: Seq[Axe] = {
    for {
      aIndex <- links.keys
      bIndex <- links.keys
      if aIndex < bIndex

      disjoint = {
        val d = new DisjointSet
        for (edge <- edges) {
          if (edge.containsPoint(aIndex) || edge.containsPoint(bIndex)) {
            if (edge.aIndex != aIndex && edge.aIndex != bIndex) d.join(edge.aIndex, edge.aIndex)
            if (edge.bIndex != aIndex && edge.bIndex != bIndex) d.join(edge.bIndex, edge.bIndex)
            // do nothing
          } else {
            d.join(edge.aIndex, edge.bIndex)
          }
        }
        d
      }
      if disjoint.connectedRegions.sizeIs > 1

    } yield Axe(aIndex, bIndex, disjoint.connectedRegions)
  }.toVector
}
