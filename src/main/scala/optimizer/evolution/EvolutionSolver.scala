package icfpc21.classified
package optimizer.evolution

import model.{Problem, Solution}
import optimizer.Scorer
import optimizer.genes._
import solver.{Solver, SolverListener}

class EvolutionSolver(solverListener: SolverListener) extends Solver {
  val speciesCount = 30
  val GenerationsCount = 600

  val genes: Seq[GeneGenerator] = Seq(
    TranslateGene,
    RotateGene,
    JointRotateGene,
    AxeMirrorGene,
    MirrorGene,
    MovePointGene
//    MoveOutsidePointGene
  )

  override def solve(problem: Problem): Solution = {
    def printScore(generation: Int, score: Scorer.Score): Unit = {
      println(
        s"## Generation $generation: Best score: ${score.total}, " +
          s"fits: ${score.fits}, " +
          s"valid: ${score.valid}, " +
          s"outside: ${score.outsideArea}, " +
          s"bonuses: ${score.bonusPoints}, " +
          s"dislikes: ${score.dislikes}           ### " + Solution(score.figure.vertices)
      )
    }

    def isFinished(score: Scorer.Score): Boolean = {
      score.fits &&
      score.valid &&
      score.dislikes == 0d &&
      score.bonusPoints == 0d
    }
    var lastBest = Scorer.score(problem.figure, problem, false)
    printScore(0, lastBest)

    var population = Population(
      Seq.fill(speciesCount)(Spices(Seq(Agent(problem, genes.flatMap(_.forProblem(problem))))))
    )
    var generation = 0
    var finished = false

    while (generation < GenerationsCount && !finished) {
      generation += 1
      val newGeneration = population.cross
      val mutated = newGeneration.mutate
      val selected = mutated.select
      solverListener.candidates(selected.bestScores.take(20).map(_.score), problem.bonuses, generation)
      val bestScore = selected.bestScores.head
      if (lastBest.total != bestScore.score.total || generation % 20 == 0) {
        printScore(generation, bestScore.score)
      }
      lastBest = bestScore.score
      finished = isFinished(selected.bestScores.head.score)

      population = selected
    }

    val result = Solution(population.bestScores.head.figure.vertices)
    solverListener.solution(result)
    result
  }
}
