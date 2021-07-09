package icfpc21.classified
package optimizer.mutators

import icfpc21.classified.model.{Figure, Hole, Vector}
import icfpc21.classified.optimizer.Mutator

import scala.util.Random

object MovePointToCenterMutator extends Mutator {

  override def mutate(figure: Figure, hole: Hole, speed: Double): Figure = {
    val pointIdx = Random.nextInt(figure.vertices.size)
    val vertex = figure.vertices(pointIdx)

    val holeCenter = Vector(
      x = Math.round((hole.points.map(_.x).max + hole.points.map(_.x).min) / 2.0).toInt,
      y = Math.round((hole.points.map(_.y).max + hole.points.map(_.y).min) / 2.0).toInt
    )

    val toCenter = holeCenter - vertex
    val delta =
      if (toCenter.x > toCenter.y && toCenter.y != 0) Vector(math.round(toCenter.x.toDouble / toCenter.y).toInt, 1)
      else Vector(1, math.round(toCenter.y.toDouble / toCenter.x).toInt)
    val result = vertex + delta

    figure.copy(vertices = figure.vertices.zipWithIndex.map {
      case (vector, i) => if (i == pointIdx) result else vector
    })
  }
}
