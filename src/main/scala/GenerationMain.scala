package icfpc21.classified

import api.PosesClient
import optimizer.{GenerationalSolver, Scorer}
import visualization.Visualizer
import model._

object GenerationMain extends App {
  val problemId = 88 // args(1).toInt
  val client = new PosesClient.Live("https://poses.live", Key.value)

  val checkpoints = Map[Int, Solution](
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
  val score = Scorer.score(problem2.figure.copy(vertices = solution.vertices), problem2, true)
  if (score.valid && score.fits) {
    println("Sending solution")
    client.submitSolution(problemId, solution)
  } else {
    println("Ignored solution")
  }

}
