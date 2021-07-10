package icfpc21.classified
package optimizer.mutators

import icfpc21.classified.model.{Figure, Hole, Vector}
import icfpc21.classified.optimizer.Mutator

import scala.util.Random

object MovePointToEdgeCenterMutator extends Mutator {

  override def mutate(figure: Figure, hole: Hole, speed: Double): Figure = {
    val pointIdx = Random.nextInt(figure.vertices.size)
    val vertex = figure.vertices(pointIdx)

    val edgeIdx = Random.nextInt(hole.points.size)
    val edgeCenter = (hole.points(edgeIdx) + hole.points((edgeIdx + 1) % hole.points.size)).scale(0.5)

    val toEdge = edgeCenter - vertex
    val delta =
      if (toEdge.x > toEdge.y && toEdge.y != 0) Vector(math.round(toEdge.x.toDouble / toEdge.y).toInt, 1)
      else Vector(1, math.round(toEdge.y.toDouble / toEdge.x).toInt)
    val result = vertex + delta

    figure.copy(vertices = figure.vertices.zipWithIndex.map {
      case (vector, i) => if (i == pointIdx) result else vector
    })
  }
}
