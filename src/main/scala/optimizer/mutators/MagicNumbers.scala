package icfpc21.classified
package optimizer.mutators

import icfpc21.classified.utils._

import scala.util.Random

object MagicNumbers {
  val magicAngles = Seq(
    math.Pi / 2, //90°
    math.Pi / 3, //60°
    math.Pi / 6, //30°
    math.Pi, //180°
    0d
  )

  def randomAngle: Double = {
    var angle = magicAngles.random
    if (angle == 0d) angle = Random.nextDouble() * 2 * math.Pi
    angle = angle.randomSign
    angle
  }
}
