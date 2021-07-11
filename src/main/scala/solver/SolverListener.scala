package icfpc21.classified
package solver

import icfpc21.classified.model.{BonusPoint, Figure, Solution}
import icfpc21.classified.optimizer.Scorer

trait SolverListener {
  def candidates(scores: Seq[Scorer.Score], bonuses: Seq[BonusPoint], generation: Int): Unit
  def solution(solution: Solution): Unit
}

object SolverListener {
  val NoOp = new SolverListener {

    override def candidates(scores: Seq[Scorer.Score], bonuses: Seq[BonusPoint], generation: Int): Unit = ()

    override def solution(solution: Solution): Unit = ()
  }
}
