package icfpc21.classified.data

import icfpc21.classified.model._

object TestData {

  object Example1 {
    val hole: Hole = Hole(
      Seq(
        Vector(55, 80),
        Vector(65, 95),
        Vector(95, 95),
        Vector(35, 5),
        Vector(5, 5),
        Vector(35, 50),
        Vector(5, 95),
        Vector(35, 95),
        Vector(45, 80)
      )
    )

    val originalFigure: Figure = Figure(
      vertices = IndexedSeq(
        Vector(20, 30),
        Vector(20, 40),
        Vector(30, 95),
        Vector(40, 15),
        Vector(40, 35),
        Vector(40, 65),
        Vector(40, 95),
        Vector(45, 5),
        Vector(45, 25),
        Vector(50, 15),
        Vector(50, 70),
        Vector(55, 5),
        Vector(55, 25),
        Vector(60, 15),
        Vector(60, 35),
        Vector(60, 65),
        Vector(60, 95),
        Vector(70, 95),
        Vector(80, 30),
        Vector(80, 40)
      ),
      edges = Edges(
        Seq(
          Edge(2, 5),
          Edge(5, 4),
          Edge(4, 1),
          Edge(1, 0),
          Edge(0, 8),
          Edge(8, 3),
          Edge(3, 7),
          Edge(7, 11),
          Edge(11, 13),
          Edge(13, 12),
          Edge(12, 18),
          Edge(18, 19),
          Edge(19, 14),
          Edge(14, 15),
          Edge(15, 17),
          Edge(17, 16),
          Edge(16, 10),
          Edge(10, 6),
          Edge(6, 2),
          Edge(8, 12),
          Edge(7, 9),
          Edge(9, 3),
          Edge(8, 9),
          Edge(9, 12),
          Edge(13, 9),
          Edge(9, 11),
          Edge(4, 8),
          Edge(12, 14),
          Edge(5, 10),
          Edge(10, 15)
        )
      )
    )

    val submittedFigure: Figure = Figure(
      edges = originalFigure.edges,
      vertices = IndexedSeq(
        Vector(21, 28),
        Vector(31, 28),
        Vector(31, 87),
        Vector(29, 41),
        Vector(44, 43),
        Vector(58, 70),
        Vector(38, 79),
        Vector(32, 31),
        Vector(36, 50),
        Vector(39, 40),
        Vector(66, 77),
        Vector(42, 29),
        Vector(46, 49),
        Vector(49, 38),
        Vector(39, 57),
        Vector(69, 66),
        Vector(41, 70),
        Vector(39, 60),
        Vector(42, 25),
        Vector(40, 35)
      )
    )

    val epsilon = 150000
  }

  object Example3 {
    val hole: Hole = Hole(
      List(
        Vector(50, 70),
        Vector(35, 75),
        Vector(35, 65),
        Vector(15, 55),
        Vector(30, 45),
        Vector(25, 30),
        Vector(30, 30),
        Vector(30, 15),
        Vector(45, 25),
        Vector(55, 35),
        Vector(55, 15),
        Vector(65, 20),
        Vector(80, 5),
        Vector(85, 25),
        Vector(90, 25),
        Vector(80, 45),
        Vector(95, 45),
        Vector(105, 50),
        Vector(100, 65),
        Vector(85, 70),
        Vector(90, 85),
        Vector(65, 80),
        Vector(60, 85),
        Vector(55, 70),
        Vector(50, 110),
        Vector(45, 110)
      )
    )

    val originalFigure = Figure(
      IndexedSeq(
        Vector(15, 70),
        Vector(25, 100),
        Vector(30, 35),
        Vector(30, 55),
        Vector(35, 10),
        Vector(35, 75),
        Vector(40, 25),
        Vector(40, 40),
        Vector(45, 35),
        Vector(50, 25),
        Vector(50, 50),
        Vector(50, 60),
        Vector(50, 75),
        Vector(50, 95),
        Vector(55, 45),
        Vector(55, 65),
        Vector(55, 70),
        Vector(60, 20),
        Vector(60, 105),
        Vector(65, 45),
        Vector(65, 65),
        Vector(65, 70),
        Vector(70, 25),
        Vector(70, 50),
        Vector(70, 60),
        Vector(70, 75),
        Vector(70, 95),
        Vector(75, 35),
        Vector(80, 25),
        Vector(80, 40),
        Vector(85, 10),
        Vector(85, 75),
        Vector(90, 35),
        Vector(90, 55),
        Vector(95, 100),
        Vector(105, 70)
      ),
      Edges(
        List(
          Edge(9, 17),
          Edge(17, 22),
          Edge(22, 27),
          Edge(27, 19),
          Edge(19, 14),
          Edge(14, 8),
          Edge(8, 9),
          Edge(22, 28),
          Edge(28, 30),
          Edge(9, 6),
          Edge(6, 4),
          Edge(19, 23),
          Edge(23, 24),
          Edge(24, 20),
          Edge(20, 21),
          Edge(14, 10),
          Edge(10, 11),
          Edge(11, 15),
          Edge(15, 16),
          Edge(23, 29),
          Edge(29, 32),
          Edge(10, 7),
          Edge(7, 2),
          Edge(24, 33),
          Edge(33, 35),
          Edge(11, 3),
          Edge(3, 0),
          Edge(21, 25),
          Edge(25, 26),
          Edge(26, 18),
          Edge(18, 13),
          Edge(13, 12),
          Edge(12, 16),
          Edge(15, 5),
          Edge(5, 1),
          Edge(20, 31),
          Edge(31, 34),
          Edge(16, 21)
        )
      )
    )

    val epsilon = 180000
  }

}
