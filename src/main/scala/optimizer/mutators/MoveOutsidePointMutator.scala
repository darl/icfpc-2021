package icfpc21.classified
package optimizer.mutators

import icfpc21.classified.model.{Figure, Hole, Vector}
import icfpc21.classified.optimizer.Mutator

import scala.util.Random

object MoveOutsidePointMutator extends Mutator {
  override def mutate(figure: Figure, hole: Hole, speed: Double): Figure = {
    val outside = figure.vertices.filterNot(p => hole.isInside(p))
    if (outside.isEmpty) return figure
    val rndIdx = Random.nextInt(outside.size)
    val point = outside(rndIdx)
    val idx = figure.vertices.indexOf(point)
    val xDiff = hole.points.map(_.x).max - hole.points.map(_.x).min + 1
    val yDiff = hole.points.map(_.y).max - hole.points.map(_.y).min + 1

    val xMove = Random.nextInt(xDiff) * Random.nextDouble() * (if (Random.nextBoolean()) 1d else -1d)
    val yMove = Random.nextInt(yDiff) * Random.nextDouble() * (if (Random.nextBoolean()) 1d else -1d)
    val move = Vector(xMove.round.toInt, yMove.round.toInt)

    figure.updateVertex(idx, _ + move)
  }
}
