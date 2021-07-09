package icfpc21.classified

import visualization.Visualizer
import model._

object Main extends App {
  Visualizer(
    Problem(
      hole = Hole(
        points = Seq(
          Vector(45, 80),
          Vector(35, 95),
          Vector(5, 95),
          Vector(35, 50),
          Vector(5, 5),
          Vector(35, 5),
          Vector(95, 95),
          Vector(65, 95),
          Vector(55, 80)
        )
      ),
      figure = Figure(
        edges = Seq(
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
        ),
        vertices = Seq(
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
        )
      ),
      epsilon = 1
    )
  ).show()
}