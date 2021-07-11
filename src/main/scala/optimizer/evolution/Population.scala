package icfpc21.classified
package optimizer.evolution

import scala.collection.parallel.CollectionConverters._

case class Population(species: Seq[Spices]) {

  lazy val bestScores: Seq[Agent] = species.map(_.bestOne).sortBy(_.score.total)

  def mutate: Population = {
    Population(
      species = species.par.map(s => s.mutate).seq
    )
  }

  def select: Population = {
    Population(
      species = bestScores.drop(1).map { score =>
        Spices(Seq(score))
      } :+ Spices(Seq(bestScores.last))
    )
  }
}
