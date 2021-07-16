package icfpc21.classified
package optimizer.genes

import model._
import optimizer.evolution.{Gene, GeneGenerator}

import scala.util.Random
import utils._

case class AxeMirrorGene(problem: Problem, axe: Axe, subIndex: Int, mirror: Boolean) extends Gene {
  override def modify(figure: Figure): Figure = {
    if (mirror) {
      val a = figure.vertices(axe.aIndex)
      val b = figure.vertices(axe.bIndex)
      if (a != b) {
        figure.updateVertexes(
          axe.subgroups(subIndex),
          (_, v) => v.mirror(a, b)
        )
      } else figure
    } else figure
  }

  override def mutate: Gene = {
    this.copy(mirror = mirror ^ (Random.nextDouble() < 0.3))
  }
}

object AxeMirrorGene extends GeneGenerator {
  override def forProblem(problem: Problem): Seq[Gene] = {
    import problem._
    problem.figure.edges.analysis.axes.flatMap { axe =>
      axe.subgroups.indices.map { group =>
        AxeMirrorGene(problem, axe, group, Random.nextDouble() < 0.005)
      }
    }
  }
}
