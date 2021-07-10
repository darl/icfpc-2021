package icfpc21.classified
package optimizer.mutators

import icfpc21.classified.model.{Figure, Hole, Problem, Vector}
import icfpc21.classified.optimizer.Mutator
import optimizer.mutators.TensionMutator.{Force, k}

import scala.util.Random

case class TensionMutator(init: Problem) extends Mutator {
  override def mutate(figure: Figure, hole: Hole, speed: Double): Figure = {
    val pointIdx = Random.nextInt(figure.vertices.size)
    val chosenPoint = figure.vertices(pointIdx)

    val currentForce = figureTensionForce(pointIdx, chosenPoint)(figure)
    val move = currentForce.scale(speed)

    figure.updateVertex(pointIdx, _ + move)
  }

  private def figureTensionForce(chosenIdx: Int, point: Vector)(figure: Figure): Force = {
    init.figure.edges.analysis.links.getOrElse(chosenIdx, Seq.empty).foldLeft(Force.empty) {
      case (force, destinationIdx) =>
        val destinationPoint = figure.vertices(destinationIdx)
        val currentEdge = destinationPoint - point
        val currentDistance = currentEdge.length
        val initialDistance = (init.figure.vertices(destinationIdx) - init.figure.vertices(chosenIdx)).length

        val currentForce = currentEdge.scale(currentEdge.length * k * currentDistance / initialDistance)
        force + currentForce
    }
  }
}

object TensionMutator {
  type Force = Vector
  object Force {
    val empty = Vector.Zero
  }

  private final val k = 1
}
