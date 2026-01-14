import Start.{isOn, lastDirection, waitingInput, looser}
import hevs.graphics.FunGraphics

import java.io.File
import javax.sound.sampled.AudioSystem

class Playing(val display: FunGraphics) {


  def run(): Unit = {
    display.clear()

    //Creating a new Board for the new game
    val game: Board = new Board(display)

    game.askUsername()
    if (Dialogs.noAnswer) return

    //Adding the first tile
    game.addNewTile()

    //Drawing Grid
    game.getGrid()
    game.show()

    var allowedDirections = game.canMove()

    //Game Loop
    while (game.isRunning && isOn) {

      //Playing sound effect when moving tiles
      val musicfile = new File("./src/bubble.wav")
      val clip = AudioSystem.getClip()
      val audio = AudioSystem.getAudioInputStream(musicfile)
      clip.open(audio)
      clip.start()


      //Test all 4 directions and block the impossible ones

      if (lastDirection >= 0 && lastDirection <= 4) {
        if (allowedDirections(lastDirection) == true) {
          if (lastDirection != -1) game.moveTiles(lastDirection)
          //& if(moveTiles to direction is allowed)
          game.score()
          game.winner()
          game.addNewTile()
          game.show()
          game.winner()
        }
        allowedDirections = game.canMove()
        if (allowedDirections.sameElements(Array(false, false, false, false)) == true && looser == false) {
          //If no direction is possible it means that you loose
          //We totally block the game in this case
          looser = true
          game.looooooooooser()
        }
      }

      //Reset lastDirection value
      lastDirection = -1

      //Waiting for user input
      if (lastDirection == -1) {
        while (waitingInput  && isOn) {
          if (lastDirection != -1) waitingInput = false
          Thread.sleep(3)
        }
        waitingInput = true
      }
      clip.stop()
    }
  }
}
