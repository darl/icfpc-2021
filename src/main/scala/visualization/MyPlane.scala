package icfpc21.classified
package visualization

import java.awt._
import java.awt.image.BufferedImage
import javax.swing.JPanel

case class MyPlane(var image: BufferedImage, var scale: Double = 4)
    extends JPanel {

  var xOffset = 0;
  var yOffset = 0;

  override def getPreferredSize: Dimension = {
    new Dimension((image.getWidth * scale).toInt, (image.getHeight * scale).toInt)
  }

  override def paintComponent(g: Graphics): Unit = {
    super.paintComponent(g)
    g.drawImage(
      image,
      xOffset,
      yOffset,
      (image.getWidth * scale).toInt,
      (image.getHeight * scale).toInt,
      null
    )
  }
}
