import hevs.graphics.FunGraphics

object Start extends App{
  val display = new FunGraphics(400, 600)
  val mainMenu = new MainMenu(display)
  mainMenu.run()
}
