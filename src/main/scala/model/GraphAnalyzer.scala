package icfpc21.classified
package model

import scala.collection.SortedMap

case class GraphAnalyzer(edges: Seq[Edge]) {
  val links: SortedMap[Int, Seq[Int]] = edges
    .flatMap(e => Seq(e.aIndex -> e.bIndex, e.bIndex -> e.aIndex))
    .groupBy(_._1)
    .map { case (key, value) => key -> value.map(_._2).toVector.sorted }
    .to(SortedMap)

//  val polygons = for {
//    start <- links.keys
//    poly <- ???
//  } yield poly
}
