package icfpc21.classified

import api.PosesClient
import optimizer.GenerationalSolver
import visualization.Visualizer
import model._

object GenerationMain extends App {
  val problemId = 4 // args(1).toInt
  val client = new PosesClient.Live("https://poses.live", Key.value)

  val checkpoints = Map(
    // dislikes: 288.0
    4 -> Solution(List(Vector(88,11), Vector(95,27), Vector(95,37), Vector(95,6), Vector(91,33), Vector(90,54), Vector(90,23), Vector(74,32), Vector(74,37), Vector(76,52), Vector(76,57), Vector(70,68), Vector(69,49), Vector(69,62), Vector(52,22), Vector(65,18), Vector(69,29), Vector(64,47), Vector(64,63), Vector(66,88), Vector(59,52), Vector(59,57), Vector(53,73), Vector(49,22), Vector(49,52), Vector(49,57), Vector(35,15), Vector(45,32), Vector(44,37), Vector(44,47), Vector(44,65), Vector(40,92), Vector(39,47), Vector(39,63), Vector(5,50), Vector(34,52), Vector(32,57), Vector(30,70), Vector(23,32), Vector(23,37), Vector(23,52), Vector(5,5), Vector(13,36)))

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
