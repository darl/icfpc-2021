package icfpc21.classified
package optimizer.genes

import model.{Figure, Problem}
import optimizer.evolution.{Gene, GeneGenerator}

import scala.util.Random

case class MirrorGene(mirrorX: Boolean, mirrorY: Boolean) extends Gene {
  override def modify(figure: Figure): Figure = {
    val x = if (mirrorX) {
      val xMiddle = (figure.vertices.map(_.x).max + figure.vertices.map(_.x).min) / 2
      figure.copy(vertices = figure.vertices.map(v => v.copy(x = 2 * xMiddle - v.x)))
    } else figure
    val y = if (mirrorY) {
      val yMiddle = (x.vertices.map(_.y).max + x.vertices.map(_.y).min) / 2
      x.copy(vertices = x.vertices.map(v => v.copy(y = 2 * yMiddle - v.y)))
    } else x
    y
  }

  override def mutate: Gene = {
    MirrorGene(Random.nextBoolean(), Random.nextBoolean())
  }
}

object MirrorGene extends GeneGenerator {
  override def forProblem(problem: Problem): Seq[Gene] = {
    Seq(MirrorGene(Random.nextBoolean(), Random.nextBoolean()))
  }
}
