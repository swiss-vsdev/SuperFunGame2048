import hevs.graphics.FunGraphics
import java.awt.{Color, Font}
import java.awt.event.{KeyEvent, KeyListener}


object MainMenu extends App {
  val windowWidth: Int = 400
  val windowHeight: Int = 600
  val display: FunGraphics = new FunGraphics(windowWidth, windowHeight)
  var lastDirection : Int = -1
  var newIn : Boolean = false
  var menuLoc : Int = 1
  var select : Boolean = false

  //Initializing KeyListener that changes the value of lastDirection
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
    }

    override def keyReleased(e: KeyEvent): Unit = {
    }
  }

  def playSelected() : Unit = {
    display.clear()
    background()
    /*
    display.setColor(Color.white)
    display.drawFillRect(135,270,115,40)
    display.setColor(Color.BLACK)
    display.drawRect (135,270,115,40)
    */


    while(!newIn) {
      for (i <- 0 to 221 by 5) {
        if(!newIn) {
          val textColor = new Color(i, i, i)
          display.setColor(Color.white)
          display.drawFillRect(140, 275, 110, 35)
          display.drawString(140, 300, "Play !", Font.MONOSPACED, Font.BOLD, 30, textColor)
          Thread.sleep(10)
        }
      }
    }
  }

  def leaderBoardSelected() : Unit = {
    display.clear()
    background()
    /*
    display.setColor(Color.white)
    display.drawFillRect(95,370,208,40)
    display.setColor(Color.BLACK)
    display.drawRect (95,370,208,40)
    */


    while(!newIn) {
      for (i <- 0 to 221 by 5) {
        if(!newIn) {
          val textColor = new Color(i, i, i)
          display.setColor(Color.white)
          display.drawFillRect(100, 375, 203, 35)
          display.drawString(100, 400, "Leaderboard", Font.MONOSPACED, Font.BOLD, 30, textColor)
          Thread.sleep(10)
        }
      }
    }
  }

  def quitSelected() : Unit = {
    display.clear()
    background()
   /*
    display.setColor(Color.white)
    display.drawFillRect(155,470,80,40)
    display.setColor(Color.BLACK)
    display.drawRect (155,470,80,40)
    */


    while(!newIn) {
      for (i <- 0 to 221 by 5) {
        if(!newIn){
          val textColor = new Color(i, i, i)
          display.setColor(Color.white)
          display.drawFillRect(160,475,75,35)
          display.drawString(160, 500, "Quit", Font.MONOSPACED, Font.BOLD, 30, textColor)
          Thread.sleep(10)
        }
      }
    }
  }

  def background() : Unit = {
    display.setColor (Color.BLACK)
    display.drawString (70, 50, "Super Fun 2048", Font.MONOSPACED, Font.BOLD, 30, Color.orange)
    display.drawString (140, 300, "Play !", Font.MONOSPACED, Font.PLAIN, 30, Color.black)
    display.drawString (100, 400, "Leaderboard", Font.MONOSPACED, Font.PLAIN, 30, Color.black)
    display.drawString (160, 500, "Quit", Font.MONOSPACED, Font.PLAIN, 30, Color.black)
    //Reset lastDirection value
    lastDirection = -1
  }

  def menuLocWrite() : Unit = {
    if(newIn){
      lastDirection match {
        case 2 => {
          if(menuLoc ==1) menuLoc = 3 else menuLoc += -1
        }
        case 0 => {
          if(menuLoc ==3) menuLoc = 1 else menuLoc += +1
        }
        case _ => ()
      }
      newIn = false
    }
  }

  def highlightMenu() : Unit = {
    menuLoc match {
      case 1 => playSelected()
      case 2 => leaderBoardSelected()
      case 3 => quitSelected()
    }
  }


  //Linking the KeyListener to the FunGraphics Window
  display.setKeyManager(kl)



  background()
  while(true){
    menuLocWrite()
    highlightMenu()
    if(select){
      menuLoc match {
        case 1 => {
          println("Starting Game...")
        }
        case 2 => println("Opening Leaderboard...")
        case 3 => {
          println("Exiting game...")
          System.exit(0)
        }
      }
    }
  }



}
