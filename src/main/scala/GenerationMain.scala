package icfpc21.classified

import icfpc21.classified.api.PosesClient
import icfpc21.classified.optimizer.GenerationalSolver
import icfpc21.classified.solver.SolverListener

object GenerationMain extends App {
  val problemId = 4 // args(1).toInt
  val client = new PosesClient.Live("https://poses.live", Key.value)

  val problem = client.getProblem(problemId)
  println("Eps = " + problem.epsilon)
  val solution = new GenerationalSolver(SolverListener.NoOp).solve(problem)

  println(solution)
}
