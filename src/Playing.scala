import hevs.graphics.FunGraphics
import java.awt.event.{KeyEvent, KeyListener}

object Playing extends App {
  //Creates the FunGraphics Window
  val windowWidth: Int = 400
  val windowHeight: Int = 600
  val display: FunGraphics = new FunGraphics(windowWidth, windowHeight)

  //Initializing the var for the last typed direction and the waiting status for the player input
  var lastDirection: Int = -1
  var waitingInput : Boolean = true

  //Initializing KeyListener that changes the value of lastDirection
  val kl: KeyListener = new KeyListener {
    override def keyTyped(e: KeyEvent): Unit = {
    }

    override def keyPressed(e: KeyEvent): Unit = {
      e.getKeyChar match {
        case 'w' => lastDirection = 2
        case 'a' => lastDirection = 1
        case 's' => lastDirection = 0
        case 'd' => lastDirection = 3
        case _ => ()
      }
    }

    override def keyReleased(e: KeyEvent): Unit = {
    }
  }

  //Linking the KeyListener to the FunGraphics Window
  display.setKeyManager(kl)

  //Creating a new Board for the new game
  val game: Board = new Board()

  //Adding the first tile
  game.addNewTile()

  //Drawing Grid
  game.getGrid()

  //Game Loop
  while (game.isRunning) {
    if (lastDirection != -1) game.moveTiles(lastDirection)
    game.score()
    game.winner()
    game.addNewTile()
    game.looooooooooser()
    game.show()

    //Reset lastDirection value
    lastDirection = -1

    //Waiting for user input
    if(lastDirection == -1) {
    while (waitingInput){
      if(lastDirection != -1) waitingInput = false
      Thread.sleep(300)
    }
    waitingInput = true
    }
  }


}
