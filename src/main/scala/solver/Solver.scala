package icfpc21.classified
package solver

import icfpc21.classified.model.{Problem, Solution}

trait Solver {
  def solve(problem: Problem): Solution
}
