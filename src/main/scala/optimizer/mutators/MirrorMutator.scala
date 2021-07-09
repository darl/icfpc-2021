package icfpc21.classified
package optimizer.mutators

import icfpc21.classified.model.{Figure, Hole}
import icfpc21.classified.optimizer.Mutator

import scala.util.Random

object MirrorMutator extends Mutator {
  override def mutate(figure: Figure, hole: Hole, speed: Double): Figure = {
    if (Random.nextBoolean())
      figure.copy(vertices = figure.vertices.map(v => v.copy(x = -v.x)))
    else
      figure.copy(vertices = figure.vertices.map(v => v.copy(y = -v.y)))
  }
}
