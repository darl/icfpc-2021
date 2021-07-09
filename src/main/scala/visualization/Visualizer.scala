package icfpc21.classified
package visualization

import model._

import java.awt.BorderLayout
import java.awt.event._
import javax.swing.JFrame

case class Visualizer(problem: Problem) {
  def show() = {
    val frame = new JFrame("Problem visualization")
    frame.setLayout(new BorderLayout())
    val plane = MyPlane(Renderer.renderProblem(problem), 1)
    frame.add(plane)
    frame.pack()
    frame.setLocationRelativeTo(null)
    frame.setVisible(true)

    val lock = new Object
    frame.addWindowListener(new WindowAdapter {
      override def windowClosing(e: WindowEvent): Unit = {
        lock.synchronized {
          lock.notify()
        }
      }
    })
    lock.synchronized {
      lock.wait()
    }
    frame.dispose()
  }
}
