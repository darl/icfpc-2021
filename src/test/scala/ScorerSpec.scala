package icfpc21.classified

import icfpc21.classified.data.TestData
import icfpc21.classified.model._
import icfpc21.classified.optimizer.Scorer
import org.scalatest.wordspec.AnyWordSpec

class ScorerSpec extends AnyWordSpec {

  "Scorer" should {
    "count figure as fit" in {
      val figure = Figure(
        vertices = Seq(
          Vector(0, 0),
          Vector(0, 1),
          Vector(1, 0)
        ),
        edges = Seq(Edge(0, 1), Edge(1, 2), Edge(2, 0))
      )

      val hole = Hole(points =
        Seq(
          Vector(0, 0),
          Vector(0, 2),
          Vector(2, 0)
        )
      )

      assert(Scorer.checkFits(figure, hole) === true)
    }
    "count figure as non fit" in {
      val figure = Figure(
        vertices = Seq(
          Vector(0, 0),
          Vector(0, 10),
          Vector(1, 0)
        ),
        edges = Seq(Edge(0, 1), Edge(1, 2), Edge(2, 0))
      )

      val hole = Hole(points =
        Seq(
          Vector(0, 0),
          Vector(0, 2),
          Vector(2, 0)
        )
      )

      assert(Scorer.checkFits(figure, hole) === false)
    }
    "checkStretchingIsOk returns true on checking stretching of first example" in {
      import TestData.Example1._
      assert(
        Scorer.checkStretchingIsOk(originalFigure, submittedFigure, epsilon)
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

      assert(Scorer.checkStretchingIsOk(origFigure, submitted, 100_000) === false)
    }
  }
}
