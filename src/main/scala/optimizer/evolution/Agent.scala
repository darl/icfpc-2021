package icfpc21.classified
package optimizer.evolution

import model._
import optimizer.Scorer

import Agent._

import scala.collection.parallel.CollectionConverters._
import scala.util.Random

case class Agent(problem: Problem, genes: Seq[Gene]) {
  val figure = genes.foldLeft(problem.figure) {
    case (result, gene) =>
      gene.modify(result)
  }

  val score = Scorer.score(figure, problem, false)

  def children: Seq[Agent] = {
    (0 until ChildrenPerGeneration).par.map { _ =>
      Agent(
        problem,
        genes = genes.map { gene =>
          if (Random.nextBoolean()) gene.mutate else gene
        }
      )
    }.seq
  }
}

object Agent {
  val ChildrenPerGeneration = 200
}
