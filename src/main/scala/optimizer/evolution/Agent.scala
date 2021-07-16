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
    val probability = (this.score.valid, other.score.valid) match {
      case (true, false) => 0.05
      case (false, true) => 0.2
      case _             => 0.1
    }
    Agent(
      problem,
      genes.zip(other.genes).map {
        case (a, b) =>
          if (Random.nextDouble() < probability) b else a
      }
    )
  }

  def children: Seq[Agent] = {
    val probability = (this.score.valid) match {
      case true => 0.6
      case _    => 0.1
    }
    (0 until ChildrenPerGeneration).par.map { _ =>
      Agent(
        problem,
        genes = genes.par.map { gene =>
          if (Random.nextDouble() < probability) gene.mutate else gene
        }.seq
      )
    }.seq ++ Seq(this)
  }
}

object Agent {
  val ChildrenPerGeneration = 50
}
