import hevs.graphics.FunGraphics
import java.awt.event.{KeyEvent, KeyListener}

object Start extends App {
  val display = new FunGraphics(400, 600, "Super Fun 2048")
  val mainMenu = new MainMenu(display)
  val kl: KeyListener = new KeyListener {
    override def keyTyped(e: KeyEvent): Unit = {
      newIn = true
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
          isOn = false
          restart = true
          looser = false
        }
        case _ => ()
      }
    }

    override def keyReleased(e: KeyEvent): Unit = {
    }
  }
  var lastDirection = -1
  var select = false
  var newIn = false
  var isOn: Boolean = false
  var menuLoc: Int = 1
  var menuOpen = true
  var waitingInput: Boolean = true
  var looser: Boolean = false
  var restart: Boolean = false

  display.setKeyManager(kl)
  start()

  while (true) {
    if (restart) start()
  }

  def start(): Unit = {
    newIn = false
    isOn = true
    mainMenu.run()
  }
}
