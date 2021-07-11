package icfpc21.classified

import api.PosesClient
import optimizer.Scorer
import optimizer.evolution.EvolutionSolver
import visualization.Visualizer

object GenerationMain extends App {
  val problemId = 1 // args(1).toInt
  val client = new PosesClient.Live("https://poses.live", Key.value)

  val problem = client.getProblem(problemId)
  println("Eps = " + problem.epsilon)
  println(problem.figure.edges.analysis.axes.size)
  problem.figure.edges.analysis.axes.foreach(println)
  val visualizer = Visualizer(problem)
  val t = new Thread(() => visualizer.show())
  t.start()
  val solution = new EvolutionSolver(visualizer).solve(problem)
  t.join()

  println(solution)
  val score = Scorer.score(problem.figure.copy(vertices = solution.vertices), problem, true)
  if (score.valid && score.fits) {
    println("Sending solution")
    //client.submitSolution(problemId, solution)
  } else {
    println("Ignored solution")
  }

}
