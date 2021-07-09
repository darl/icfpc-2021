package icfpc21.classified
package visualization

import model._

import java.awt.BorderLayout

case class Visualizer(problem: Problem) {
  def show() = {
    import javax.swing.JFrame
    val frame = new JFrame("Problem visualization")
    frame.setLayout(new BorderLayout())
    val plane = MyPlane(, 1)
  }
}
