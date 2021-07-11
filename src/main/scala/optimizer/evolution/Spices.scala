package icfpc21.classified
package optimizer.evolution

import scala.collection.parallel.CollectionConverters._
import utils._

case class Spices(members: Seq[Agent]) {
  lazy val bestOne: Agent = members.maxBy(_.score.total)

  def cross(other: Spices) = {
    Spices(
      members.map(_.cross(other.members.random)) ++ members
    )
  }

  def mutate: Spices = {
    Spices(
      members = members
        .flatMap(_.children)
    )
  }
}
