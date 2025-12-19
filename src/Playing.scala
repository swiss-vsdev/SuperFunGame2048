import hevs.graphics.FunGraphics

import java.awt.event.{KeyEvent, KeyListener}

object Playing extends App {
  val windowWidth : Int = 400
  val windowHeight : Int = 600
  val display : FunGraphics = new FunGraphics(windowWidth, windowHeight)


  val game : Board = new Board()

  for(i <- 0 to 1){
    game.addNewTile()
  }
  /*game.moveTiles()
  while(game.isRunning){
    game.score()
    game.winner()
    game.looooooooooser()
    game.addNewTile()
    game.show()
  }*/





}
