package icfpc21.classified
package utils

import scala.collection.mutable
import scala.util.Random

class DisjointSet() {
  private val map = mutable.HashMap.empty[Int, Int]

  def get(x: Int): Int =
    if (map.contains(x)) {
      val result = get(map(x))
      map.put(x, result)
      result
    } else {
      x
    }

  def join(x: Int, y: Int): Unit = {
    if (Random.nextBoolean()) {
      map.put(get(y), get(x))
    } else {
      map.put(get(x), get(y))
    }
  }

  def connectedRegions: Seq[Seq[Int]] =
    map.keys.groupBy(get).values.map(_.toVector).toVector

  def isSame(x: Int, y: Int): Boolean = get(x) == get(y)
}
