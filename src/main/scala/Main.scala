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
        vertices = Seq.empty,
        edges = Seq.empty
      ),
      epsilon = 1
    )
  ).show()
}
