package icfpc21.classified
package optimizer

import icfpc21.classified.model.{Figure, Hole, Problem, Solution}
import icfpc21.classified.optimizer.mutators.{IdentityMutator, MirrorMutator, MovePointMutator}
import icfpc21.classified.solver.Solver

import scala.util.Random

class GenerationalSolver extends Solver {
  val count = 500
  val ChildrenPerGeneration = 10
  val MutationsPerChild = 2
  val GenerationsCount = 500

  val mutators: Seq[Mutator] = Seq(
    MirrorMutator,
    MovePointMutator,
    IdentityMutator
  )

  def generate(figure: Figure, hole: Hole): Seq[Figure] = {
    (0 until ChildrenPerGeneration).map { _ =>
      (0 until MutationsPerChild).foldLeft(figure) { (f, _) =>
        val mIdx = Random.nextInt(mutators.size)
        val m = mutators(mIdx)
        m.mutate(f, hole, speed = 1d)
      }
    }
  }

  override def solve(problem: Problem): Solution = {
    def printScore(generation: Int, best: Figure): Unit = {
      println(
        s"## Generation $generation: Best score: ${Scorer.score(best, problem)}, " +
          s"fits: ${Scorer.checkFits(best, problem.hole)}, " +
          s"valid: ${Scorer.checkStretchingIsOk(best, problem)}, " +
          s"dislikes: ${Scorer.scoreDislikes(best, problem.hole)}"
      )
    }

    def isFinished(best: Figure): Boolean = {
      Scorer.checkFits(best, problem.hole) &&
      Scorer.checkStretchingIsOk(best, problem) &&
      Scorer.scoreDislikes(best, problem.hole) == 0d
    }
    printScore(0, problem.figure)

    var candidates = Seq.fill(count)(problem.figure)
    var generation = 0
    var finished = false
    while (generation < GenerationsCount && !finished) {
      generation += 1
      val newGeneration = candidates.flatMap(generate(_, problem.hole))
      val sorted = newGeneration.sortBy(f => Scorer.score(f, problem))
      val selected = sorted.takeRight(count)
      printScore(generation, selected.last)
      finished = isFinished(selected.last)

      candidates = selected
    }

    Solution(candidates.last.vertices)
  }
}
