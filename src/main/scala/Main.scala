package icfpc21.classified

import visualization.Visualizer
import model._

object Main extends App {
  Visualizer(
    Problem(
      hole = Hole(points =
        Seq(
          Vector(0, 0),
          Vector(0, 10),
          Vector(10, 10),
          Vector(10, 0)
        )
      ),
      figure = Figure(
        vertices = Seq(
          Vector(1, 1),
          Vector(30, 1),
          Vector(2, 3)
        ),
        edges = Seq(Edge(0, 1), Edge(1, 2), Edge(2, 0))
      ),
      epsilon = 1
    )
  ).show()
}
