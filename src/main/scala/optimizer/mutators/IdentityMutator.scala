package icfpc21.classified
package optimizer.mutators

import icfpc21.classified.model.{Figure, Hole, Problem}
import icfpc21.classified.optimizer.Mutator

object IdentityMutator extends Mutator {
  override def mutate(figure: Figure, problem: Problem, speed: Double): Figure = figure
}
