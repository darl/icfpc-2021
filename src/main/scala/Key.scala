package icfpc21.classified

import java.nio.file.{Files, Path}

object Key {
  val value = Files.readString(Path.of(".token"))
}
