package icfpc21.classified

import api.PosesClient
import optimizer.GenerationalSolver
import visualization.Visualizer
import model._

object GenerationMain extends App {
  val problemId = 4 // args(1).toInt
  val client = new PosesClient.Live("https://poses.live", Key.value)

  val checkpoints = Map(
    // dislikes: 385.0
    4 -> Solution(List(Vector(88,11), Vector(94,27), Vector(95,37), Vector(94,6), Vector(89,32), Vector(87,53), Vector(89,22), Vector(74,32), Vector(74,37), Vector(75,52), Vector(75,57), Vector(71,67), Vector(69,47), Vector(69,62), Vector(51,25), Vector(65,19), Vector(66,30), Vector(64,47), Vector(64,63), Vector(65,85), Vector(59,52), Vector(59,57), Vector(53,71), Vector(49,22), Vector(49,52), Vector(49,57), Vector(35,15), Vector(44,32), Vector(44,37), Vector(44,47), Vector(44,63), Vector(42,90), Vector(39,47), Vector(39,62), Vector(5,50), Vector(34,52), Vector(33,57), Vector(31,70), Vector(24,32), Vector(23,37), Vector(23,52), Vector(5,5), Vector(13,34)))

  )

  val problem = client.getProblem(problemId)
  println("Eps = " + problem.epsilon)
  val problem2 = checkpoints.get(problemId) match {
    case Some(checkpoint) => problem.copy(figure = problem.figure.copy(vertices = checkpoint.vertices))
    case None             => problem
  }
  val visualizer = Visualizer(problem2)
  val t = new Thread(() => visualizer.show())
  t.start()
  val solution = new GenerationalSolver(visualizer).solve(problem2)
  t.join()

  println(solution)
}
