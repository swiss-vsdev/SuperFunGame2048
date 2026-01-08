import hevs.graphics.FunGraphics
import java.awt.event.{KeyEvent, KeyListener}

object Start extends App {

  val display = new FunGraphics(400, 600, "Super Fun 2048")
  val mainMenu = new MainMenu(display)
  var lastDirection = -1
  var select = false
  var newIn = false

  val kl: KeyListener = new KeyListener {
    override def keyTyped(e: KeyEvent): Unit = {
      newIn = true
      println("hello")
    }

    override def keyPressed(e: KeyEvent): Unit = {
      e.getKeyChar match {
        case 'w' => lastDirection = 2
        case 'a' => lastDirection = 1
        case 's' => lastDirection = 0
        case 'd' => lastDirection = 3
        case ' ' => select = true
        case _ => ()
      }
      e.getKeyCode match {
        case 27 => {
          System.exit(0)
        }
        case _ => ()
      }
    }

    override def keyReleased(e: KeyEvent): Unit = {
    }
  }

  display.setKeyManager(kl)
  newIn = false

  mainMenu.run()
}
