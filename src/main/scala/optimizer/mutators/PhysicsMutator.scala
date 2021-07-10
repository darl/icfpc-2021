package icfpc21.classified
package optimizer.mutators

import icfpc21.classified.model.{Figure, Hole, Problem, Vector}
import icfpc21.classified.optimizer.Mutator
import optimizer.mutators.PhysicsMutator.Force

import scala.util.Random

case class PhysicsMutator(force: (Int, Vector) => (Figure, Hole) => Force) extends Mutator {
  override def mutate(figure: Figure, hole: Hole, speed: Double): Figure = {
    val pointIdx = Random.nextInt(figure.vertices.size)
    val chosenPoint = figure.vertices(pointIdx)

    val currentForce = force(pointIdx, chosenPoint)(figure, hole)
    val move = currentForce.scale(speed)

    figure.updateVertex(pointIdx, _ + move)
  }
}

object PhysicsMutator {
  type Force = Vector
  object Force {
    val empty = Vector.Zero
  }

  def figureTensionForce(init: Problem): (Int, Vector) => (Figure, Hole) => Force = {
    val edges = init.figure.edges.values
    val destinations: Map[Int, Seq[Int]] =
      (edges ++ edges.map(_.swap))
        .groupBy(_.aIndex)
        .view
        .mapValues { edges =>
          edges.map(_.bIndex)
        }
        .toMap

    (chosenIdx: Int, point: Vector) =>
      (figure: Figure, _: Hole) => {
        destinations.getOrElse(chosenIdx, Seq.empty).foldLeft(Force.empty) {
          case (force, destinationIdx) =>
            val destinationPoint = figure.vertices(destinationIdx)
            val currentEdge = destinationPoint - point
            val currentDistance = currentEdge.length
            val initialDistance = (init.figure.vertices(destinationIdx) - init.figure.vertices(chosenIdx)).length
            val delta = currentDistance - initialDistance

            // https://ru.wikipedia.org/wiki/%D0%97%D0%B0%D0%BA%D0%BE%D0%BD_%D0%93%D1%83%D0%BA%D0%B0
            val hooke = k * delta
            val currentForce = currentEdge.scale(1 / currentEdge.length * hooke)
            force + currentForce
        }
      }
  }

  private final val k = 1
}
