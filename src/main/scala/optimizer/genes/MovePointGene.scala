package icfpc21.classified

import model.{Figure, Problem, Vector}
import optimizer.evolution.{Gene, GeneGenerator}

import scala.util.Random

case class MovePointGene(index: Int, move: Vector) extends Gene {
  override def modify(figure: Figure): Figure = {
    figure.updateVertexes(Seq(index), (_, v) => v + move)
  }

  override def mutate: Gene = {
    val a = Random.nextDouble()
    val b = Random.nextDouble()
    val xMutate = if (a < 0.2) -1 else if (a > 0.8) 1 else 0
    val yMutate = if (b < 0.2) -1 else if (b > 0.8) 1 else 0
    import move._
    MovePointGene(index, move = Vector(x + xMutate, y + yMutate))
  }
}

object MovePointGene extends GeneGenerator {
  override def forProblem(problem: Problem): Seq[Gene] = {
    import problem._

    figure.vertices.indices.map { point =>
      val a = Random.nextDouble()
      val b = Random.nextDouble()
      val xMutate = if (a < 0.01) -1 else if (a > 0.99) 1 else 0
      val yMutate = if (b < 0.01) -1 else if (b > 0.99) 1 else 0
      val move = Vector(xMutate, yMutate)
      MovePointGene(point, move)
    }
  }
}
