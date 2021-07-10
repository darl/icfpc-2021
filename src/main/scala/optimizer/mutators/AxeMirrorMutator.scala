package icfpc21.classified
package optimizer.mutators

import icfpc21.classified.model.{Figure, Hole, Problem}
import icfpc21.classified.optimizer.Mutator
import icfpc21.classified.utils.RichIterable

object AxeMirrorMutator extends Mutator {
  override def mutate(figure: Figure, problem: Problem, speed: Double): Figure = {
    if (figure.edges.analysis.axes.isEmpty) {
      return figure
    }
    val axe = figure.edges.analysis.axes.random
    val aPoint = figure.vertices(axe.aIndex)
    val bPoint = figure.vertices(axe.bIndex)
    if (aPoint == bPoint) {
      return figure
    }
    val randomSegment = axe.subgroups.random

    figure.updateVertexes(randomSegment, (_, vector) => vector.mirror(aPoint, bPoint))
  }
}
