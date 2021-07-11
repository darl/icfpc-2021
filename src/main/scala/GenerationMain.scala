package icfpc21.classified

import api.PosesClient
import optimizer.Scorer
import optimizer.evolution.EvolutionSolver
import visualization.Visualizer

object GenerationMain extends App {
  val problemId = 88 // args(1).toInt
  val client = new PosesClient.Live("https://poses.live", Tokens.value, Tokens.session)

  val problem = client.getProblem(problemId)

  println("Eps = " + problem.epsilon)

  val visualizer = Visualizer(problem)
  val t = new Thread(() => visualizer.show())
  t.start()
  val solution = new EvolutionSolver(visualizer).solve(problem)
  t.join()

  println(solution)
  val score = Scorer.score(problem.figure.copy(vertices = solution.vertices), problem, true)
  if (score.valid && score.fits) {
    val lastSubmission = client.getLastSubmission(problemId)
    if (
      lastSubmission.isEmpty || lastSubmission.get.dislikes.isEmpty || lastSubmission.get.dislikes.get > score.dislikes
    ) {
      println(s"Sending solution")
      println(s"Last submission is worse: $lastSubmission")
      client.submitSolution(problemId, solution)
    } else {
      println(s"Last submission is better: $lastSubmission")
    }
  } else {
    println("Ignored solution")
  }

}
