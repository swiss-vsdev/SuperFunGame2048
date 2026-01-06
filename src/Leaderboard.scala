import hevs.graphics.FunGraphics
import java.awt.{Color, Font}
import java.awt.event.{KeyEvent, KeyListener}

class Leaderboard (val display : FunGraphics){
  val kl: KeyListener = new KeyListener {
    override def keyTyped(e: KeyEvent): Unit = {

    }

    override def keyPressed(e: KeyEvent): Unit = {

    }

    override def keyReleased(e: KeyEvent): Unit = {
    }
  }

  def run() : Unit = {

    display.clear()
    display.drawString (100, 50, "Leaderboard", Font.MONOSPACED, Font.BOLD, 30, Color.orange)

  }

  def readfile() : Unit = {

  }

  def writefile(username : String, score : Int) : Unit = {

  }
}
