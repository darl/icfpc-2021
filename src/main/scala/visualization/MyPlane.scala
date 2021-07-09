package icfpc21.classified
package visualization

import java.awt._
import java.awt.image.BufferedImage
import javax.swing.JPanel

case class MyPlane(var image: BufferedImage, var scale: Int = 4)
    extends JPanel {

  override def getPreferredSize: Dimension = {
    new Dimension(image.getWidth * scale, image.getHeight * scale)
  }

  override def paintComponent(g: Graphics): Unit = {
    super.paintComponent(g)
    g.drawImage(
      image,
      0,
      0,
      image.getWidth * scale,
      image.getHeight() * scale,
      null
    )
  }
}
