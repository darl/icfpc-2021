package icfpc21.classified
package optimizer.mutators

import icfpc21.classified.model.{Figure, Hole, Problem}
import icfpc21.classified.optimizer.Mutator

case class SeqMutator(mutators: Mutator*) extends Mutator {
  override def mutate(figure: Figure, problem: Problem, speed: Double): Figure = {
    mutators.foldLeft(figure)((f, m) => m.mutate(f, problem, speed))
  }
}
