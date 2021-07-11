package icfpc21.classified
package optimizer.genes

import model.{Figure, Problem, Vector}
import optimizer.evolution.{Gene, GeneGenerator}

import scala.util.Random

case class TranslateGene(move: Vector) extends Gene {
  override def modify(figure: Figure): Figure = {
    figure.updateVertexes(figure.vertices.indices, (_, v) => v + move)
  }

  override def mutate: Gene = {
    val xMutate = Random.nextInt(3) - 1
    val yMutate = Random.nextInt(3) - 1
    import move._
    TranslateGene(move = Vector(x + xMutate, y + yMutate))
  }
}

object TranslateGene extends GeneGenerator {
  override def forProblem(problem: Problem): Seq[Gene] = {
    import problem._
    val xDiff = hole.points.map(_.x).max - hole.points.map(_.x).min + 1
    val yDiff = hole.points.map(_.y).max - hole.points.map(_.y).min + 1

    val xMove = Random.nextInt(xDiff) * Random.nextDouble() * (if (Random.nextBoolean()) 1d else -1d)
    val yMove = Random.nextInt(yDiff) * Random.nextDouble() * (if (Random.nextBoolean()) 1d else -1d)
    val move = Vector(xMove.round.toInt, yMove.round.toInt)
    Seq(TranslateGene(move))
  }
}
