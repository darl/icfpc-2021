package icfpc21.classified

import api.PosesClient
import optimizer.GenerationalSolver
import visualization.Visualizer
import model._

object GenerationMain extends App {
  val problemId = 2 // args(1).toInt
  val client = new PosesClient.Live("https://poses.live", Key.value)

  val checkpoints = Map(
    // dislikes: 247.0
    4 -> Solution(IndexedSeq(Vector(12,11), Vector(5,27), Vector(5,37), Vector(5,6), Vector(9,33), Vector(9,53), Vector(10,23), Vector(26,32), Vector(26,37), Vector(24,52), Vector(24,57), Vector(30,69), Vector(32,51), Vector(31,62), Vector(48,22), Vector(34,17), Vector(31,29), Vector(36,47), Vector(36,63), Vector(31,90), Vector(41,52), Vector(41,57), Vector(47,75), Vector(51,22), Vector(51,52), Vector(51,57), Vector(65,15), Vector(55,32), Vector(56,37), Vector(56,47), Vector(56,66), Vector(60,92), Vector(61,47), Vector(61,63), Vector(94,50), Vector(66,52), Vector(68,57), Vector(70,70), Vector(77,32), Vector(78,37), Vector(78,54), Vector(95,5), Vector(88,36))),
    34 -> Solution(IndexedSeq(Vector(49,67), Vector(51,39), Vector(26,56), Vector(59,12), Vector(50,40), Vector(25,27), Vector(2,43), Vector(49,12), Vector(24,25))),
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
  client.submitSolution(problemId, solution)
}
