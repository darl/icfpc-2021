package icfpc21.classified
package optimizer.evolution

import utils._
import scala.collection.parallel.CollectionConverters._

case class Population(species: Seq[Spices]) {

  lazy val bestScores: Seq[Agent] = species.map(_.bestOne).sortBy(_.score.total).reverse

  def cross: Population = {
    val agents = bestScores.zipWithIndex.flatMap {
      case (spicies, i) =>
        Seq.fill((bestScores.size - i) * 50)(Spices(Seq(spicies)))
    }
    Population(
      species = species.par.map { s =>
        Spices(Seq((1 to 100).par.map(_ => s.cross(agents.random)).flatMap(_.members).maxBy(_.score.total)))
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
