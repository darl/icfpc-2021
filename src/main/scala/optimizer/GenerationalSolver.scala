package icfpc21.classified
package optimizer

import icfpc21.classified.model.{Figure, Problem, Solution}
import icfpc21.classified.solver.Solver

import scala.util.Random

class GenerationalSolver extends Solver {
  val count = 50
  val mutationsPerGeneration = 3
  val GenerationsCount = 50

  val mutators = Seq.empty[Mutator]

  def generate(figure: Figure): Seq[Figure] = {
    (0 until mutationsPerGeneration).map { i =>
      val mIdx = Random.nextInt(mutators.size)
      val m = mutators(mIdx)
      m.mutate(figure, speed = 1d)
    }
  }

  override def solve(problem: Problem): Solution = {
    var candidates = Seq.fill(count)(problem.figure)
    var generation = 0
    while (generation < GenerationsCount) {
      val newGeneration = candidates.flatMap(generate)
      val sorted = newGeneration.sortBy(f => Scorer.score(f, problem))
      val selected = sorted.takeRight(count)
      val best = selected.last
      println(
        s"## Generation $generation: Best score: ${Scorer.score(best, problem)}, " +
          s"fits: ${Scorer.checkFits(best, problem.hole)}, " +
          s"valid: ${Scorer.checkStretchingIsOk(best, problem)}, " +
          s"dislikes: ${Scorer.scoreDislikes(best, problem.hole)}"
      )
      candidates = selected
      generation += 1
    }

    Solution(candidates.last.vertices)
  }
}
