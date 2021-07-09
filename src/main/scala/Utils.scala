package icfpc21.classified

object Utils {
  def squaredDistance(
      x1: Double,
      y1: Double,
      x2: Double,
      y2: Double
  ): Double = {
    (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)
  }

  def distance(x1: Double, y1: Double, x2: Double, y2: Double): Double = {
    math.sqrt(squaredDistance(x1, y1, x2, y2))
  }
}
