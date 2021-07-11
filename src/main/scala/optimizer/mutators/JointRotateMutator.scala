package icfpc21.classified
package optimizer.mutators

import icfpc21.classified.model.{Figure, Problem}
import icfpc21.classified.optimizer.Mutator
import icfpc21.classified.utils._

import scala.util.Random

object JointRotateMutator extends Mutator {
  val magicNumbers = Seq(
    math.Pi / 2, //90°
    math.Pi / 3, //60°
    math.Pi / 6, //30°
    math.Pi, //180°
    0d
  )

  override def mutate(figure: Figure, problem: Problem, speed: Double): Figure = {
    if (figure.edges.analysis.joints.isEmpty) return figure

    var angle = magicNumbers.random
    if (angle == 0d) angle = Random.nextDouble() * 2 * math.Pi
    angle = angle.randomSign

    val joint = figure.edges.analysis.joints.random
    val center = figure.vertices(joint.index)

    val randomSegment = joint.subgroups.random

    figure.updateVertexes(randomSegment, (_, vector) => vector.rotateAround(center, angle))
  }
}
