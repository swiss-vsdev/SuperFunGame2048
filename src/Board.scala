import hevs.graphics.FunGraphics
import hevs.graphics.utils.GraphicsBitmap
import java.awt.Color
import java.awt.Font
import javax.sound.sampled.AudioSystem
import java.io.File

class Board(val display: FunGraphics) {
  //Generic Coordinates for the tiles drawing
  val gridCoordinates: Array[Array[GridTile]] = Array(
    Array(new GridTile(65, 200, 60, 60), new GridTile(135, 200, 60, 60), new GridTile(205, 200, 60, 60), new GridTile(275, 200, 60, 60)),
    Array(new GridTile(65, 270, 60, 60), new GridTile(135, 270, 60, 60), new GridTile(205, 270, 60, 60), new GridTile(275, 270, 60, 60)),
    Array(new GridTile(65, 340, 60, 60), new GridTile(135, 340, 60, 60), new GridTile(205, 340, 60, 60), new GridTile(275, 340, 60, 60)),
    Array(new GridTile(65, 410, 60, 60), new GridTile(135, 410, 60, 60), new GridTile(205, 410, 60, 60), new GridTile(275, 410, 60, 60)),
  )
  var mainBoard: Array[Array[Int]] = Array.ofDim(4, 4)
  var scoreValue: Int = 0
  var username: String = ""
  var isAWinner: Boolean = false

  def addNewTile(): Unit = {
    //Each loop, add a 2 or a 4 randomly on a free tile
    val rand: Int = (math.random() * 10).toInt
    var numToAdd: Int = 2;
    if (rand <= 2) {
      numToAdd = 4
    }

    val freeSN = getFreeSpacesNumber()
    var rand2: Int = (math.random() * freeSN).toInt
    if (rand2 < 0) {
      rand2 = 0
    } else if (rand2 > 16) {
      rand2 = 15
    }

    if (freeSN <= 0) { // Check if a move is possible. In that case score increases and the player is not a looser
    } else {
      val x = getFreeSpacesPosition()(rand2)(0)
      val y = getFreeSpacesPosition()(rand2)(1)
      mainBoard(x)(y) = numToAdd
    }
  }

  def getFreeSpacesPosition(): Array[Array[Int]] = {
    // Get the (x,y) postitions of all empty tiles
    val freeSpaceArray: Array[Array[Int]] = Array.ofDim(getFreeSpacesNumber(), 2)

    var freeSpaceArrayIndices: Int = 0;
    for (x <- mainBoard.indices) {
      for (y <- mainBoard(x).indices) {
        if (mainBoard(x)(y) == 0) {
          freeSpaceArray(freeSpaceArrayIndices)(0) = x
          freeSpaceArray(freeSpaceArrayIndices)(1) = y
          freeSpaceArrayIndices += 1
        }
      }
    }

    freeSpaceArray
  }

  def getFreeSpacesNumber(): Int = {
    // Search for number of empty tiles
    var freeSpace: Int = 0;

    for (x <- mainBoard.indices) {
      for (y <- mainBoard(x).indices) {
        if (mainBoard(x)(y) == 0) {
          freeSpace += 1
        }
      }
    }
    freeSpace
  }

  def canMove(): Array[Boolean] = {
    // Every loop of the game, try on every direction if the board can move (if there is space between the numbers & the
    // wall and if the number can merge)
    // Return four Boolean, one for each direction
    val direction: Array[Boolean] = Array(false, false, false, false)

    //Down
    var compareBoard: Array[Array[Int]] = Array.ofDim(4, 4)
    for (i <- 0 to 3) {
      val line: Array[Int] = Array(mainBoard(3)(i), mainBoard(2)(i), mainBoard(1)(i), mainBoard(0)(i));
      val newLine = lineProcessor(line, false)
      compareBoard(3)(i) = newLine(0)
      compareBoard(2)(i) = newLine(1)
      compareBoard(1)(i) = newLine(2)
      compareBoard(0)(i) = newLine(3)
    }
    if (!compareBoard(0).sameElements(mainBoard(0)) // Si One element is not the same after the check the move is possible
      || !compareBoard(1).sameElements(mainBoard(1))
      || !compareBoard(2).sameElements(mainBoard(2))
      || !compareBoard(3).sameElements(mainBoard(3))
    ) {
      direction(0) = true
    } else {
      direction(0) = false
    }

    compareBoard = Array.ofDim(4, 4) // Reset Compairing Array

    //Left
    for (i <- 0 to 3) {
      val line: Array[Int] = Array(mainBoard(i)(0), mainBoard(i)(1), mainBoard(i)(2), mainBoard(i)(3));
      val newLine = lineProcessor(line, false)
      compareBoard(i)(0) = newLine(0)
      compareBoard(i)(1) = newLine(1)
      compareBoard(i)(2) = newLine(2)
      compareBoard(i)(3) = newLine(3)
    }
    if (!compareBoard(0).sameElements(mainBoard(0))
      || !compareBoard(1).sameElements(mainBoard(1))
      || !compareBoard(2).sameElements(mainBoard(2))
      || !compareBoard(3).sameElements(mainBoard(3))
    ) {
      direction(1) = true
    } else {
      direction(1) = false
    }

    compareBoard = Array.ofDim(4, 4) // Reset Compairing Array

    //Up
    for (i <- 0 to 3) {
      val line: Array[Int] = Array(mainBoard(0)(i), mainBoard(1)(i), mainBoard(2)(i), mainBoard(3)(i));
      val newLine = lineProcessor(line, false)
      compareBoard(0)(i) = newLine(0)
      compareBoard(1)(i) = newLine(1)
      compareBoard(2)(i) = newLine(2)
      compareBoard(3)(i) = newLine(3)
    }
    if (!compareBoard(0).sameElements(mainBoard(0))
      || !compareBoard(1).sameElements(mainBoard(1))
      || !compareBoard(2).sameElements(mainBoard(2))
      || !compareBoard(3).sameElements(mainBoard(3))
    ) {
      direction(2) = true
    } else {
      direction(2) = false
    }

    compareBoard = Array.ofDim(4, 4) // Reset Comparing Array

    //Right
    for (i <- 0 to 3) {
      val line: Array[Int] = Array(mainBoard(i)(3), mainBoard(i)(2), mainBoard(i)(1), mainBoard(i)(0));
      val newLine = lineProcessor(line, false)
      compareBoard(i)(3) = newLine(0)
      compareBoard(i)(2) = newLine(1)
      compareBoard(i)(1) = newLine(2)
      compareBoard(i)(0) = newLine(3)
    }
    if (!compareBoard(0).sameElements(mainBoard(0))
      || !compareBoard(1).sameElements(mainBoard(1))
      || !compareBoard(2).sameElements(mainBoard(2))
      || !compareBoard(3).sameElements(mainBoard(3))
    ) {
      direction(3) = true
    } else {
      direction(3) = false
    }

    // Directions :
    // id 0 = Down
    // id 1 = Left
    // id 2 = Up
    // id 3 = Right
    direction
  }

  def moveTiles(direction: Int) = {
    // Trigger the movement for the whole board depending on the direction
    // id 0 = Down
    // id 1 = Left
    // id 2 = Up
    // id 3 = Right

    direction match {
      case 0 => { // Bas
        for (i <- 0 to 3) {
          val line: Array[Int] = Array(mainBoard(3)(i), mainBoard(2)(i), mainBoard(1)(i), mainBoard(0)(i));
          val newLine = lineProcessor(line, true)
          mainBoard(3)(i) = newLine(0)
          mainBoard(2)(i) = newLine(1)
          mainBoard(1)(i) = newLine(2)
          mainBoard(0)(i) = newLine(3)
        }
      }
      case 1 => { // Left
        for (i <- 0 to 3) {
          val line: Array[Int] = Array(mainBoard(i)(0), mainBoard(i)(1), mainBoard(i)(2), mainBoard(i)(3));
          val newLine = lineProcessor(line, true)
          mainBoard(i)(0) = newLine(0)
          mainBoard(i)(1) = newLine(1)
          mainBoard(i)(2) = newLine(2)
          mainBoard(i)(3) = newLine(3)
        }
      }
      case 2 => { // Up
        for (i <- 0 to 3) {
          val line: Array[Int] = Array(mainBoard(0)(i), mainBoard(1)(i), mainBoard(2)(i), mainBoard(3)(i));
          val newLine = lineProcessor(line, true)
          mainBoard(0)(i) = newLine(0)
          mainBoard(1)(i) = newLine(1)
          mainBoard(2)(i) = newLine(2)
          mainBoard(3)(i) = newLine(3)
        }
      }
      case 3 => { // Right
        for (i <- 0 to 3) {
          val line: Array[Int] = Array(mainBoard(i)(3), mainBoard(i)(2), mainBoard(i)(1), mainBoard(i)(0));
          val newLine = lineProcessor(line, true)
          mainBoard(i)(3) = newLine(0)
          mainBoard(i)(2) = newLine(1)
          mainBoard(i)(1) = newLine(2)
          mainBoard(i)(0) = newLine(3)
        }
      }
    }
  }

  def lineProcessor(line: Array[Int], scoring: Boolean): Array[Int] = {
    //Function to process a Line
    //(you give it a line, and it returns the processed line after deleting Zeros and merging numbers)

    var nbr1: Int = line(0)
    var nbr2: Int = line(1)
    var nbr3: Int = line(2)
    var nbr4: Int = line(3)

    if (nbr1 == 0 && nbr2 == 0 && nbr3 == 0 && nbr4 == 0) { // Nothing to do
      return line
    }

    // Sticking number to each others and to the top
    for (i <- 0 to 3) {
      if (nbr1 == 0) {
        nbr1 = nbr2
        nbr2 = nbr3
        nbr3 = nbr4
        nbr4 = 0
      }
    }
    for (i <- 0 to 2) {
      if (nbr2 == 0) {
        nbr2 = nbr3
        nbr3 = nbr4
        nbr4 = 0
      }
    }
    for (i <- 0 to 1) {
      if (nbr3 == 0) {
        nbr3 = nbr4
        nbr4 = 0
      }
    }

    //merging the numbers
    if (nbr1 != 0 && nbr1 == nbr2) {
      nbr1 = nbr1 + nbr2
      if (scoring) {
        scoreValue += nbr1
      }
      nbr2 = nbr3
      nbr3 = nbr4
      nbr4 = 0
    }
    if (nbr2 != 0 && nbr2 == nbr3) {
      nbr2 = nbr2 + nbr3
      if (scoring) {
        scoreValue += nbr2
      }
      nbr3 = nbr4
      nbr4 = 0
    }
    if (nbr3 != 0 && nbr3 == nbr4) {
      nbr3 = nbr3 + nbr4
      if (scoring) {
        scoreValue += nbr3
      }
      nbr4 = 0
    }

    //preparing the line to return it
    line(0) = nbr1
    line(1) = nbr2
    line(2) = nbr3
    line(3) = nbr4

    line
  }

  def show() = {
    // Displays the game
    val nbrSize: Int = 40
    val nbrColor: Color = Color.white
    val oneDigitLength: Array[Int] = Array[Int](82, 152, 222, 292)
    val oneDigitHeight: Array[Int] = Array[Int](245, 315, 385, 455)

    display.clear()
    getGrid()

    //Drawing the numbers on the Board
    for (x <- 0 until 4) {
      for (y <- 0 until 4) {
        if (mainBoard(x)(y) != 0) {
          display.drawString(nbrCoordinates(mainBoard(x)(y), oneDigitLength(y)), nbrHeight(mainBoard(x)(y), oneDigitHeight(x)), s"${mainBoard(x)(y)}", nbrColor, nbrSizeValue(mainBoard(x)(y), nbrSize))
        }
      }
    }
  }

  def getGrid(): Unit = {
    //Displays the Grid
    display.setColor(Color.BLACK)
    display.drawString(70, 50, "Super Fun 2048", Font.MONOSPACED, Font.BOLD, 30, Color.orange)

    display.setColor(Color.lightGray)
    display.drawFillRect(150, 100, 100, 50)
    display.drawString(155, 115, "Score", Font.MONOSPACED, Font.BOLD, 15, Color.white)
    display.drawString(155, 140, scoreValue.toString, Font.MONOSPACED, Font.BOLD, 20, Color.black)

    display.setColor(Color.gray)
    display.drawFillRect(55, 190, 290, 290)

    //Drawing the Grid with Fun Graphics
    for (x <- 0 until 4) {
      for (y <- 0 until 4) {
        display.setColor(caseColor(mainBoard(x)(y)))
        display.drawFillRect(gridCoordinates(x)(y).posX, gridCoordinates(x)(y).posY, gridCoordinates(x)(y).width, gridCoordinates(x)(y).height)
      }
    }
  }

  private def caseColor(in: Int): Color = {
    // Give the color for the cases, depend of the number printed on it
    in match {
      case 0 => Color.lightGray
      case 2 => new Color(227, 184, 43)
      case 4 => new Color(227, 166, 43)
      case 8 => new Color(227, 110, 43)
      case 16 => new Color(227, 80, 43)
      case 32 => new Color(227, 43, 43)
      case 64 => new Color(227, 43, 83)
      case 128 => new Color(227, 43, 163)
      case 256 => new Color(227, 43, 209)
      case 512 => new Color(175, 43, 227)
      case 1024 => new Color(104, 43, 227)
      case 2048 => new Color(0, 0, 0)
      case 4096 => new Color(255, 0, 0)
      case 8192 => new Color(0, 255, 0)
      case 16384 => new Color(0, 0, 255)
      case 32768 => new Color(43, 117, 227)
      case 65536 => new Color(43, 117, 227)
      case 131072 => new Color(43, 117, 227) // Maximal theoric number on 2048 games of 4x4 Board
      case 262144 => new Color(43, 117, 227)
      case 524288 => new Color(43, 117, 227)
      case 1048576 => new Color(43, 117, 227)
      case 2097152 => new Color(43, 117, 227)
    }
  }

  private def nbrCoordinates(in: Int, oneDigit: Int): Int = {
    //Help to print the numbers in the middle of the tiles
    if (in > 1000) {
      oneDigit - 12
    } else if (in > 100) {
      oneDigit - 11
    } else if (in > 10) {
      oneDigit - 12
    } else {
      oneDigit
    }
  }

  private def nbrSizeValue(in: Int, oneDigit: Int): Int = {
    //Manage the width of fonts for number printing
    if (in > 1000) {
      oneDigit - 20
    } else if (in > 100) {
      oneDigit - 14
    } else {
      oneDigit
    }
  }

  private def nbrHeight(in: Int, oneDigit: Int): Int = {
    //Manage the heigt of fonts for number printing
    if (in > 1000) {
      oneDigit - 7
    } else if (in > 100) {
      oneDigit - 5
    } else {
      oneDigit
    }
  }

  def winner() : Unit  = {
    // Show a winning picture when the player reach 2048
    //Initializing the variable highestValue
    var highestValue: Int = 0
    //Navigating the entire ArrayOfDim
    for (i <- mainBoard.indices) {
      for (j <- mainBoard(0).indices) {
        //Writes the highest value of the entire Array in highestValue
        if (highestValue < mainBoard(i)(j)) highestValue = mainBoard(i)(j)
      }
      //Print Winner ! if the highest value of any case is 2048
      if (highestValue == 2048 && isAWinner == false) {
        isAWinner = true
        println("WINNER !")
        val gb = new GraphicsBitmap("/Assets/winner.jpg")
        display.drawPicture(200, 300, gb)
        Thread.sleep(2000)
      }
    }
  }

  def looooooooooser() = {
    //Yes we know that the name of this function is not optimal. But it's for the joke (don't forget the fun in -FUN-GRAPHICS)
    //Play a Sound, Show a picture and pause the game

    //Writing the score in the score.txt
    val lbwrite = new Leaderboard(display)
    lbwrite.writefile(username, scoreValue)

    //Generates the image
    display.clear()
    val gb = new GraphicsBitmap("/Assets/looser.jpg")
    display.drawPicture(200, 300, gb)
    display.drawString(130, 550, s"Score : $scoreValue", Font.MONOSPACED, Font.BOLD, 30, Color.white)

    //Reminds you that you are a looser using a sound file
    val musicfile = new File("./src/Assets/game-over.wav")
    val clip = AudioSystem.getClip()
    val audio = AudioSystem.getAudioInputStream(musicfile)
    clip.open(audio)
    clip.start()
    Thread.sleep(clip.getMicrosecondLength / 1000)
    clip.stop()

    Thread.sleep(700)
    display.drawString(10, 20, "Press [Esc] key to leave the game", Font.MONOSPACED, Font.BOLD, 15, Color.black)
  }

  def askUsername(): String = {
    username = Dialogs.getString("Type your username")
    println(s"Hi $username !")
    username
  }

  override def toString: String = {
    //Print the board in the Terminal (Ready usefull to debug)
    val mainLine : String = "Board :\n"
    var out : String = ""
    var cnt : Int = 0

    for (x <- mainBoard.indices) {
      for (y <- mainBoard(x).indices){
        out += mainBoard(x)(y) + "|"
        cnt += 1
        if(cnt == 4){
          out += "\n"
          cnt = 0
        }
      }
    }
    mainLine + out
  }


  class GridTile(var posX: Int, var posY: Int, var width: Int, var height: Int) {}
}