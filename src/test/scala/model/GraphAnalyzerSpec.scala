package icfpc21.classified
package model

import icfpc21.classified.data.TestData
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.immutable.TreeMap

class GraphAnalyzerSpec extends AnyWordSpec {
  val ga: GraphAnalyzer = GraphAnalyzer(TestData.Example1.originalFigure.edges)
  "GraphAnalyzer" should {
    "check links" in {
      assert(
        ga.links === TreeMap(
          0 -> Seq(1, 8),
          1 -> Seq(0, 4),
          2 -> Seq(5, 6),
          3 -> Seq(7, 8, 9),
          4 -> Seq(1, 5, 8),
          5 -> Seq(2, 4, 10),
          6 -> Seq(2, 10),
          7 -> Seq(3, 9, 11),
          8 -> Seq(0, 3, 4, 9, 12),
          9 -> Seq(3, 7, 8, 11, 12, 13),
          10 -> Seq(5, 6, 15, 16),
          11 -> Seq(7, 9, 13),
          12 -> Seq(8, 9, 13, 14, 18),
          13 -> Seq(9, 11, 12),
          14 -> Seq(12, 15, 19),
          15 -> Seq(10, 14, 17),
          16 -> Seq(10, 17),
          17 -> Seq(15, 16),
          18 -> Seq(12, 19),
          19 -> Seq(14, 18)
        )
      )
    }
    "cal polygons" in {
      ga.polygons.foreach(println)
    }
  }
}
