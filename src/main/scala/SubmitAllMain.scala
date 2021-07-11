package icfpc21.classified

import icfpc21.classified.api.PosesClient
import icfpc21.classified.optimizer.{GenerationalSolver, Scorer}
import icfpc21.classified.solver.SolverListener

object SubmitAllMain extends App {
  val problems = 1 to 132
  problems.foreach { problemId =>
    val client = new PosesClient.Live("https://poses.live", Tokens.value, Tokens.session)

    val problem = client.getProblem(problemId)

    println(s"ProblemId = $problemId Eps = ${problem.epsilon}")

    val solution = new GenerationalSolver(SolverListener.NoOp).solve(problem)

    println(solution)
    val score = Scorer.score(problem.figure.copy(vertices = solution.vertices), problem, true)
    if (score.valid && score.fits) {
      val lastSubmission = client.getLastSubmission(problemId)
      if (
        lastSubmission.isEmpty || lastSubmission.get.dislikes.isEmpty || lastSubmission.get.dislikes.get >= score.dislikes
      ) {
        println(s"Sending solution for $problemId")
        println(s"Last submission is worse: $lastSubmission")
        try client.submitSolution(problemId, solution)
        catch {
          case _: Throwable => println("Ignored exception")
        }
      } else {
        println(s"Last submission is better: $lastSubmission")
      }
    } else {
      println(s"Ignored solution for $problemId")
    }

  }
}
