package icfpc21.classified
package optimizer.genes

import optimizer.evolution.{Gene, GeneGenerator}

import icfpc21.classified.model.{Figure, Problem, Vector}

import scala.util.Random

case class MovePointsToCenterGene(center: Vector, point: Int, move: Boolean) extends Gene {
  override def modify(figure: Figure): Figure = {
    if (!move) return figure
    val vertex = figure.vertices(point)
    val toCenter = center - vertex
    val delta =
      if (toCenter.x > toCenter.y && toCenter.y != 0) Vector(math.round(toCenter.x.toDouble / toCenter.y).toInt, 1)
      else Vector(1, math.round(toCenter.y.toDouble / toCenter.x).toInt)
    val result = vertex + delta

    figure.updateVertex(point, _ => result)
  }

  override def mutate: Gene = this.copy(move = move ^ (Random.nextDouble() < 0.3))
}

object MovePointsToCenterGene extends GeneGenerator {
  override def forProblem(problem: Problem): Seq[Gene] = {
    import problem._

    figure.vertices.indices.map { idx =>
      MovePointsToCenterGene(hole.center, idx, Random.nextDouble() < 0.001)
    }
  }
}
