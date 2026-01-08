import hevs.graphics.FunGraphics
import hevs.graphics.utils.GraphicsBitmap
import java.awt.{Color, Font}
import java.io.File
import javax.sound.sampled.AudioSystem
import Start.{lastDirection, newIn, select}

class MainMenu(val display: FunGraphics) {
  val lb: Leaderboard = new Leaderboard(display)
  var menuLoc: Int = 1

  def run(): Unit = {
    var menuOpen = true
    lastDirection = -1

    display.clear()
    menuLoc = 1

    background()
    while (menuOpen) {
      menuLocWrite()
      highlightMenu()
      if (select) {
        menuLoc match {
          case 1 => {
            println("Starting Game...")
            val game: Playing = new Playing(display)
            select = false
            menuOpen = false
            game.run
          }
          case 2 => {
            println("Opening Leaderboard...")
            select = false
            menuOpen = false
            lb.run
          }
          case 3 => {
            println("Exiting game...")

            val musicfile = new File("./src/bye.wav")
            val clip = AudioSystem.getClip()
            val audio = AudioSystem.getAudioInputStream(musicfile)
            clip.open(audio)
            clip.start()

            val time : Long = System.currentTimeMillis() + 7500

            while(System.currentTimeMillis() < time){
              for(i <- 0 to 9){
                val gb = new GraphicsBitmap(s"/byeGif/frame_0$i.jpg")
                display.drawPicture(200, 303, gb)
                Thread.sleep(15)
              }
            }

            clip.stop()
            display.clear()
            val gb = new GraphicsBitmap("/bonus2.jpg")
            display.drawPicture(200, 300, gb)
            Thread.sleep(1000)
            System.exit(0)
          }
        }
      }
    }
  }

  def menuLocWrite(): Unit = {
    if (newIn) {
      lastDirection match {
        case 2 => {
          if (menuLoc == 1) menuLoc = 3 else menuLoc += -1
        }
        case 0 => {
          if (menuLoc == 3) menuLoc = 1 else menuLoc += +1
        }
        case _ => ()
      }
      newIn = false
    }
  }

  def highlightMenu(): Unit = {
    menuLoc match {
      case 1 => {
        playSelected()
      }
      case 2 => {
        leaderBoardSelected()
      }
      case 3 => {
        quitSelected()
      }
      case _ => {
      }
    }
  }

  def playSelected(): Unit = {
    display.clear()
    background()

    while (!newIn) {
      for (i <- 0 to 221 by 5) {
        if (!newIn) {
          val textColor = new Color(i, i, i)
          display.setColor(Color.white)
          display.drawFillRect(140, 275, 110, 35)
          display.drawString(140, 300, "Play !", Font.MONOSPACED, Font.BOLD, 30, textColor)
          Thread.sleep(10)
        }
      }
    }
  }

  def background(): Unit = {
    display.setColor(Color.BLACK)
    display.drawString(70, 50, "Super Fun 2048", Font.MONOSPACED, Font.BOLD, 30, Color.orange)
    display.drawString(140, 300, "Play !", Font.MONOSPACED, Font.PLAIN, 30, Color.black)
    display.drawString(100, 400, "Leaderboard", Font.MONOSPACED, Font.PLAIN, 30, Color.black)
    display.drawString(160, 500, "Quit", Font.MONOSPACED, Font.PLAIN, 30, Color.black)
    //Reset lastDirection value
    lastDirection = -1
  }

  def leaderBoardSelected(): Unit = {
    display.clear()
    background()

    while (!newIn) {
      for (i <- 0 to 221 by 5) {
        if (!newIn) {
          val textColor = new Color(i, i, i)
          display.setColor(Color.white)
          display.drawFillRect(100, 375, 203, 35)
          display.drawString(100, 400, "Leaderboard", Font.MONOSPACED, Font.BOLD, 30, textColor)
          Thread.sleep(10)
        }
      }
    }
  }

  def quitSelected(): Unit = {
    display.clear()
    background()

    while (!newIn) {
      for (i <- 0 to 221 by 5) {
        if (!newIn) {
          val textColor = new Color(i, i, i)
          display.setColor(Color.white)
          display.drawFillRect(160, 475, 75, 35)
          display.drawString(160, 500, "Quit", Font.MONOSPACED, Font.BOLD, 30, textColor)
          Thread.sleep(10)
        }
      }
    }
  }
}
