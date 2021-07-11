package icfpc21.classified
package visualization

import icfpc21.classified.model._
import icfpc21.classified.optimizer.Scorer
import icfpc21.classified.solver.SolverListener

import java.awt.event._
import java.awt.image.BufferedImage
import java.awt.{FlowLayout, Point}
import java.util.concurrent.CopyOnWriteArrayList
import javax.swing.JFrame

case class Visualizer(val problem: Problem) extends SolverListener {
  private val images = new CopyOnWriteArrayList[Seq[BufferedImage]]()
  private val lock = new Object
  private var current = 0
  private var mousePos = new Point(1, 1)

  images.add(Renderer.render(problem.hole, Seq(Scorer.score(problem.figure, problem, true)), problem.bonuses, 0))
  private val plane: MyPlane = MyPlane(images.get(0), 1)
  var playing = true

  def show() = {
    val frame = new JFrame("Problem visualization")
    frame.setLayout(new FlowLayout())
    frame.add(plane)
    frame.pack()
    frame.setLocationRelativeTo(null)
    frame.addWindowListener(windowListener)
    frame.addKeyListener(keyListener)
    frame.addMouseMotionListener(mouseMotionListener)

    frame.setVisible(true)
    lock.synchronized {
      lock.wait()
    }
    frame.dispose()
  }

  override def candidates(scores: Seq[Scorer.Score], bonuses: Seq[BonusPoint], generation: Int): Unit = {
    images.add(Renderer.render(problem.hole, scores.reverse, bonuses, generation))

    if (playing) {
      while (images.size() > 20) {
        images.remove(0)
      }
      current = images.size() - 1
      plane.images = images.get(current)
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
        plane.images = images.get(current)
        playing = false
        plane.repaint()
      }
      if (e.getKeyCode == KeyEvent.VK_SPACE) {
        playing = true
        current = images.size() - 1
        plane.images = images.get(current)
        plane.repaint()
      }
      if (e.getKeyCode == KeyEvent.VK_UP) {
        plane.scale = plane.scale + 0.25
        plane.repaint()
      }
      if (e.getKeyCode == KeyEvent.VK_DOWN) {
        plane.scale = plane.scale - 0.25
        plane.repaint()
      }
    }

    override def keyReleased(e: KeyEvent): Unit = ()
  }

  private val mouseMotionListener = new MouseMotionListener {
    override def mouseDragged(mouseEvent: MouseEvent): Unit = {
      plane.xOffset += (mouseEvent.getX - mousePos.getX).toInt
      plane.yOffset += (mouseEvent.getY - mousePos.getY).toInt
      mousePos = mouseEvent.getPoint
      plane.repaint()
    }

    override def mouseMoved(mouseEvent: MouseEvent): Unit = {
      mousePos = mouseEvent.getPoint
    }
  }

}
