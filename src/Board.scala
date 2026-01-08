import hevs.graphics.FunGraphics
import hevs.graphics.utils.GraphicsBitmap
import java.awt.Color
import java.awt.Font
import javax.sound.sampled.AudioSystem
import java.io.File

class Board(val display: FunGraphics) {
  var mainBoard: Array[Array[Int]] = Array.ofDim(4, 4)
  var scoreValue: Int = 0
  var username: String = ""
  var isAWinner: Boolean = false

  def addNewTile(): Unit = {
    var rand: Int = (math.random() * 10).toInt
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

    if (freeSN <= 0) { // Vérifier si c'est possible de faire un move, dans ce cas pas de looser mais pas de score+ non plus
      //looooooooooser() // Logique de loose ne devrait pas être ici
    } else {
      var x = getFreeSpacesPosition()(rand2)(0)
      var y = getFreeSpacesPosition()(rand2)(1)

      mainBoard(x)(y) = numToAdd
    }
  }

  def getFreeSpacesPosition(): Array[Array[Int]] = {
    var freeSpaceArray: Array[Array[Int]] = Array.ofDim(getFreeSpacesNumber(), 2)

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
    val direction: Array[Boolean] = Array(false, false, false, false)

    //Bas
    var compareBoard: Array[Array[Int]] = Array.ofDim(4, 4)
    for (i <- 0 to 3) {
      val line: Array[Int] = Array(mainBoard(3)(i), mainBoard(2)(i), mainBoard(1)(i), mainBoard(0)(i));
      val newLine = lineProcessor(line, false)
      compareBoard(3)(i) = newLine(0)
      compareBoard(2)(i) = newLine(1)
      compareBoard(1)(i) = newLine(2)
      compareBoard(0)(i) = newLine(3)
    }
    if (!compareBoard(0).sameElements(mainBoard(0)) // Si false alors éléments différent donc droit de bouger
      || !compareBoard(1).sameElements(mainBoard(1))
      || !compareBoard(2).sameElements(mainBoard(2))
      || !compareBoard(3).sameElements(mainBoard(3))
    ) {
      direction(0) = true
    } else {
      direction(0) = false
    }

    compareBoard = Array.ofDim(4, 4) // réinitialisation du tableau de comparaison

    //Gauche
    for (i <- 0 to 3) {
      var line: Array[Int] = Array(mainBoard(i)(0), mainBoard(i)(1), mainBoard(i)(2), mainBoard(i)(3));
      var newLine = lineProcessor(line, false)
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

    compareBoard = Array.ofDim(4, 4)

    //Haut
    for (i <- 0 to 3) {
      var line: Array[Int] = Array(mainBoard(0)(i), mainBoard(1)(i), mainBoard(2)(i), mainBoard(3)(i));
      var newLine = lineProcessor(line, false)
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

    compareBoard = Array.ofDim(4, 4)

    //Droite
    for (i <- 0 to 3) {
      var line: Array[Int] = Array(mainBoard(i)(3), mainBoard(i)(2), mainBoard(i)(1), mainBoard(i)(0));
      var newLine = lineProcessor(line, false)
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
    // id 0 = bas
    // id 1 = gauche
    // id 2 = haut
    // id 3 = droite

    direction
  }

  def moveTiles(direction: Int) = {
    // 0 = bas
    // 1 = gauche
    // 2 = haut
    // 3 = droite

    var xStart: Int = 0;
    var xEnd: Int = 3;
    var yStart: Int = 0;
    var yEnd: Int = 3;

    direction match {
      case 0 => { // Bas
        for (i <- 0 to 3) {
          val line: Array[Int] = Array(mainBoard(3)(i), mainBoard(2)(i), mainBoard(1)(i), mainBoard(0)(i));
          val newLine = lineProcessor(line, true)
          //println("Line processed = " + newLine)
          mainBoard(3)(i) = newLine(0)
          mainBoard(2)(i) = newLine(1)
          mainBoard(1)(i) = newLine(2)
          mainBoard(0)(i) = newLine(3)
        }
        //printBoard
      }
      case 1 => { // Gauche
        for (i <- 0 to 3) {
          val line: Array[Int] = Array(mainBoard(i)(0), mainBoard(i)(1), mainBoard(i)(2), mainBoard(i)(3));
          val newLine = lineProcessor(line, true)
          //println("Line processed = " + newLine)
          mainBoard(i)(0) = newLine(0)
          mainBoard(i)(1) = newLine(1)
          mainBoard(i)(2) = newLine(2)
          mainBoard(i)(3) = newLine(3)
        }
      }
      case 2 => { // Haut
        for (i <- 0 to 3) {
          val line: Array[Int] = Array(mainBoard(0)(i), mainBoard(1)(i), mainBoard(2)(i), mainBoard(3)(i));
          val newLine = lineProcessor(line, true)
          //println("Line processed = " + newLine)
          mainBoard(0)(i) = newLine(0)
          mainBoard(1)(i) = newLine(1)
          mainBoard(2)(i) = newLine(2)
          mainBoard(3)(i) = newLine(3)
        }
      }
      case 3 => { // Droite
        for (i <- 0 to 3) {
          val line: Array[Int] = Array(mainBoard(i)(3), mainBoard(i)(2), mainBoard(i)(1), mainBoard(i)(0));
          val newLine = lineProcessor(line, true)
          //println("Line processed = " + newLine)
          mainBoard(i)(3) = newLine(0)
          mainBoard(i)(2) = newLine(1)
          mainBoard(i)(1) = newLine(2)
          mainBoard(i)(0) = newLine(3)
        }
      }
    }

    for (i <- mainBoard.indices) {
      for (j <- mainBoard(0).indices) {

      }
    }

  }

  def lineProcessor(line: Array[Int], scoring: Boolean): Array[Int] = {
    var nbr1: Int = line(0)
    var nbr2: Int = line(1)
    var nbr3: Int = line(2)
    var nbr4: Int = line(3)

    if (nbr1 == 0 && nbr2 == 0 && nbr3 == 0 && nbr4 == 0) { // Nothing to do
      return line
    }

    /*println("Before sticking")
    println("Nbr1 = " + nbr1)
    println("Nbr2 = " + nbr2)
    println("Nbr3 = " + nbr3)
    println("Nbr4 = " + nbr4)*/

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
    if(nbr1 != 0 && nbr1 == nbr2){
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
    line(0) = nbr1
    line(1) = nbr2
    line(2) = nbr3
    line(3) = nbr4

    /*println("After sticking")
    println("Nbr1 = " + nbr1)
    println("Nbr2 = " + nbr2)
    println("Nbr3 = " + nbr3)
    println("Nbr4 = " + nbr4)*/

    line
  }

  def score(): Int = {
    scoreValue
  }

  def show() = {
    val nbrSize: Int = 40
    val nbrColor: Color = Color.white
    val oneDigitLength1 = 82
    val oneDigitLength2 = 152
    val oneDigitLength3 = 222
    val oneDigitLength4 = 292

    val oneDigitHeight1 = 245
    val oneDigitHeight2 = 315
    val oneDigitHeight3 = 385
    val oneDigitHeight4 = 455

    display.clear()
    getGrid()

    if (mainBoard(0)(0) != 0) display.drawString(nbrCoordinates(mainBoard(0)(0), oneDigitLength1), nbrHeight(mainBoard(0)(0), oneDigitHeight1), s"${mainBoard(0)(0)}", nbrColor, nbrSizeValue(mainBoard(0)(0), nbrSize))
    if (mainBoard(0)(1) != 0) display.drawString(nbrCoordinates(mainBoard(0)(1), oneDigitLength2), nbrHeight(mainBoard(0)(1), oneDigitHeight1), s"${mainBoard(0)(1)}", nbrColor, nbrSizeValue(mainBoard(0)(1), nbrSize))
    if (mainBoard(0)(2) != 0) display.drawString(nbrCoordinates(mainBoard(0)(2), oneDigitLength3), nbrHeight(mainBoard(0)(2), oneDigitHeight1), s"${mainBoard(0)(2)}", nbrColor, nbrSizeValue(mainBoard(0)(2), nbrSize))
    if (mainBoard(0)(3) != 0) display.drawString(nbrCoordinates(mainBoard(0)(3), oneDigitLength4), nbrHeight(mainBoard(0)(3), oneDigitHeight1), s"${mainBoard(0)(3)}", nbrColor, nbrSizeValue(mainBoard(0)(3), nbrSize))

    if (mainBoard(1)(0) != 0) display.drawString(nbrCoordinates(mainBoard(1)(0), oneDigitLength1), nbrHeight(mainBoard(1)(0), oneDigitHeight2), s"${mainBoard(1)(0)}", nbrColor, nbrSizeValue(mainBoard(1)(0), nbrSize))
    if (mainBoard(1)(1) != 0) display.drawString(nbrCoordinates(mainBoard(1)(1), oneDigitLength2), nbrHeight(mainBoard(1)(1), oneDigitHeight2), s"${mainBoard(1)(1)}", nbrColor, nbrSizeValue(mainBoard(1)(1), nbrSize))
    if (mainBoard(1)(2) != 0) display.drawString(nbrCoordinates(mainBoard(1)(2), oneDigitLength3), nbrHeight(mainBoard(1)(2), oneDigitHeight2), s"${mainBoard(1)(2)}", nbrColor, nbrSizeValue(mainBoard(1)(2), nbrSize))
    if (mainBoard(1)(3) != 0) display.drawString(nbrCoordinates(mainBoard(1)(3), oneDigitLength4), nbrHeight(mainBoard(1)(3), oneDigitHeight2), s"${mainBoard(1)(3)}", nbrColor, nbrSizeValue(mainBoard(1)(3), nbrSize))

    if (mainBoard(2)(0) != 0) display.drawString(nbrCoordinates(mainBoard(2)(0), oneDigitLength1), nbrHeight(mainBoard(2)(0), oneDigitHeight3), s"${mainBoard(2)(0)}", nbrColor, nbrSizeValue(mainBoard(2)(0), nbrSize))
    if (mainBoard(2)(1) != 0) display.drawString(nbrCoordinates(mainBoard(2)(1), oneDigitLength2), nbrHeight(mainBoard(2)(1), oneDigitHeight3), s"${mainBoard(2)(1)}", nbrColor, nbrSizeValue(mainBoard(2)(1), nbrSize))
    if (mainBoard(2)(2) != 0) display.drawString(nbrCoordinates(mainBoard(2)(2), oneDigitLength3), nbrHeight(mainBoard(2)(2), oneDigitHeight3), s"${mainBoard(2)(2)}", nbrColor, nbrSizeValue(mainBoard(2)(2), nbrSize))
    if (mainBoard(2)(3) != 0) display.drawString(nbrCoordinates(mainBoard(2)(3), oneDigitLength4), nbrHeight(mainBoard(2)(3), oneDigitHeight3), s"${mainBoard(2)(3)}", nbrColor, nbrSizeValue(mainBoard(2)(3), nbrSize))

    if (mainBoard(3)(0) != 0) display.drawString(nbrCoordinates(mainBoard(3)(0), oneDigitLength1), nbrHeight(mainBoard(3)(0), oneDigitHeight4), s"${mainBoard(3)(0)}", nbrColor, nbrSizeValue(mainBoard(3)(0), nbrSize))
    if (mainBoard(3)(1) != 0) display.drawString(nbrCoordinates(mainBoard(3)(1), oneDigitLength2), nbrHeight(mainBoard(3)(1), oneDigitHeight4), s"${mainBoard(3)(1)}", nbrColor, nbrSizeValue(mainBoard(3)(1), nbrSize))
    if (mainBoard(3)(2) != 0) display.drawString(nbrCoordinates(mainBoard(3)(2), oneDigitLength3), nbrHeight(mainBoard(3)(2), oneDigitHeight4), s"${mainBoard(3)(2)}", nbrColor, nbrSizeValue(mainBoard(3)(2), nbrSize))
    if (mainBoard(3)(3) != 0) display.drawString(nbrCoordinates(mainBoard(3)(3), oneDigitLength4), nbrHeight(mainBoard(3)(3), oneDigitHeight4), s"${mainBoard(3)(3)}", nbrColor, nbrSizeValue(mainBoard(3)(3), nbrSize))


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

    display.setColor(caseColor(mainBoard(0)(0)))
    display.drawFillRect(65, 200, 60, 60)
    display.setColor(caseColor(mainBoard(0)(1)))
    display.drawFillRect(135, 200, 60, 60)
    display.setColor(caseColor(mainBoard(0)(2)))
    display.drawFillRect(205, 200, 60, 60)
    display.setColor(caseColor(mainBoard(0)(3)))
    display.drawFillRect(275, 200, 60, 60)
    display.setColor(caseColor(mainBoard(1)(0)))
    display.drawFillRect(65, 270, 60, 60)
    display.setColor(caseColor(mainBoard(1)(1)))
    display.drawFillRect(135, 270, 60, 60)
    display.setColor(caseColor(mainBoard(1)(2)))
    display.drawFillRect(205, 270, 60, 60)
    display.setColor(caseColor(mainBoard(1)(3)))
    display.drawFillRect(275, 270, 60, 60)
    display.setColor(caseColor(mainBoard(2)(0)))
    display.drawFillRect(65, 340, 60, 60)
    display.setColor(caseColor(mainBoard(2)(1)))
    display.drawFillRect(135, 340, 60, 60)
    display.setColor(caseColor(mainBoard(2)(2)))
    display.drawFillRect(205, 340, 60, 60)
    display.setColor(caseColor(mainBoard(2)(3)))
    display.drawFillRect(275, 340, 60, 60)
    display.setColor(caseColor(mainBoard(3)(0)))
    display.drawFillRect(65, 410, 60, 60)
    display.setColor(caseColor(mainBoard(3)(1)))
    display.drawFillRect(135, 410, 60, 60)
    display.setColor(caseColor(mainBoard(3)(2)))
    display.drawFillRect(205, 410, 60, 60)
    display.setColor(caseColor(mainBoard(3)(3)))
    display.drawFillRect(275, 410, 60, 60)
  }

  private def caseColor(in: Int): Color = {
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
      case 131072 => new Color(43, 117, 227)
      case 262144 => new Color(43, 117, 227)
      case 524288 => new Color(43, 117, 227)
      case 1048576 => new Color(43, 117, 227)
      case 2097152 => new Color(43, 117, 227)
    }
  }

  private def nbrCoordinates(in: Int, oneDigit: Int): Int = {
    if (in > 1000) {
      return oneDigit - 12
    } else if (in > 100) {
      return oneDigit - 11
    } else if (in > 10) {
      return oneDigit - 12
    } else {
      return oneDigit
    }
  }

  private def nbrSizeValue(in: Int, oneDigit: Int): Int = {
    if (in > 1000) {
      return oneDigit - 20
    } else if (in > 100) {
      return oneDigit - 14
    } else {
      return oneDigit
    }
  }

  private def nbrHeight(in: Int, oneDigit: Int): Int = {
    if (in > 1000) {
      return oneDigit - 7
    } else if (in > 100) {
      return oneDigit - 5
    } else {
      return oneDigit
    }
  }

  def winner() = {
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
        val gb = new GraphicsBitmap("/winner.jpg")
        display.drawPicture(200, 300, gb)
        Thread.sleep(2000)
      }
    }
  }

  def looooooooooser() = {
    val lbwrite = new Leaderboard(display)
    lbwrite.writefile(username, scoreValue)
    display.clear()
    val gb = new GraphicsBitmap("/looser.jpg")
    display.drawPicture(200, 300, gb)
    display.drawString(130, 550, s"Score : $scoreValue", Font.MONOSPACED, Font.BOLD, 30, Color.white)

    val musicfile = new File("./src/game-over.wav")
    val clip = AudioSystem.getClip()
    val audio = AudioSystem.getAudioInputStream(musicfile)
    clip.open(audio)
    clip.start()
    Thread.sleep(clip.getMicrosecondLength / 1000)
    clip.stop()

    Thread.sleep(1000)
    display.drawString(10, 20, "Press [Esc] key to leave the game", Font.MONOSPACED, Font.BOLD, 15, Color.black)
  }

  def printBoard(): Unit = {
    println("Board = ______________________")
    for (x <- mainBoard.indices) {
      println(mainBoard(x).mkString("/"))
    }
  }

  def isRunning(): Boolean = {
    true
  }

  def askUsername(): String = {
    username = Dialogs.getString("Type your username")
    println(s"Hi $username !")
    username
  }
}