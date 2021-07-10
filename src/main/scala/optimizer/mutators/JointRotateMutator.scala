package icfpc21.classified
package optimizer.mutators

import optimizer.Mutator
import model.{Figure, Hole, Vector}
import utils._
import scala.util.Random

object JointRotateMutator extends Mutator {
  override def mutate(figure: Figure, hole: Hole, speed: Double): Figure = {
    val joints = figure.edges.analysis.joints.filter(_ => Random.nextBoolean())
    if (joints.isEmpty) {
      return figure
    }

    joints.foldLeft(figure) {
      case (figure, joint) =>
        val rotationCenter = figure.vertices(joint.index)

        val rotateLeft = {
          val angle = Random.nextDouble() * 2 * math.Pi
          figure.copy(vertices =
            figure.vertices.zipWithIndex
              .map {
                case (v, i) =>
                  if (joint.subgroups.head.contains(i)) {
                    v.rotateAround(rotationCenter, angle)
                  } else {
                    v
                  }
              }
          )
        }

        val rotateRight = if (Random.nextBoolean()) {
          val angle = Random.nextDouble() * 2 * math.Pi
          rotateLeft.copy(vertices =
            rotateLeft.vertices.zipWithIndex
              .map {
                case (v, i) =>
                  if (joint.subgroups.last.contains(i)) {
                    v.rotateAround(rotationCenter, angle)
                  } else {
                    v
                  }
              }
          )
        } else rotateLeft
        rotateRight
    }
  }
}
