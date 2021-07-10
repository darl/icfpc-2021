package icfpc21.classified
package optimizer

import icfpc21.classified.model.{Figure, Hole, Problem, Solution}
import icfpc21.classified.optimizer.mutators._
import icfpc21.classified.utils.RichIterable
import icfpc21.classified.solver.{Solver, SolverListener}

class GenerationalSolver(solverListener: SolverListener) extends Solver {
  val speciesCount = 30
  val ChildrenPerGeneration = 600
  val MutationsPerChild = 4
  val GenerationsCount = 600

  val mutators: Seq[Mutator] = Seq(
    MirrorMutator,
    MovePointMutator,
    MovePointToEdgeCenterMutator,
    MovePointToEdgeCornerMutator,
    MovePointToCenterMutator,
    IdentityMutator,
    SmallMovePointMutator,
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
    }
  }

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

    def isFinished(best: Figure): Boolean = {
      Scorer.checkFits(best, problem.hole) &&
      Scorer.checkStretchingIsOk(best, problem) &&
      Scorer.scoreDislikes(best, problem.hole) == 0d
    }
    var lastBest = Scorer.score(problem.figure, problem)
    printScore(0, lastBest)

    var population = Population(Seq.fill(speciesCount)(Spices(Seq(lastBest))))
    var generation = 0
    var finished = false

    while (generation < GenerationsCount && !finished) {
      generation += 1
      val newGeneration = population.mutate(problem)
      val selected = newGeneration.select
      solverListener.candidates(selected.bestScores.map(_.figure).reverse, problem.bonuses, generation)
      val bestScore = selected.bestScores.last
      if (lastBest.total != bestScore.total || generation % 20 == 0) {
        printScore(generation, bestScore)
      }
      lastBest = bestScore
      finished = isFinished(selected.bestScores.last.figure)

      population = selected
    }

    val result = Solution(population.bestScores.last.figure.vertices)
    solverListener.solution(result)
    result
  }

  case class Population(species: Seq[Spices]) {

    lazy val bestScores: Seq[Scorer.Score] = species.map(_.bestOne).sortBy(_.total)

    def mutate(problem: Problem): Population = {
      Population(
        species = species.map(s => s.mutate(problem))
      )
    }

    def select: Population = {
      Population(
        species = bestScores.drop(1).map { score =>
          Spices(Seq(score))
        } :+ Spices(Seq(bestScores.last))
      )
    }
  }

  case class Spices(members: Seq[Scorer.Score]) {
    lazy val bestOne: Scorer.Score = members.last

    def mutate(problem: Problem): Spices = {
      Spices(
        members = members
          .flatMap(f => generate(f.figure, problem.hole))
          .distinct
          .map(f => Scorer.score(f, problem))
          .sortBy(_.total)
      )
    }
  }
}
