package icfpc21.classified

import java.io.File
import java.nio.file.{Files, Path}

object Key {
  val value = Files.readString(Path.of(".token"))
}
