package icfpc21.classified
package optimizer

import icfpc21.classified.model.{Figure, Problem, Solution}
import icfpc21.classified.solver.Solver

class GenerationalSolver extends Solver {
  val count = 50
  val mutationsPerGeneration = 3
  val generations = 50

  val mutators = Seq()

  def generate(figure: Figure): Seq[Figure] = Seq(figure)

  override def solve(problem: Problem): Solution = {
    var candidates = Seq.fill(count)(problem.figure)

    (0 until generations).foreach { _ =>
      val newGeneration = candidates.flatMap(generate)
      val sorted = newGeneration.sortBy(f =>
        Scorer.score(f, problem.hole)(problem.epsilon)
      )
      val selected = sorted.takeRight(count)
      candidates = selected
    }

    Solution(candidates.last.vertices)
  }
}
