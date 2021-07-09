package icfpc21.classified
package solver
import icfpc21.classified.model.{Problem, Solution}

object NoOpSolver extends Solver {
  override def solve(problem: Problem): Solution =
    Solution(problem.figure.vertices)
}
