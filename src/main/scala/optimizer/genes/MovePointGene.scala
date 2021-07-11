package icfpc21.classified

import model.{Figure, Problem, Vector}
import optimizer.evolution.{Gene, GeneGenerator}

import scala.util.Random

case class MovePointGene(index: Int, move: Vector) extends Gene {
  override def modify(figure: Figure): Figure = {
    figure.updateVertexes(Seq(index), (_, v) => v + move)
  }

  override def mutate: Gene = {
    val xMutate = Random.nextInt(3) - 1
    val yMutate = Random.nextInt(3) - 1
    import move._
    MovePointGene(index, move = Vector(x + xMutate, y + yMutate))
  }
}

object MovePointGene extends GeneGenerator {
  override def forProblem(problem: Problem): Seq[Gene] = {
    import problem._
    val xDiff = hole.points.map(_.x).max - hole.points.map(_.x).min + 1
    val yDiff = hole.points.map(_.y).max - hole.points.map(_.y).min + 1

    figure.vertices.indices.map { point =>
      val xMove = Random.nextInt(xDiff) * Random.nextDouble() * (if (Random.nextBoolean()) 1d else -1d)
      val yMove = Random.nextInt(yDiff) * Random.nextDouble() * (if (Random.nextBoolean()) 1d else -1d)
      val move = Vector(xMove.round.toInt, yMove.round.toInt)
      MovePointGene(point, move)
    }
  }
}
