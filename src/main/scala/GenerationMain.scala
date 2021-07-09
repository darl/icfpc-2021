package icfpc21.classified

import icfpc21.classified.api.PosesClient
import icfpc21.classified.model._
import icfpc21.classified.optimizer.GenerationalSolver
import icfpc21.classified.solver.SolverListener

object GenerationMain extends App {
  val problemId = 5 // args(1).toInt
  val client = new PosesClient.Live("https://poses.live", Key.value)

  val checkpoints = Map(
    // dislikes: 634.0
    4 -> Solution(List(Vector(95,11), Vector(94,27), Vector(94,37), Vector(89,7), Vector(89,32), Vector(85,53), Vector(89,22), Vector(74,32), Vector(74,37), Vector(74,52), Vector(74,57), Vector(71,67), Vector(69,47), Vector(69,62), Vector(50,26), Vector(64,21), Vector(64,32), Vector(64,47), Vector(64,62), Vector(65,83), Vector(59,52), Vector(59,57), Vector(53,67), Vector(49,22), Vector(49,52), Vector(49,57), Vector(35,15), Vector(44,32), Vector(44,37), Vector(44,47), Vector(44,62), Vector(44,85), Vector(39,47), Vector(39,62), Vector(5,50), Vector(34,52), Vector(34,57), Vector(31,70), Vector(24,32), Vector(24,37), Vector(24,52), Vector(6,8), Vector(14,37)))
  )

  val problem = client.getProblem(problemId)
  println("Eps = " + problem.epsilon)
  val problem2 = checkpoints.get(problemId) match {
    case Some(checkpoint) => problem.copy(figure = problem.figure.copy(vertices = checkpoint.vertices))
    case None => problem
  }
  val solution = new GenerationalSolver(SolverListener.NoOp).solve(problem2)

  println(solution)
}
