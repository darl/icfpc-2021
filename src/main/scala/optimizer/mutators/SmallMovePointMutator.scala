package icfpc21.classified
package optimizer.mutators

import icfpc21.classified.model.{Figure, Hole, Vector}
import icfpc21.classified.optimizer.Mutator

import scala.util.Random

object SmallMovePointMutator extends Mutator {
  override def mutate(figure: Figure, hole: Hole, speed: Double): Figure = {
    val pointIdx = Random.nextInt(figure.vertices.size)
    val xDiff = hole.points.map(_.x).max - hole.points.map(_.x).min + 1
    val yDiff = hole.points.map(_.y).max - hole.points.map(_.y).min + 1

    val xMove = Random.nextInt(1) * (if (Random.nextBoolean()) 1 else -1)
    val yMove = Random.nextInt(1) * (if (Random.nextBoolean()) 1 else -1)
    val move = Vector(xMove, yMove)

    figure.copy(vertices = figure.vertices.zipWithIndex.map {
      case (vector, i) => if (i == pointIdx) vector + move else vector
    })
  }
}
