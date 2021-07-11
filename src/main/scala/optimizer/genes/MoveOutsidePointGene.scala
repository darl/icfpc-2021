package icfpc21.classified
package optimizer.genes

import model._
import optimizer.evolution.{Gene, GeneGenerator}

import scala.util.Random

case class MoveOutsidePointGene(hole: Hole, pointIdx: Int, moveX: Boolean, moveY: Boolean, factor: Double)
    extends Gene {
  override def modify(figure: Figure): Figure = {
    val point = figure.vertices(pointIdx)
    if (!hole.isInside(point)) return figure
    val xMove = Random.nextInt(hole.xDiff) * factor * (if (moveY) 1d else -1d)
    val yMove = Random.nextInt(hole.yDiff) * factor * (if (moveY) 1d else -1d)
    val moveVector = Vector(xMove.round.toInt, yMove.round.toInt)

    figure.updateVertex(pointIdx, _ + moveVector)
  }

  override def mutate: Gene = {
    this.copy(
      factor = factor + (Random.nextDouble() / 10 - 0.05),
      moveX = Random.nextBoolean(),
      moveY = Random.nextBoolean()
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
        factor = Random.nextDouble(),
        moveY = Random.nextBoolean(),
        moveX = Random.nextBoolean()
      )
    }
  }
}
