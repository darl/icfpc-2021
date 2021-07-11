package icfpc21.classified
package optimizer.mutators

import icfpc21.classified.model.{Figure, Problem, Vector}
import icfpc21.classified.optimizer.Mutator

import scala.util.Random

object TensionMutator extends Mutator {
  type Force = Vector
  object Force {
    val empty = Vector.Zero
  }

  private final val k = 1

  override def mutate(
      figure: Figure,
      problem: Problem,
      speed: Double
  ): Figure = {
    val pointIdx = Random.nextInt(figure.vertices.size)
    val chosenPoint = figure.vertices(pointIdx)

    val currentForce = figureTensionForce(problem)(pointIdx, chosenPoint)(figure)
    val move = currentForce.scale(speed)

    figure.updateVertex(pointIdx, _ + move)
  }

  private def figureTensionForce(problem: Problem)(chosenIdx: Int, point: Vector)(figure: Figure): Force = {
    problem.figure.edges.analysis.links.getOrElse(chosenIdx, Seq.empty).foldLeft(Force.empty) {
      case (force, destinationIdx) =>
        val destinationPoint = figure.vertices(destinationIdx)
        val currentEdge = destinationPoint - point
        val currentDistance = currentEdge.length
        val initialDistance = (problem.figure.vertices(destinationIdx) - problem.figure.vertices(chosenIdx)).length

        val currentForce = currentEdge.scale(currentEdge.length * k * currentDistance / initialDistance)
        force + currentForce
    }
  }
}
