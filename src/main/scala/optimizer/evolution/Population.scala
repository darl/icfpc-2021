package icfpc21.classified
package optimizer.evolution

import scala.collection.parallel.CollectionConverters._

case class Population(species: Seq[Spices]) {

  lazy val bestScores: Seq[Agent] = species.map(_.bestOne).sortBy(-_.score.total)

  def cross: Population = {
    Population(
      species = species.par.map { s =>
        Spices(Seq(species.map(s1 => s.cross(s1)).flatMap(_.members).maxBy(_.score.total)))
      }.seq
    )
  }

  def mutate: Population = {
    Population(
      species = species.map(s => s.mutate)
    )
  }

  def select: Population = {
    Population(
      species = bestScores.map { agent =>
        Spices(Seq(agent))
      }
    )
  }
}
