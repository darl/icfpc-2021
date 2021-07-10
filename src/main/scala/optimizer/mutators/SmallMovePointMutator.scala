package icfpc21.classified
package optimizer.mutators

import icfpc21.classified.model.{Figure, Hole, Vector}
import icfpc21.classified.optimizer.Mutator

import scala.util.Random

object SmallMovePointMutator extends Mutator {
  override def mutate(figure: Figure, hole: Hole, speed: Double): Figure = {
    val pointIdx = Random.nextInt(figure.vertices.size)

    val xMove = Random.nextInt(1) * (if (Random.nextBoolean()) 1 else -1)
    val yMove = Random.nextInt(1) * (if (Random.nextBoolean()) 1 else -1)
    val move = Vector(xMove, yMove)

    figure.updateVertex(pointIdx, _ + move)
  }
}
