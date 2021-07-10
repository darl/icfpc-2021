package icfpc21.classified
package optimizer.mutators

import icfpc21.classified.model.{Figure, Hole, Problem, Vector}
import icfpc21.classified.optimizer.Mutator

import scala.util.Random

object RotateMutator extends Mutator {
  override def mutate(figure: Figure, problem: Problem, speed: Double): Figure = {
    import problem.hole
    val angle = Random.nextDouble() * 2 * math.Pi

    val holeCenter = Vector(
      x = Math.round((hole.points.map(_.x).max + hole.points.map(_.x).min) / 2.0).toInt,
      y = Math.round((hole.points.map(_.y).max + hole.points.map(_.y).min) / 2.0).toInt
    )

    figure.copy(vertices = figure.vertices.map(v => v.rotateAround(holeCenter, angle)))
  }
}
