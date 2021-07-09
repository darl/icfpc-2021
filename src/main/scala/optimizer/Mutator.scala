package icfpc21.classified
package optimizer

import icfpc21.classified.model.{Figure, Hole}

trait Mutator {
  // speed нужен чтобы замедлить скорость мутаций в конце
  def mutate(figure: Figure, hole: Hole, speed: Double): Figure
}
