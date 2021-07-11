package icfpc21.classified

import icfpc21.classified.api.PosesClient
import icfpc21.classified.optimizer.{GenerationalSolver, Scorer}
import icfpc21.classified.visualization.Visualizer

object GenerationMain extends App {
  val problemId = 88 // args(1).toInt
  val client = new PosesClient.Live("https://poses.live", Key.value)

  val problem = client.getProblem(problemId)
  println("Eps = " + problem.epsilon)
  println(problem.figure.edges.analysis.axes.size)
  problem.figure.edges.analysis.axes.foreach(println)
  val visualizer = Visualizer(problem)
  val t = new Thread(() => visualizer.show())
  t.start()
  val solution = new GenerationalSolver(visualizer).solve(problem)
  t.join()

  println(solution)
  val score = Scorer.score(problem.figure.copy(vertices = solution.vertices), problem, true)
  if (score.valid && score.fits) {
    println("Sending solution")
    client.submitSolution(problemId, solution)
  } else {
    println("Ignored solution")
  }

}
