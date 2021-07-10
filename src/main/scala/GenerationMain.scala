package icfpc21.classified

import api.PosesClient
import optimizer.GenerationalSolver
import visualization.Visualizer
import model._

object GenerationMain extends App {
  val problemId = 4 // args(1).toInt
  val client = new PosesClient.Live("https://poses.live", Key.value)

  val checkpoints = Map(
    // dislikes: 397.0
    4 -> Solution(List(Vector(12,11), Vector(6,27), Vector(5,37), Vector(6,6), Vector(11,32), Vector(13,53), Vector(10,21), Vector(26,32), Vector(26,37), Vector(25,52), Vector(25,57), Vector(29,67), Vector(31,47), Vector(31,62), Vector(49,25), Vector(35,19), Vector(34,30), Vector(36,47), Vector(36,63), Vector(35,85), Vector(41,52), Vector(41,57), Vector(47,71), Vector(51,22), Vector(51,52), Vector(51,57), Vector(65,15), Vector(56,32), Vector(56,37), Vector(56,47), Vector(56,63), Vector(58,89), Vector(61,47), Vector(61,62), Vector(95,50), Vector(66,52), Vector(67,57), Vector(69,70), Vector(76,32), Vector(77,37), Vector(77,52), Vector(95,5), Vector(86,34)))

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
