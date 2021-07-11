package icfpc21.classified

import java.nio.file.{Files, Path}

object Tokens {
  val value = Files.readString(Path.of(".token"))
  val session = Files.readString(Path.of(".session"))
}
