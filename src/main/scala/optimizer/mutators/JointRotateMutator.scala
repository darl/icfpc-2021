package icfpc21.classified
package optimizer.mutators

import optimizer.Mutator
import model.{Figure, Hole, Vector}
import utils._
import scala.util.Random

object JointRotateMutator extends Mutator {
  override def mutate(figure: Figure, hole: Hole, speed: Double): Figure = {
    if (figure.edges.analysis.joints.isEmpty) return figure

    val angle = Random.nextDouble() * 2 * math.Pi
    val joint = figure.edges.analysis.joints.random
    val center = figure.vertices(joint.index)

    val randomSegment = joint.subgroups.random

    figure.updateVertexes(randomSegment, (_, vector) => vector.rotateAround(center, angle))
  }
}
