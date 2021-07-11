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
        vertices = IndexedSeq(
          Vector(1, 1),
          Vector(3, 1),
          Vector(2, 3)
        ),
        edges = Edges(Seq(Edge(0, 1), Edge(1, 2), Edge(2, 0)))
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
        vertices = IndexedSeq(
          Vector(1, 1),
          Vector(30, 1),
          Vector(2, 3)
        ),
        edges = Edges(Seq(Edge(0, 1), Edge(1, 2), Edge(2, 0)))
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
        vertices = IndexedSeq(
          Vector(0, 5),
          Vector(10, 5),
          Vector(5, 10)
        ),
        edges = Edges(Seq(Edge(0, 1), Edge(1, 2), Edge(2, 0)))
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
        vertices = IndexedSeq(
          Vector(5, 5),
          Vector(2, 10),
          Vector(5, 10),
          Vector(7, 10),
          Vector(9, 10)
        ),
        edges = Edges(Seq(Edge(0, 1), Edge(1, 2), Edge(2, 3), Edge(3, 4), Edge(0, 3), Edge(0, 4)))
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
    "5.count figure as fit" in {
      val figure = Figure(
        vertices = IndexedSeq(
          Vector(0, 1),
          Vector(0, 10),
          Vector(10, 10)
        ),
        edges = Edges(Seq(Edge(0, 1), Edge(1, 2), Edge(2, 0)))
      )

      val hole = Hole(points =
        Seq(
          Vector(0, 0),
          Vector(0, 5),
          Vector(0, 10),
          Vector(10, 10),
          Vector(10, 0)
        )
      )

      assert(Scorer.checkFits(figure, hole) === true)
    }

    "6.count figure as not fit" in {
      val figure = Figure(
        vertices = IndexedSeq(
          Vector(0, 1),
          Vector(10, 5),
          Vector(1, 9)
        ),
        edges = Edges(Seq(Edge(0, 1), Edge(1, 2), Edge(2, 0)))
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
        vertices = IndexedSeq(
          Vector(0, 2),
          Vector(10, 5),
          Vector(0, 8)
        ),
        edges = Edges(Seq(Edge(0, 1), Edge(1, 2), Edge(2, 0)))
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
        vertices = IndexedSeq(
          Vector(0, 2),
          Vector(10, 5),
          Vector(0, 8)
        ),
        edges = Edges(Seq(Edge(0, 1), Edge(1, 2), Edge(2, 0)))
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
    "9.problem 2 solution don't fit" in {
      val figure = Figure(
        vertices = IndexedSeq(
          Vector(30, 10),
          Vector(25, 8),
          Vector(20, 12),
          Vector(15, 25),
          Vector(29, 17),
          Vector(20, 43),
          Vector(22, 54),
          Vector(20, 55),
          Vector(24, 23),
          Vector(28, 47),
          Vector(25, 51),
          Vector(39, 18),
          Vector(26, 53),
          Vector(34, 60),
          Vector(32, 60),
          Vector(35, 11),
          Vector(34, 52),
          Vector(30, 56),
          Vector(48, 14),
          Vector(38, 18),
          Vector(38, 59),
          Vector(45, 10),
          Vector(41, 49),
          Vector(50, 13),
          Vector(63, 45),
          Vector(65, 44),
          Vector(62, 40),
          Vector(67, 14),
          Vector(65, 41),
          Vector(67, 23),
          Vector(69, 24),
          Vector(64, 37),
          Vector(64, 38),
          Vector(65, 25),
          Vector(64, 32),
          Vector(75, 25),
          Vector(65, 28),
          Vector(75, 5),
          Vector(65, 8)
        ),
        edges = Edges(
          List(
            Edge(2, 0),
            Edge(0, 1),
            Edge(1, 4),
            Edge(4, 2),
            Edge(4, 11),
            Edge(11, 18),
            Edge(18, 21),
            Edge(21, 15),
            Edge(15, 11),
            Edge(2, 3),
            Edge(3, 8),
            Edge(8, 19),
            Edge(19, 15),
            Edge(9, 10),
            Edge(10, 6),
            Edge(6, 7),
            Edge(7, 12),
            Edge(12, 16),
            Edge(16, 17),
            Edge(17, 13),
            Edge(13, 14),
            Edge(14, 20),
            Edge(20, 22),
            Edge(22, 16),
            Edge(16, 9),
            Edge(3, 5),
            Edge(5, 9),
            Edge(22, 19),
            Edge(19, 23),
            Edge(23, 27),
            Edge(27, 26),
            Edge(26, 22),
            Edge(31, 28),
            Edge(28, 24),
            Edge(24, 25),
            Edge(25, 32),
            Edge(32, 34),
            Edge(34, 33),
            Edge(33, 29),
            Edge(29, 30),
            Edge(30, 35),
            Edge(35, 36),
            Edge(36, 34),
            Edge(34, 31),
            Edge(31, 26),
            Edge(36, 38),
            Edge(38, 37),
            Edge(37, 27)
          )
        )
      )

      val hole = Hole(
        List(
          Vector(15, 60),
          Vector(15, 25),
          Vector(5, 25),
          Vector(5, 5),
          Vector(15, 5),
          Vector(15, 10),
          Vector(25, 10),
          Vector(25, 5),
          Vector(35, 5),
          Vector(35, 10),
          Vector(45, 10),
          Vector(45, 5),
          Vector(55, 5),
          Vector(55, 10),
          Vector(65, 10),
          Vector(65, 5),
          Vector(75, 5),
          Vector(75, 25),
          Vector(65, 25),
          Vector(65, 60)
        )
      )

      assert(Scorer.checkFits(figure, hole) === false)
    }
    "checkStretchingIsOk returns true on checking stretching of first example" in {
      import TestData.Example1._
      assert(
        Scorer.checkStretchingIsOk(originalFigure, Problem(hole, submittedFigure, Seq.empty, epsilon))
      )
    }
    "checkStretchingIsOk returns false" in {
      val origFigure = Figure(
        vertices = IndexedSeq(Vector(0, 0), Vector(0, 1), Vector(1, 1)),
        edges = Edges(Seq(Edge(0, 1), Edge(1, 2), Edge(2, 0)))
      )

      val submitted = Figure(
        vertices = IndexedSeq(Vector(0, 0), Vector(0, 1), Vector(2, 2)),
        edges = origFigure.edges
      )

      assert(Scorer.checkStretchingIsOk(origFigure, Problem(hole, submitted, Seq.empty, 100_000)) === false)
    }
  }
}
