package icfpc21.classified
package model

case class Vector(x: Int, y: Int) {
  def +(other: Vector): Vector = Vector(x + other.x, y + other.y)
  def -(other: Vector): Vector = Vector(x - other.x, y - other.y)

  def *(v: Int): Vector = Vector(x * v, y * v)
//  def /(v: Double): Vector = Vector(x / v, y / v)

  def |*|(other: Vector): Double = x * other.x + y * other.y

  def product(other: Vector): Int = x * other.y - y * other.x

  def ! : Vector = this * -1

  def squaredLength: Int = x * x + y * y

  def length: Double = math.sqrt(squaredLength)

  def map(f: Int => Int): Vector = Vector(f(x), f(y))

//  def widthLength(v: Double): Vector = {
//    val len = length
//    Vector((x * v / len), (y * v / len))
//  }

  def normal(other: Vector): Vector = {
    if (other.x != 0) {
      val nx = other.x
      val ny = (-x * nx) / y
      Vector(nx, ny)
    } else {
      val ny = other.y
      val nx = (-y * ny) / x
      Vector(nx, ny)
    }
  }

  def scale(coef: Double): Vector = Vector(math.round(x * coef).toInt, math.round(y * coef).toInt)

  def distanceToLine(p1: Vector, p2: Vector): Double = {
    val px = p2.x - p1.x
    val py = p2.y - p1.y
    val tmp = px * px + py * py
    var u = ((x - p1.x) * px + (y - p1.y) * py).toDouble / tmp

    if (u > 1) {
      u = 1d
    } else if (u < 0) u = 0d
    val x0 = p1.x + u * px
    val y0 = p1.y + u * py

    val dx = x0 - x
    val dy = y0 - y
    Math.sqrt(dx * dx + dy * dy)
  }

  def rotateAround(center: Vector, angle: Double): Vector = {
    val aCos = math.cos(angle)
    val aSin = math.sin(angle)

    VectorD(
      aCos * (x - center.x) - aSin * (y - center.y) + center.x,
      aSin * (x - center.x) + aCos * (y - center.y) + center.y
    ).round
  }

  def mirror(a: Vector, b: Vector): Vector = {
    // https://stackoverflow.com/a/8954454
    val A = b.y - a.y
    val B = -(b.x - a.x)
    val C = -A * a.x - B * a.y

    val M = math.sqrt(A * A + B * B)
    val A1 = A / M
    val B1 = B / M
    val C1 = C / M

    val D = A1 * x + B1 * y + C1

    VectorD(
      x - 2 * A1 * D,
      y - 2 * B1 * D
    ).round
  }

//  def normalize: Vector = widthLength(1)

  def round: Vector = {
    Vector(
      x.round,
      y.round
    )
  }

  def angleTo(another: Vector): Double =
    math.acos((this |*| another) / this.length / another.length) / Math.PI * 180

  def isZero: Boolean = x == 0 && y == 0
  def nonZero: Boolean = !isZero

  def toDouble: VectorD = VectorD(x, y)
}

object Vector {
  val Zero: Vector = Vector(0, 0)
}
