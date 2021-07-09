package icfpc21.classified
package optimizer.domain

class FigureUpdate(eps: Int) {
  private val eps0 = eps.toDouble / 1000000

  class PointUpdate(var x: Double, var y: Double) {
    def moveTo(newX: Double, newY: Double): Unit = {
      x = newX
      y = newY
    }
  }
  class EdgeUpdate(aIndex: Int, bIndex: Int, originSquaredLength: Double) {
    def squaredLength: Double = {
      val a = _points(aIndex)
      val b = _points(bIndex)
      Utils.squaredDistance(
        a.x,
        a.y,
        b.x,
        b.y
      )
    }

    def tension: Double = {
      val diff = (squaredLength / originSquaredLength) - 1
      diff / eps0
    }

    def isValid: Boolean = tension.abs <= 1.0d
  }

  private val _points: Array[PointUpdate] = Array.empty
  private val _edged: Array[EdgeUpdate] = Array.empty

  def points: Seq[PointUpdate] = _points
  def edges: Seq[EdgeUpdate] = _edged

  def isValid: Boolean = edges.forall(_.isValid)

  def copy: FigureUpdate = ???
}
