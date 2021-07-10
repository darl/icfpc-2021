package icfpc21.classified
package visualization

import model._

import java.awt.image.BufferedImage
import java.awt.{BasicStroke, Color, Font}

object Renderer {
  val size = 1200
  val scale = 4

  private val colors = Seq(
    Color.RED,
    Color.YELLOW,
    Color.GREEN,
    Color.BLUE,
    Color.MAGENTA,
    Color.CYAN,
    Color.PINK
  )

  implicit class RichVector(val vector: Vector) {

    def toScreen: Vector = {
      Vector(
        x = vector.x * scale + (size / 2),
        y = vector.y * scale + (size / 2)
      )

    }
  }

  def render(hole: Hole, figures: Seq[Figure], generation: Int): BufferedImage = {
    val image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB)
    val g = image.createGraphics()
    g.setFont(new Font("Monospaced", Font.PLAIN, 14))

    //Background
    g.setColor(Color.LIGHT_GRAY)
    g.fillRect(0, 0, size, size)

    //Hole
    g.setColor(Color.WHITE)
    val holePoints = hole.points.map(_.toScreen)
    g.fillPolygon(
      holePoints.map(_.x).toArray,
      holePoints.map(_.y).toArray,
      holePoints.size
    )

    //Figures
    g.setStroke(new BasicStroke(2))
    figures.zipWithIndex.foreach {
      case (figure, index) =>
        g.setColor(colors(index))
        figure.edges.foreach { edge =>
          val a = figure.vertices(edge.aIndex).toScreen
          val b = figure.vertices(edge.bIndex).toScreen
          g.drawLine(a.x, a.y, b.x, b.y)
        }
    }

    //Text
    g.setColor(Color.RED)
    g.setStroke(new BasicStroke(3))
    g.setFont(new Font("Monospaced", Font.PLAIN, 16))
    g.drawString(s"Generation: $generation", 100, 300)

    g.dispose()
    image
  }

}
