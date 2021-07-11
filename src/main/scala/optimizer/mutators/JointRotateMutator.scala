package icfpc21.classified
package optimizer.mutators

import icfpc21.classified.model.{Figure, Problem}
import icfpc21.classified.optimizer.Mutator
import icfpc21.classified.utils._

object JointRotateMutator extends Mutator {

  override def mutate(figure: Figure, problem: Problem, speed: Double): Figure = {
    if (figure.edges.analysis.joints.isEmpty) return figure

    val angle = MagicNumbers.randomAngle

    val joint = figure.edges.analysis.joints.random
    val center = figure.vertices(joint.index)

    val randomSegment = joint.subgroups.random

    figure.updateVertexes(randomSegment, (_, vector) => vector.rotateAround(center, angle))
  }
}
