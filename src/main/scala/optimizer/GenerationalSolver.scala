package icfpc21.classified
package optimizer

import icfpc21.classified.model.{Figure, Hole, Problem, Solution}
import icfpc21.classified.optimizer.mutators._
import icfpc21.classified.utils.RichIterable
import icfpc21.classified.solver.{Solver, SolverListener}

import scala.util.Random

class GenerationalSolver(solverListener: SolverListener) extends Solver {
  val count = 30
  val ChildrenPerGeneration = 100
  val MutationsPerChild = 8
  val GenerationsCount = 500

  val mutators: Seq[Mutator] = Seq(
    MirrorMutator,
    MovePointMutator,
    MovePointToEdgeCenterMutator,
    MovePointToEdgeCornerMutator,
    MovePointToCenterMutator,
    IdentityMutator,
    SmallMovePointMutator,
    SmallMovePointMutator,
    MoveOutsidePointMutator,
    MoveOutsidePointMutator,
    TranslateMutator,
    RotateMutator,
    JointRotateMutator,
    AxeMirrorMutator
  )

  def generate(figure: Figure, hole: Hole): Seq[Figure] = {
    (0 until ChildrenPerGeneration).map { _ =>
      (0 until MutationsPerChild).foldLeft(figure) { (f, _) =>
        val m = mutators.random
        m.mutate(f, hole, speed = 1d)
      }
    } ++ Seq(figure)
  }

  override def solve(problem: Problem): Solution = {
    def printScore(generation: Int, score: Scorer.Score): Unit = {
      println(
        s"## Generation $generation: Best score: ${score.total}, " +
          s"fits: ${score.fits}, " +
          s"valid: ${score.valid}, " +
          s"outside: ${score.outsideArea}, " +
          s"dislikes: ${score.dislikes}           ### " + Solution(score.figure.vertices)
      )
    }

    def isFinished(best: Figure): Boolean = {
      Scorer.checkFits(best, problem.hole) &&
      Scorer.checkStretchingIsOk(best, problem) &&
      Scorer.scoreDislikes(best, problem.hole) == 0d
    }
    var lastBest = Scorer.score(problem.figure, problem)
    printScore(0, lastBest)

    var candidates = Seq.fill(count)(problem.figure)
    var generation = 0
    var finished = false

    while (generation < GenerationsCount && !finished) {
      generation += 1
      val newGeneration = candidates.flatMap(generate(_, problem.hole)).distinct
      val sorted = newGeneration.map(f => Scorer.score(f, problem)).sortBy(f => f.total)
      val selected = sorted.takeRight(count)
      solverListener.candidates(selected.takeRight(20).map(_.figure), generation)
      val bestScore = selected.last
      if (lastBest.total != bestScore.total || generation % 20 == 0) {
        printScore(generation, selected.last)
      }
      lastBest = bestScore
      finished = isFinished(selected.last.figure)

      candidates = selected.map(_.figure)
    }

    val result = Solution(candidates.last.vertices)
    solverListener.solution(result)
    result
  }
}
