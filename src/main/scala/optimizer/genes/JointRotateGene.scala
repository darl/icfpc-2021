package icfpc21.classified
package optimizer.genes

import model._
import optimizer.evolution.{Gene, GeneGenerator}

import scala.util.Random

case class JointRotateGene(centerIndex: Int, vertices: Seq[Int], angle: Double) extends Gene {
  override def modify(figure: Figure): Figure =
    figure.updateVertexes(vertices, (_, v) => v.rotateAround(figure.vertices(centerIndex), angle))

  override def mutate: Gene = {
    val mutation = Random.nextDouble() * 2 - 1
    JointRotateGene(centerIndex, vertices, angle + mutation)
  }
}

object JointRotateGene extends GeneGenerator {
  override def forProblem(problem: Problem): Seq[Gene] = {
    import problem._
    figure.edges.analysis.joints.flatMap { joint =>
      joint.subgroups.map { group =>
        val angle = Random.nextDouble() * 2 * math.Pi
        JointRotateGene(joint.index, group, angle)
      }
    }
  }
}
