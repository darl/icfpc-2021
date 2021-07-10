package icfpc21.classified
package solver

import model.{BonusPoint, Figure, Problem, Solution}

trait SolverListener {
  def candidates(figures: Seq[Figure], bonuses: Seq[BonusPoint], generation: Int): Unit
  def solution(solution: Solution): Unit
}

object SolverListener {
  val NoOp = new SolverListener {

    override def candidates(figures: Seq[Figure], bonuses: Seq[BonusPoint], generation: Int): Unit = ()

    override def solution(solution: Solution): Unit = ()
  }
}
