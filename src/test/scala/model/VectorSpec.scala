package icfpc21.classified
package model

import org.scalatest.wordspec.AnyWordSpec

class VectorSpec extends AnyWordSpec {
  "Vector" should {
    "mirror across line" in {
      val a = Vector(1, 1)
      val b = Vector(0, 0)
      val c = Vector(0, 1)
      assert(a.mirror(b, c) === Vector(-1, 1))
    }
    "mirror across line #2" in {
      val a = Vector(1, 1)
      val b = Vector(1, 0)
      val c = Vector(0, 1)
      assert(a.mirror(b, c) === Vector(0, 0))
    }
    "mirror across line #3" in {
      val a = Vector(3, 2)
      val b = Vector(2, 4)
      val c = Vector(4, 5)
      assert(a.mirror(b, c) === Vector(1, 6))
    }
  }
}
