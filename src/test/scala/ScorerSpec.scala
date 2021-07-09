package icfpc21.classified

import icfpc21.classified.data.TestData
import icfpc21.classified.data.TestData.Example1.hole
import icfpc21.classified.model._
import icfpc21.classified.optimizer.Scorer
import org.scalatest.wordspec.AnyWordSpec

class ScorerSpec extends AnyWordSpec {

  "Scorer" should {
    "1.count figure as fit" in {
      val figure = Figure(
        vertices = Seq(
          Vector(1, 1),
          Vector(3, 1),
          Vector(2, 3)
        ),
        edges = Seq(Edge(0, 1), Edge(1, 2), Edge(2, 0))
      )

      val hole = Hole(points =
        Seq(
          Vector(0, 0),
          Vector(0, 10),
          Vector(10, 10),
          Vector(10, 0)
        )
      )

      assert(Scorer.checkFits(figure, hole) === true)
    }
    "2.count figure as non fit" in {
      val figure = Figure(
        vertices = Seq(
          Vector(1, 1),
          Vector(30, 1),
          Vector(2, 3)
        ),
        edges = Seq(Edge(0, 1), Edge(1, 2), Edge(2, 0))
      )

      val hole = Hole(points =
        Seq(
          Vector(0, 0),
          Vector(0, 10),
          Vector(10, 10),
          Vector(10, 0)
        )
      )

      assert(Scorer.checkFits(figure, hole) === false)
    }
    "3.count figure as fit" in {
      val figure = Figure(
        vertices = Seq(
          Vector(0, 5),
          Vector(10, 5),
          Vector(5, 10)
        ),
        edges = Seq(Edge(0, 1), Edge(1, 2), Edge(2, 0))
      )

      val hole = Hole(points =
        Seq(
          Vector(0, 0),
          Vector(0, 10),
          Vector(10, 10),
          Vector(10, 0)
        )
      )

      assert(Scorer.checkFits(figure, hole) === true)
    }
    "4.count figure as fit" in {
      val figure = Figure(
        vertices = Seq(
          Vector(5, 5),
          Vector(2, 10),
          Vector(5, 10),
          Vector(7, 10),
          Vector(9, 10)
        ),
        edges = Seq(Edge(0, 1), Edge(1, 2), Edge(2, 3), Edge(3, 4), Edge(0, 3), Edge(0, 4))
      )

      val hole = Hole(points =
        Seq(
          Vector(0, 0),
          Vector(0, 10),
          Vector(10, 10),
          Vector(10, 0)
        )
      )

      assert(Scorer.checkFits(figure, hole) === true)
    }
//    "5.count figure as fit" in {
//      val figure = Figure(
//        vertices = Seq(
//          Vector(0, 1),
//          Vector(0, 10),
//          Vector(10, 10)
//        ),
//        edges = Seq(Edge(0, 1), Edge(1, 2), Edge(2, 0))
//      )
//
//      val hole = Hole(points =
//        Seq(
//          Vector(0, 0),
//          Vector(0, 5),
//          Vector(0, 10),
//          Vector(10, 10),
//          Vector(10, 0)
//        )
//      )
//
//      assert(Scorer.checkFits(figure, hole) === true)
//    }

    "6.count figure as not fit" in {
      val figure = Figure(
        vertices = Seq(
          Vector(0, 1),
          Vector(10, 5),
          Vector(1, 9)
        ),
        edges = Seq(Edge(0, 1), Edge(1, 2), Edge(2, 0))
      )

      val hole = Hole(points =
        Seq(
          Vector(0, 0),
          Vector(5, 5),
          Vector(0, 10),
          Vector(10, 10),
          Vector(10, 0)
        )
      )

      assert(Scorer.checkFits(figure, hole) === false)
    }
    "7.Super tricky test not fits" in {
      val figure = Figure(
        vertices = Seq(
          Vector(0, 2),
          Vector(10, 5),
          Vector(0, 8)
        ),
        edges = Seq(Edge(0, 1), Edge(1, 2), Edge(2, 0))
      )

      val hole = Hole(points =
        Seq(
          Vector(0, 0),
          Vector(0, 4),
          Vector(1, 5),
          Vector(0, 6),
          Vector(0, 10),
          Vector(10, 10),
          Vector(10, 0)
        )
      )

      assert(Scorer.checkFits(figure, hole) === false)
    }
    "8.Super tricky test fits" in {
      val figure = Figure(
        vertices = Seq(
          Vector(0, 2),
          Vector(10, 5),
          Vector(0, 8)
        ),
        edges = Seq(Edge(0, 1), Edge(1, 2), Edge(2, 0))
      )

      val hole = Hole(points =
        Seq(
          Vector(0, 0),
          Vector(0, 4),
          Vector(-1, 5),
          Vector(0, 6),
          Vector(0, 10),
          Vector(10, 10),
          Vector(10, 0)
        )
      )

      assert(Scorer.checkFits(figure, hole) === true)
    }
    "checkStretchingIsOk returns true on checking stretching of first example" in {
      import TestData.Example1._
      assert(
        Scorer.checkStretchingIsOk(originalFigure, Problem(hole, submittedFigure, epsilon))
      )
    }
    "checkStretchingIsOk returns false" in {
      val origFigure = Figure(
        vertices = Seq(Vector(0, 0), Vector(0, 1), Vector(1, 1)),
        edges = Seq(Edge(0, 1), Edge(1, 2), Edge(2, 0))
      )

      val submitted = Figure(
        vertices = Seq(Vector(0, 0), Vector(0, 1), Vector(2, 2)),
        edges = origFigure.edges
      )

      assert(Scorer.checkStretchingIsOk(origFigure, Problem(hole, submitted, 100_000)) === false)
    }
  }
}
