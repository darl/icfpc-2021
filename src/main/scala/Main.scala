package icfpc21.classified

import visualization.Visualizer
import model._

object Main extends App {
  Visualizer(
    Problem(
      hole = Hole(points =
        Seq(
          Vector(0, 0),
          Vector(0, 4),
          Vector(1, 5),
          Vector(0, 6),
          Vector(0, 10),
          Vector(10, 10),
          Vector(10, 0)
        )
      ),
      figure = Figure(
        vertices = IndexedSeq(
          Vector(0, 2),
          Vector(10, 5),
          Vector(0, 8)
        ),
        edges = Edges(Seq(Edge(0, 1), Edge(1, 2), Edge(2, 0)))
      ),
      epsilon = 1
    )
  ).show()
}
