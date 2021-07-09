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
  }

  def renderProblem(problem: Problem): BufferedImage = {
    import problem._
    val image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB)
    val g = image.createGraphics()
    g.setFont(new Font("Monospaced", Font.PLAIN, 14))

    //Background
    g.setColor(Color.BLACK)
    g.fillRect(0, 0, size, size)

    //Hole
    g.setColor(Color.WHITE)
    val holePoints = hole.points.map(_.toScreen)
    g.fillPolygon(
      holePoints.map(_.x).toArray,
      holePoints.map(_.y).toArray,
      holePoints.size
    )

    //Figure
    g.setColor(Color.RED)
    figure.edges.foreach { edge =>
      val a = figure.vertices(edge.aIndex).toScreen
      val b = figure.vertices(edge.bIndex).toScreen
      g.drawLine(a.x, a.y, b.x, b.y)
    }

    g.dispose()
    image
  }

}
