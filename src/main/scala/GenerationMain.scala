package icfpc21.classified

import icfpc21.classified.api.PosesClient
import icfpc21.classified.optimizer.GenerationalSolver

object GenerationMain extends App {
  val problemId = 20 // args(1).toInt
  val client = new PosesClient.Live("https://poses.live", Key.value)

  val problem = client.getProblem(problemId)
  println("Eps = " + problem.epsilon)
  val solution = new GenerationalSolver().solve(problem)

  println(solution)
}
