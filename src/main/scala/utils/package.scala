package icfpc21.classified

import scala.util.Random

package object utils {

  implicit class RichIterable[T](val seq: Seq[T]) {
    def random: T = {
      seq(Random.nextInt(seq.size))
    }
  }
}
