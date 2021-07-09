package icfpc21.classified
package optimizer

import icfpc21.classified.optimizer.domain.FigureUpdate

trait Mutator {
  // speed нужен чтобы замедлить скорость мутаций в конце
  def mutate(figure: FigureUpdate, speed: Double): Unit
}
