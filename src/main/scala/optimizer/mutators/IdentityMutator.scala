package icfpc21.classified
package optimizer.mutators

import icfpc21.classified.model.{Figure, Hole}
import icfpc21.classified.optimizer.Mutator

object IdentityMutator extends Mutator {
  override def mutate(figure: Figure, hole: Hole, speed: Double): Figure = figure
}
