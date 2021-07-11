package icfpc21.classified
package optimizer.evolution

import model._
import optimizer.Scorer

import Agent._

import scala.collection.parallel.CollectionConverters._
import scala.util.Random

case class Agent(problem: Problem, genes: Seq[Gene]) {
  lazy val figure = genes.foldLeft(problem.figure) {
    case (result, gene) =>
      gene.modify(result)
  }

  lazy val score = Scorer.score(figure, problem, false)

  def cross(other: Agent) = {
    Agent(
      problem,
      genes.zip(other.genes).map {
        case (a, b) =>
          if (Random.nextDouble() < 0.02) a else b
      }
    )
  }

  def children: Seq[Agent] = {
    (0 until ChildrenPerGeneration).par.map { _ =>
      Agent(
        problem,
        genes = genes.par.map { gene =>
          if (Random.nextDouble() < 0.05) gene.mutate else gene
        }.seq
      )
    }.seq ++ Seq(this)
  }
}

object Agent {
  val ChildrenPerGeneration = 50
}
