package icfpc21.classified
package visualization

import model._

import java.awt.image.BufferedImage
import java.awt.{BasicStroke, Color, Font}

object Renderer {
  val size = 500
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
        x = vector.x * scale,
        y = vector.y * scale
      )

    }
  }

  def render(hole: Hole, figures: Seq[Figure], generation: Int): Seq[BufferedImage] =
    figures.zipWithIndex.map {
      case (figure, index) =>
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
        g.setFont(new Font("Monospaced", Font.PLAIN, 8))
        val points = scala.collection.mutable.HashSet[Int]()
        g.setStroke(new BasicStroke(2))
        g.setColor(colors(index % colors.size))
        figure.edges.values.foreach { edge =>
          val a = figure.vertices(edge.aIndex).toScreen
          val b = figure.vertices(edge.bIndex).toScreen
          if (!points.contains(edge.aIndex)) {
            g.drawString(s"${edge.aIndex}", a.x, a.y)
            points.add(edge.aIndex)
          }
          if (!points.contains(edge.bIndex)) {
            g.drawString(s"${edge.bIndex}", b.x, b.y)
            points.add(edge.bIndex)
          }
          g.drawLine(a.x, a.y, b.x, b.y)
        }

        //Text
        g.setColor(Color.RED)
        g.setStroke(new BasicStroke(3))
        g.setFont(new Font("Monospaced", Font.PLAIN, 16))
        if (index == 0) {
          g.drawString(s"Generation: $generation", 30, 30)
        }

        g.dispose()
        image
    }

}
