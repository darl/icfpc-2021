package icfpc21.classified

import scala.util.Random

package object utils {

  implicit class RichIterable[T](val seq: Seq[T]) {
    def random: T = {
      seq(Random.nextInt(seq.size))
    }

    def randomN(count: Int): Seq[T] = {
      if (count >= seq.size) seq
      else {
        val result = scala.collection.mutable.HashSet[Int]()
        while (result.size < count) {
          result.add(Random.nextInt(seq.size))
        }
        seq.zipWithIndex.filter { case (_, i) => result.contains(i) }.map(_._1)
      }
    }
  }
  implicit class RichDouble(val v: Double) {
    def randomSign: Double =
      v * (if (Random.nextBoolean()) 1d else -1d)
  }
}
