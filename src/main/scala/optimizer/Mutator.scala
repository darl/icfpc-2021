package icfpc21.classified
package optimizer

import icfpc21.classified.model.{Figure, Problem}

trait Mutator {
  // speed нужен чтобы замедлить скорость мутаций в конце
  def mutate(figure: Figure, problem: Problem, speed: Double): Figure
}
