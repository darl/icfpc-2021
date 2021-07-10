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
      edges = Edges(Seq(
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
      ))
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

}
