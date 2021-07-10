package icfpc21.classified
package optimizer.mutators

import icfpc21.classified.model.{Figure, Hole}
import icfpc21.classified.optimizer.Mutator

case class SeqMutator(mutators: Mutator*) extends Mutator {
  override def mutate(figure: Figure, hole: Hole, speed: Double): Figure = {
    mutators.foldLeft(figure)((f, m) => m.mutate(f, hole, speed))
  }
}
