package icfpc21.classified
package optimizer.evolution

import scala.collection.parallel.CollectionConverters._

case class Spices(members: Seq[Agent]) {
  lazy val bestOne: Agent = members.last

  def mutate: Spices = {
    Spices(
      members = members.par
        .flatMap(_.children)
        .distinct
        .seq
        .sortBy(_.score.total)
    )
  }
}
