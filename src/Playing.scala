import Start.lastDirection
import hevs.graphics.FunGraphics

import java.awt.event.{KeyEvent, KeyListener}

class Playing(val display : FunGraphics){
  //Creates the FunGraphics Window
  //val windowWidth: Int = 400
  //val windowHeight: Int = 600
  //val display: FunGraphics = new FunGraphics(windowWidth, windowHeight)

  //Initializing the var for the waiting status for the player input
  var waitingInput : Boolean = true



  def run() : Unit = {
    display.clear()


    //Creating a new Board for the new game
    val game: Board = new Board(display)

    game.askUsername()

    //Adding the first tile
    game.addNewTile()

    //Drawing Grid
    game.getGrid()
    game.show()

    var allowedDirections = game.canMove()

    //Game Loop
    while (game.isRunning) {

      //Tester les 4 directions et bloquer les non authorisées

      println("directions :" + allowedDirections.mkString(" / "))

      if (lastDirection >= 0 && lastDirection <= 4) {
        if (allowedDirections(lastDirection) == true) {
          if (lastDirection != -1) game.moveTiles(lastDirection)
          //Si aucune direction possible : looser et plus d'actions possibles

          //& if(moveTiles vers direction est authorisé)
          game.score()
          game.winner()
          game.addNewTile()
          game.show()
        }
        allowedDirections = game.canMove()
        if (allowedDirections.sameElements(Array(false, false, false, false)) == true) {
          game.looooooooooser()
        }
      }

      //Reset lastDirection value
      lastDirection = -1

      //Waiting for user input
      if (lastDirection == -1) {
        while (waitingInput) {
          if (lastDirection != -1) waitingInput = false
          Thread.sleep(3)
        }
        waitingInput = true
      }

    }

  }
}
