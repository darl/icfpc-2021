package icfpc21.classified
package optimizer.evolution

import model.{Figure, Problem}

trait Gene {
  def modify(figure: Figure): Figure
  def mutate: Gene
}

trait GeneGenerator {
  def forProblem(problem: Problem): Seq[Gene]
}
