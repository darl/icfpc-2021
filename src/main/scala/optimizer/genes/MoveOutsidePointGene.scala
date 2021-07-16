package icfpc21.classified
package optimizer.genes

import model._
import optimizer.evolution.{Gene, GeneGenerator}

import scala.util.Random

case class MoveOutsidePointGene(hole: Hole, pointIdx: Int, move: Boolean, factor: Double) extends Gene {
  override def modify(figure: Figure): Figure = {
    val point = figure.vertices(pointIdx)
    if (!hole.isInside(point)) return figure
    val xMove = Random.nextInt(hole.xDiff) * factor * (if (move) 1d else 0)
    val yMove = Random.nextInt(hole.yDiff) * factor * (if (move) 1d else 0)
    val moveVector = Vector(xMove.round.toInt, yMove.round.toInt)

    figure.updateVertex(pointIdx, _ + moveVector)
  }

  override def mutate: Gene = {
    this.copy(
      factor = factor + (Random.nextDouble() / 100 - 0.05),
      move = move ^ (Random.nextDouble() < 0.3)
    )
  }
}

object MoveOutsidePointGene extends GeneGenerator {
  override def forProblem(problem: Problem): Seq[Gene] = {
    import problem._

    figure.vertices.indices.map { point =>
      MoveOutsidePointGene(
        hole = problem.hole,
        pointIdx = point,
        factor = Random.nextDouble() / 100 - 0.05,
        move = Random.nextDouble() < 0.005
      )
    }
  }
}
