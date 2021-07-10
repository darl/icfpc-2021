package icfpc21.classified
package visualization

import java.awt.{image, _}
import java.awt.image.BufferedImage
import javax.swing.JPanel

case class MyPlane(var images: Seq[BufferedImage], var scale: Double = 4) extends JPanel {

  var xOffset = 0;
  var yOffset = 0;

  override def getPreferredSize: Dimension = {
    new Dimension((images.map(_.getWidth).sum * scale * 5).toInt, (images.map(_.getHeight).sum * scale * 5).toInt)
  }

  override def paintComponent(g: Graphics): Unit = {
    super.paintComponent(g)
    val rows = Math.ceil(Math.sqrt(images.size)).toInt
    val colums = Math.floor(Math.sqrt(images.size)).toInt
    val width = (images.head.getWidth * scale).toInt
    val height = (images.head.getHeight * scale).toInt
    images.zipWithIndex.foreach {
      case (image, index) =>
        val offsetX = Math.floor(index.toDouble / colums).toInt * width + xOffset
        val offsetY = (index % rows) * height + yOffset
        g.drawImage(
          image,
          offsetX,
          offsetY,
          width,
          height,
          null
        )
    }
  }
}
