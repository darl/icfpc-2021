package icfpc21.classified
package solver

import model.{Figure, Problem, Solution}

trait SolverListener {
  def start(problem: Problem): Unit
  def candidates(figures: Seq[Figure]): Unit
  def solution(solution: Solution): Unit
}

object SolverListener {
  val NoOp = new SolverListener {
    override def start(problem: Problem): Unit = ()

    override def candidates(figures: Seq[Figure]): Unit = ()

    override def solution(solution: Solution): Unit = ()
  }
}
