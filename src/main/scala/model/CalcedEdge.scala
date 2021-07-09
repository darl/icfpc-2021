package icfpc21.classified
package model

case class CalcedEdge(a: Vector, b: Vector, originSquaredLength: Double) {
  def squaredLength: Int = (a - b).squaredLength

  def tension(implicit eps: Int): Double = {
    val diff = (squaredLength / originSquaredLength) - 1
    diff / (eps.toDouble / 1000000)
  }
}
