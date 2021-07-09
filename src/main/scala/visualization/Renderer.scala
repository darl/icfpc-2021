package icfpc21.classified
package visualization

import model._

import java.awt.{Color, Font}
import java.awt.image.BufferedImage

object Renderer {
  val size = 1200
  val scale = 4

  implicit class RichVector(val vector: Vector) {

    def toScreen: Vector = {
      Vector(
        x = vector.x * scale + (size / 2),
        y = vector.y * scale + (size / 2)
      )

    }

    def renderProblem(problem: Problem) = {
      val image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB)
      val g = image.createGraphics()
      g.setFont(new Font("Monospaced", Font.PLAIN, 14))

      //Background
      g.setColor(Color.BLACK)
      g.fillRect(0, 0, size, size)

      //Hole
      g.setColor(Color.WHITE)
      g.fillPolygon(problem.hole.points.map(_.x),)
      val topLeft = Vector(-14, -14).toScreen
      g.drawRect(topLeft.x.toInt, topLeft.y.toInt, 28 * scale, 28 * scale)

    }
  }

}
