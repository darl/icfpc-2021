package icfpc21.classified
package solver

import model.{Figure, Problem, Solution}

trait SolverListener {
  def candidates(figures: Seq[Figure]): Unit
  def solution(solution: Solution): Unit
}

object SolverListener {
  val NoOp = new SolverListener {

    override def candidates(figures: Seq[Figure]): Unit = ()

    override def solution(solution: Solution): Unit = ()
  }
}
