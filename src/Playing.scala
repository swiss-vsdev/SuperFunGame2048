import hevs.graphics.FunGraphics

import java.awt.event.{KeyEvent, KeyListener}

object Playing extends App {
  val windowWidth : Int = 400
  val windowHeight : Int = 600
  val display : FunGraphics = new FunGraphics(windowWidth, windowHeight)
  val kl : KeyListener = KeyListener

  display.setKeyManager(kl)

  /*
  val game : Array[Array[Int]] = new Board()
  game.moveTiles()
  for(i <- 0 to 1){
    game.addNewTile()
  }
  while(game.isRunning){
    game.score()
    game.winner()
    game.looooooooooser()
    game.addNewTile()
    game.show()
  }

 */



}
