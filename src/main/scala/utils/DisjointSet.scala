package icfpc21.classified
package utils

import scala.collection.mutable
import scala.util.Random

class DisjointSet() {
  private val map = mutable.HashMap.empty[Int, Int]

  def get(x: Int): Int =
    if (map.contains(x)) {
      val g = map(x)
      val result = if (g == x) g else get(g)
      map.put(x, result)
      result
    } else {
      x
    }

  def join(x: Int, y: Int): Unit = {
    val gX = get(x)
    val gY = get(y)
    if (Random.nextBoolean()) {
      map.put(gY, gX)
    } else {
      map.put(gX, gY)
    }
  }

  def connectedRegions: Seq[Seq[Int]] =
    (map.keys ++ map.values).groupBy(get).values.map(_.toVector).toVector

  def isSame(x: Int, y: Int): Boolean = get(x) == get(y)
}
