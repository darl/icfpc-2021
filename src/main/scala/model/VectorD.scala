package icfpc21.classified
package model

case class VectorD(x: Double, y: Double) {
  def +(other: VectorD): VectorD = VectorD(x + other.x, y + other.y)
  def -(other: VectorD): VectorD = VectorD(x - other.x, y - other.y)

  def *(v: Double): VectorD = VectorD(x * v, y * v)
  def /(v: Double): VectorD = VectorD(x / v, y / v)

  def |*|(other: VectorD): Double = x * other.x + y * other.y

  def product(other: VectorD): Double = x * other.y - y * other.x

  def ! : VectorD = this * -1

  def squaredLength: Double = x * x + y * y

  def length: Double = math.sqrt(squaredLength)

  def map(f: Double => Double): VectorD = VectorD(f(x), f(y))

  def widthLength(v: Double): VectorD = {
    val len = length
    VectorD((x * v / len), (y * v / len))
  }

  def normal(other: VectorD): VectorD = {
    if (other.x != 0) {
      val nx = other.x
      val ny = (-x * nx) / y
      VectorD(nx, ny)
    } else {
      val ny = other.y
      val nx = (-y * ny) / x
      VectorD(nx, ny)
    }
  }

  def normalize: VectorD = widthLength(1)

  def round: Vector = {
    Vector(
      x.round.toInt,
      y.round.toInt
    )
  }

  def angleTo(another: VectorD): Double =
    math.acos((this |*| another) / this.length / another.length) / Math.PI * 180

  def isZero: Boolean = x == 0 && y == 0
  def nonZero: Boolean = !isZero
}

object VectorD {
  val Zero: VectorD = VectorD(0, 0)
}
