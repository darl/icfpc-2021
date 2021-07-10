package icfpc21.classified
package visualization

import model._
import solver.SolverListener

import java.awt.BorderLayout
import java.awt.event._
import java.awt.image.BufferedImage
import java.util.concurrent.CopyOnWriteArrayList
import javax.swing.JFrame

case class Visualizer(val problem: Problem) extends SolverListener {
  private val images = new CopyOnWriteArrayList[BufferedImage]()
  private val lock = new Object
  private var current = 0
  private var plane: MyPlane = null
  var playing = true

  def show() = {
    val frame = new JFrame("Problem visualization")
    frame.setLayout(new BorderLayout())
    images.add(Renderer.render(problem.hole, Seq(problem.figure), 0))
    plane = MyPlane(images.get(0), 1)
    frame.add(plane)
    frame.pack()
    frame.setLocationRelativeTo(null)
    frame.setVisible(true)

    frame.addWindowListener(windowListener)
    frame.addKeyListener(keyListener)
    lock.synchronized {
      lock.wait()
    }
    frame.dispose()
  }

  override def candidates(figures: Seq[Figure], generation: Int): Unit = {
    images.add(Renderer.render(problem.hole, figures, generation))
    if (playing) {
      current = images.size() - 1
      plane.image = images.get(current)
      plane.repaint()
    }
  }

  override def solution(solution: Solution): Unit = ()

  private val windowListener = new WindowAdapter {
    override def windowClosing(e: WindowEvent): Unit = {
      lock.synchronized {
        lock.notify()
      }
    }
  }

  private val keyListener: KeyListener = new KeyListener {
    override def keyTyped(e: KeyEvent): Unit = ()

    override def keyPressed(e: KeyEvent): Unit = {
      val left = e.getKeyCode == KeyEvent.VK_LEFT && current > 0
      val right = e.getKeyCode == KeyEvent.VK_RIGHT && current < images.size - 1
      if (left) {
        current = current - 1
        playing = false
      }
      if (right) {
        current = current + 1
      }
      if (left || right) {
        plane.image = images.get(current)
        playing = false
        plane.repaint()
      }
      if (e.getKeyCode == KeyEvent.VK_SPACE) {
        playing = true
        current = images.size() - 1
        plane.image = images.get(current)
        plane.repaint()
      }
    }

    override def keyReleased(e: KeyEvent): Unit = ()
  }
}
