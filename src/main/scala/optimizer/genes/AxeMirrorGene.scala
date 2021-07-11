package icfpc21.classified
package optimizer.genes

import model._
import optimizer.evolution.{Gene, GeneGenerator}

import scala.util.Random
import utils._

case class AxeMirrorGene(problem: Problem, axesToMirror: Map[Axe, Seq[Seq[Int]]]) extends Gene {
  override def modify(figure: Figure): Figure = {
    axesToMirror.foldLeft(figure) {
      case (result, (axe, groups)) =>
        groups.foldLeft(result) {
          case (groupResult, group) =>
            groupResult.updateVertexes(
              group,
              (_, v) => v.mirror(figure.vertices(axe.aIndex), figure.vertices(axe.bIndex))
            )
        }
    }
  }

  override def mutate: Gene = {
    val axesToAdd = if (Random.nextBoolean()) {
      val newAxe = problem.figure.edges.analysis.axes.filterNot(axesToMirror.contains).random
      Seq(newAxe -> newAxe.subgroups.randomN(1))
    } else Seq.empty

    val axesToDelete = if (Random.nextBoolean()) {
      Seq(axesToMirror.keys.toSeq.random)
    } else Seq.empty

    val result = axesToMirror.filterNot {
      case (key, _) => axesToDelete.contains(key)
    } ++ axesToAdd.toMap

    AxeMirrorGene(
      problem,
      result.map {
        case (key, value) =>
      }
    )
  }
}

object AxeMirrorGene extends GeneGenerator {
  override def forProblem(problem: Problem): Seq[Gene] = {
    import problem._
    Seq(
      AxeMirrorGene(
        problem,
        figure.edges.analysis.axes
          .randomN(3)
          .map { axe =>
            axe -> axe.subgroups.randomN(2)
          }
          .toMap
      )
    )
  }
}
