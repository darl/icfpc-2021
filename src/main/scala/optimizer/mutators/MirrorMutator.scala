package icfpc21.classified
package optimizer.mutators

import icfpc21.classified.model.{Figure, Hole}
import icfpc21.classified.optimizer.Mutator

import scala.util.Random

object MirrorMutator extends Mutator {
  override def mutate(figure: Figure, hole: Hole, speed: Double): Figure = {
    if (Random.nextBoolean()) {
      val xMiddle = (figure.vertices.map(_.x).max + figure.vertices.map(_.x).min) / 2
      figure.copy(vertices = figure.vertices.map(v => v.copy(x = 2 * xMiddle - v.x)))
    } else {
      val yMiddle = (figure.vertices.map(_.y).max + figure.vertices.map(_.y).min) / 2
      figure.copy(vertices = figure.vertices.map(v => v.copy(y = 2 * yMiddle - v.y)))
    }
  }
}
