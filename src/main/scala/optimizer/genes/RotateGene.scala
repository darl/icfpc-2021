package icfpc21.classified
package optimizer.genes

import model.{Figure, Problem, Vector}
import optimizer.evolution._

import scala.util.Random

case class RotateGene(center: Vector, angle: Double) extends Gene {
  override def modify(figure: Figure): Figure =
    figure.updateVertexes(figure.vertices.indices, (_, v) => v.rotateAround(center, angle))

  override def mutate: Gene = {
    val mutation = Random.nextDouble() * 2 - 1
    RotateGene(center, angle + mutation)
  }
}

object RotateGene extends GeneGenerator {
  override def forProblem(problem: Problem): Seq[Gene] = {
    import problem._
    val angle = Random.nextDouble() * 2 * math.Pi

    val holeCenter = Vector(
      x = Math.round((hole.points.map(_.x).max + hole.points.map(_.x).min) / 2.0).toInt,
      y = Math.round((hole.points.map(_.y).max + hole.points.map(_.y).min) / 2.0).toInt
    )

    Seq(RotateGene(holeCenter, angle))
  }
}
