import hevs.graphics.FunGraphics
import java.awt.Color
import java.awt.Font

class Board (val display : FunGraphics) {
  var mainBoard : Array[Array[Int]] = Array.ofDim(4,4)
  var boardBefore : Array[Array[Int]] = Array.ofDim(4,4)

  var tilesToMove : Array[TileToMove] =  Array.fill(12)(new TileToMove(0,0,0,0,0,0,0,0, java.awt.Color.red, false))

  var scoreValue : Int = 0

  val oneDigitLength1 = 82
  val oneDigitLength2 = 152
  val oneDigitLength3 = 222
  val oneDigitLength4 = 292

  val oneDigitHeight1 = 245
  val oneDigitHeight2 = 315
  val oneDigitHeight3 = 385
  val oneDigitHeight4 = 455

  //Generic Coordinates for the tiles drawing
  val gridCoordinates: Array[Array[GridTile]] = Array(
    Array(new GridTile(65, 200, 60, 60), new GridTile(135, 200, 60, 60), new GridTile(205, 200, 60, 60), new GridTile(275, 200, 60, 60)),
    Array(new GridTile(65, 270, 60, 60), new GridTile(135, 270, 60, 60), new GridTile(205, 270, 60, 60), new GridTile(275, 270, 60, 60)),
    Array(new GridTile(65, 340, 60, 60), new GridTile(135, 340, 60, 60), new GridTile(205, 340, 60, 60), new GridTile(275, 340, 60, 60)),
    Array(new GridTile(65, 410, 60, 60), new GridTile(135, 410, 60, 60), new GridTile(205, 410, 60, 60), new GridTile(275, 410, 60, 60)),
  )

  def addNewTile() = {
    var rand : Int = (math.random() * 10).toInt
    var numToAdd: Int = 2;
    if (rand <= 2){
      numToAdd = 4
    }

    val freeSN = getFreeSpacesNumber()
    var rand2 : Int = (math.random() * freeSN).toInt
    if(rand2 < 0){
      rand2 = 0
    } else if(rand2 > 16){
      rand2 = 15
    }

    if( freeSN <= 0){ // Vérifier si c'est possible de faire un move, dans ce cas pas de looser mais pas de score+ non plus
      //looooooooooser() // Logique de loose ne devrait pas être ici
    } else {
      var x = getFreeSpacesPosition()(rand2)(0)
      var y = getFreeSpacesPosition()(rand2)(1)

      mainBoard(x)(y) = numToAdd
    }
    //printBoard
  }

  def canMove(): Array[Boolean] = {
    var direction: Array[Boolean] = Array(false,false,false,false)

    //Bas
    var compareBoard : Array[Array[Int]] = Array.ofDim(4,4)
    for (i <- 0 to 3){
      var line : Array[Int] =  Array(mainBoard(3)(i), mainBoard(2)(i), mainBoard(1)(i), mainBoard(0)(i));
      var newLine = lineProcessor(line)
      compareBoard(3)(i) = newLine(0)
      compareBoard(2)(i) = newLine(1)
      compareBoard(1)(i) = newLine(2)
      compareBoard(0)(i) = newLine(3)
    }
    if (!compareBoard(0).sameElements(mainBoard(0)) // Si false alors éléments différent donc droit de bouger
      || !compareBoard(1).sameElements(mainBoard(1))
      || !compareBoard(2).sameElements(mainBoard(2))
      || !compareBoard(3).sameElements(mainBoard(3))
    ){
      direction(0) = true
    } else {
      direction(0) = false
    }

    compareBoard = Array.ofDim(4,4) // réinitialisation du tableau de comparaison

    //Gauche
    for (i <- 0 to 3){
      var line : Array[Int] =  Array(mainBoard(i)(0), mainBoard(i)(1), mainBoard(i)(2), mainBoard(i)(3));
      var newLine = lineProcessor(line)
      compareBoard(i)(0) = newLine(0)
      compareBoard(i)(1) = newLine(1)
      compareBoard(i)(2) = newLine(2)
      compareBoard(i)(3) = newLine(3)
    }
    if (!compareBoard(0).sameElements(mainBoard(0))
      || !compareBoard(1).sameElements(mainBoard(1))
      || !compareBoard(2).sameElements(mainBoard(2))
      || !compareBoard(3).sameElements(mainBoard(3))
    ){
      direction(1) = true
    } else {
      direction(1) = false
    }

    compareBoard = Array.ofDim(4,4)

    //Haut
    for (i <- 0 to 3){
      var line : Array[Int] =  Array(mainBoard(0)(i), mainBoard(1)(i), mainBoard(2)(i), mainBoard(3)(i));
      var newLine = lineProcessor(line)
      compareBoard(0)(i) = newLine(0)
      compareBoard(1)(i) = newLine(1)
      compareBoard(2)(i) = newLine(2)
      compareBoard(3)(i) = newLine(3)
    }
    if (!compareBoard(0).sameElements(mainBoard(0))
      || !compareBoard(1).sameElements(mainBoard(1))
      || !compareBoard(2).sameElements(mainBoard(2))
      || !compareBoard(3).sameElements(mainBoard(3))
    ){
      direction(2) = true
    } else {
      direction(2) = false
    }

    compareBoard = Array.ofDim(4,4)

    //Droite
    for (i <- 0 to 3){
      var line : Array[Int] =  Array(mainBoard(i)(3), mainBoard(i)(2), mainBoard(i)(1), mainBoard(i)(0));
      var newLine = lineProcessor(line)
      compareBoard(i)(3) = newLine(0)
      compareBoard(i)(2) = newLine(1)
      compareBoard(i)(1) = newLine(2)
      compareBoard(i)(0) = newLine(3)
    }
    if (!compareBoard(0).sameElements(mainBoard(0))
      || !compareBoard(1).sameElements(mainBoard(1))
      || !compareBoard(2).sameElements(mainBoard(2))
      || !compareBoard(3).sameElements(mainBoard(3))
    ){
      direction(3) = true
    } else {
      direction(3) = false
    }

    // Directions :
    // id 0 = bas
    // id 1 = gauche
    // id 2 = haut
    // id 3 = droite
    return direction
  }

  def moveTiles(direction : Int) = {
    // 0 = bas
    // 1 = gauche
    // 2 = haut
    // 3 = droite
    var xStart : Int = 0;
    var xEnd : Int = 3;
    var yStart : Int = 0;
    var yEnd : Int = 3;

    var tileToMoveId = 0;

    boardBefore = mainBoard.clone()

    direction match {
      case 0 => {// Bas
        for (i <- 0 to 3){
          var line : Array[Int] =  Array(mainBoard(3)(i), mainBoard(2)(i), mainBoard(1)(i), mainBoard(0)(i));
          var newLine = lineProcessor(line)
          println("Line processed = " + newLine)
          mainBoard(3)(i) = newLine(0)
          mainBoard(2)(i) = newLine(1)
          mainBoard(1)(i) = newLine(2)
          mainBoard(0)(i) = newLine(3)

          if(newLine(7) > 0){
            println("longueur du mouvement :" + newLine(7) )
            tilesToMove(tileToMoveId) = new TileToMove(3,i,3-newLine(7), i,nbrCoordinates(boardBefore(3)(i), oneDigitLength1), nbrHeight(mainBoard(3)(i),oneDigitHeight1),0,0, caseColor(boardBefore(3)(i)), active = true)
            tileToMoveId += 1
          }
          if(newLine(6) > 0){
            tilesToMove(tileToMoveId) = new TileToMove(2,i,2-newLine(6), i,nbrCoordinates(boardBefore(2)(i), oneDigitLength1), nbrHeight(mainBoard(2)(i),oneDigitHeight1),0,0, caseColor(boardBefore(2)(i)), active = true)
            tileToMoveId += 1
          }
          if(newLine(5) > 0){
            tilesToMove(tileToMoveId) = new TileToMove(1,i,1-newLine(5), i,nbrCoordinates(boardBefore(1)(i), oneDigitLength1), nbrHeight(mainBoard(1)(i),oneDigitHeight1),0,0, caseColor(boardBefore(1)(i)), active = true)
            tileToMoveId += 1
          }
        }
        //printBoard
      }
      case 1 => {// Gauche
        for (i <- 0 to 3){
          var line : Array[Int] =  Array(mainBoard(i)(0), mainBoard(i)(1), mainBoard(i)(2), mainBoard(i)(3));
          var newLine = lineProcessor(line)
          println("Line processed = " + newLine)
          mainBoard(i)(0) = newLine(0)
          mainBoard(i)(1) = newLine(1)
          mainBoard(i)(2) = newLine(2)
          mainBoard(i)(3) = newLine(3)
        }
      }
      case 2 => { // Haut
        for (i <- 0 to 3){
          var line : Array[Int] =  Array(mainBoard(0)(i), mainBoard(1)(i), mainBoard(2)(i), mainBoard(3)(i));
          var newLine = lineProcessor(line)
          println("Line processed = " + newLine)
          mainBoard(0)(i) = newLine(0)
          mainBoard(1)(i) = newLine(1)
          mainBoard(2)(i) = newLine(2)
          mainBoard(3)(i) = newLine(3)
        }
      }
      case 3 => { // Droite
        for (i <- 0 to 3){
          var line : Array[Int] =  Array(mainBoard(i)(3), mainBoard(i)(2), mainBoard(i)(1), mainBoard(i)(0));
          var newLine = lineProcessor(line)
          println("Line processed = " + newLine)
          mainBoard(i)(3) = newLine(0)
          mainBoard(i)(2) = newLine(1)
          mainBoard(i)(1) = newLine(2)
          mainBoard(i)(0) = newLine(3)

        }
      }
    }

    for (i <- mainBoard.indices){
      for (j <- mainBoard(0).indices){

      }
    }

  }

  def lineProcessor(line : Array[Int]): Array[Int] = {
    //id 0 to 3 = numbers at them new position
    //id 4 to 7 = movement of each number at the position before (id3 = id7) (id2 = id6) etc..
    /*var nbr1 : Int = line(0)
    var nbr2 : Int = line(1)
    var nbr3 : Int = line(2)
    var nbr4 : Int = line(3)*/

    var newLine = new Array[Int](8)

    if (line(0) == 0 && line(1) == 0 && line(2) == 0 && line(3) == 0){ // Nothing to do
      return newLine
    }

    var lineOnStart:Array[nbr] = new Array[nbr](4)

    //initialisation of the array
    for(i <- 0 to 3){
      if (line(i) != 0){
        lineOnStart(i) = new nbr(line(i), i, i, true)
      } else {
        lineOnStart(i) = new nbr(line(i), i, i, false)
      }
    }

    //Movement calculation  caused by the 0 between numbers
    if(lineOnStart(0).nbr == 0){
      lineOnStart(1).posEnd -= 1
      lineOnStart(2).posEnd -= 1
      lineOnStart(3).posEnd -= 1
    }

    if(lineOnStart(1).nbr == 0){
      lineOnStart(2).posEnd -= 1
      lineOnStart(3).posEnd -= 1
    }

    if(lineOnStart(2).nbr == 0){
      lineOnStart(3).posEnd -= 1
    }


    var lineAfterZeroCalc:Array[nbr] = new Array[nbr](4)

    //initialisation of the array Without The Zero between the numbers
    for(i <- 0 to 3){
      lineAfterZeroCalc(i) = lineOnStart(i)
      for(y <- 0 to 3){
        if (lineOnStart(y).posEnd == i && lineOnStart(y).exist == true){
          lineAfterZeroCalc(i) = lineOnStart(y)
        }
      }
    }

    //Calculation of the mergings (3layers of merging maximum)
    for(i <- 0 to 2){
      if(lineAfterZeroCalc(i).nbr == lineAfterZeroCalc(i+1).nbr){
        lineAfterZeroCalc(i).exist = false
        lineAfterZeroCalc(i+1).nbr *= 2
        lineAfterZeroCalc(i+1).posEnd -= 1
      }
    }


    var lineAfter1StMerging:Array[nbr] = new Array[nbr](4)

    //initialisation of the array with the number merged
    for(i <- 0 to 3){
      lineOnStart(i) = new nbr(line(i), i, i, false)
      for(y <- 0 to 3){
        if (lineOnStart(y).posEnd == i && lineOnStart(y).exist == true){
          lineAfterZeroCalc(i) = lineOnStart(y)
        }
      }
    }

    //Calculation of the mergings (3layers of merging maximum)
    for(i <- 0 to 2){
      if(lineAfterZeroCalc(i).nbr == lineAfterZeroCalc(i+1).nbr){
        lineAfterZeroCalc(i).exist = false
        lineAfterZeroCalc(i+1).nbr *= 2
        lineAfterZeroCalc(i+1).posEnd -= 1
      }
    }





    return newLine

    class nbr(var nbr:Int = 0, var posStart:Int = 0, var posEnd:Int = 0, var exist:Boolean = false) {

    }
  }

  def score(): Int = {
      scoreValue
  }

  def animate(side: Int) = {
    // // id 0 = bas
    // // id 1 = gauche
    // // id 2 = haut
    // // id 3 = droite


    val nbrSize : Int = 40
    val nbrColor : Color = Color.white

    //var toMove = new Array[Array[3]]
    var workToDo: Boolean = true;

    var nbrOfTiles : Int = 0
    tilesToMove.foreach(tile => { //comptage du nombre de tiles à bouger
      if(tile.active == true){
        nbrOfTiles += 1
      }
    })

    var i : Int = 0
    while(workToDo){
      tilesToMove.foreach(tile => {
        //tant que posX =! posX calculé pour xEndId ET pareil pour yEndId (dépends si vertical ou horizontal)
        //!!!!!! En gros faut calculer déjà à la définition du tilesToMove la position x ou y finales de la tiles
        //!!!!!! Avec la destination finale on peut checker quand finir l'animation pour la tile


        //display.drawString(tile.posX, nbrHeight(mainBoard(0)(0),oneDigitHeight1), s"${mainBoard(0)(0)}", nbrColor, nbrSizeValue(mainBoard(0)(0), nbrSize)
        if(tile.active == true) {
          println("Tile : " + tile.posX)
          println("Tile = " + tile)
          //display.clear()
          //show()
          display.drawFillRect((gridCoordinates(tile.xStartId)(tile.yStartId).posX + tile.xMoovement), (gridCoordinates(tile.xStartId)(tile.yStartId).posY + tile.yMoovement), 60, 60)
          display.drawString(tile.posX, tile.posY, s"${boardBefore(tile.xStartId)(tile.yStartId)}", nbrColor, nbrSizeValue(boardBefore(tile.xStartId)(tile.yStartId), nbrSize))
          //display.drawFillRect(65, 200, 60, 60) // Doit réimprimer le carré aussi
          if(tile.posX != gridCoordinates(tile.xEndId)(tile.yEndId).posX || tile.posY != gridCoordinates(tile.xEndId)(tile.yEndId).posY){
            side match {
              case 0 => {
                tile.posY += 1
                tile.yMoovement -= 1
              }
              case 1 => {
                tile.posX += 1
                tile.xMoovement -= 1
              }
              case 2 => {
                tile.posY -= 1
                tile.yMoovement -= 1
              }
              case 3 => {
                tile.posX -= 1
                tile.xMoovement -= 1
              }
              case _ => {}
            }
          } else {
            nbrOfTiles -= 1
            tile.active = false
          }

        }
      })
      i += 1
      Thread.sleep(1)

      if(nbrOfTiles <= 0 || i >= 600){ //
        workToDo = false;
      }
    }

  }


  def show() = {
    val nbrSize : Int = 40
    val nbrColor : Color = Color.white


    display.clear()
    getGrid()

    if(mainBoard(0)(0)!=0) display.drawString(nbrCoordinates(mainBoard(0)(0), oneDigitLength1), nbrHeight(mainBoard(0)(0),oneDigitHeight1), s"${mainBoard(0)(0)}", nbrColor, nbrSizeValue(mainBoard(0)(0), nbrSize))
    if(mainBoard(0)(1)!=0) display.drawString(nbrCoordinates(mainBoard(0)(1), oneDigitLength2), nbrHeight(mainBoard(0)(1),oneDigitHeight1), s"${mainBoard(0)(1)}", nbrColor, nbrSizeValue(mainBoard(0)(1), nbrSize))
    if(mainBoard(0)(2)!=0) display.drawString(nbrCoordinates(mainBoard(0)(2), oneDigitLength3), nbrHeight(mainBoard(0)(2),oneDigitHeight1), s"${mainBoard(0)(2)}", nbrColor, nbrSizeValue(mainBoard(0)(2), nbrSize))
    if(mainBoard(0)(3)!=0) display.drawString(nbrCoordinates(mainBoard(0)(3), oneDigitLength4), nbrHeight(mainBoard(0)(3),oneDigitHeight1), s"${mainBoard(0)(3)}", nbrColor, nbrSizeValue(mainBoard(0)(3), nbrSize))

    if(mainBoard(1)(0)!=0) display.drawString(nbrCoordinates(mainBoard(1)(0), oneDigitLength1), nbrHeight(mainBoard(1)(0),oneDigitHeight2), s"${mainBoard(1)(0)}", nbrColor, nbrSizeValue(mainBoard(1)(0), nbrSize))
    if(mainBoard(1)(1)!=0) display.drawString(nbrCoordinates(mainBoard(1)(1), oneDigitLength2), nbrHeight(mainBoard(1)(1),oneDigitHeight2), s"${mainBoard(1)(1)}", nbrColor, nbrSizeValue(mainBoard(1)(1), nbrSize))
    if(mainBoard(1)(2)!=0) display.drawString(nbrCoordinates(mainBoard(1)(2), oneDigitLength3), nbrHeight(mainBoard(1)(2),oneDigitHeight2), s"${mainBoard(1)(2)}", nbrColor, nbrSizeValue(mainBoard(1)(2), nbrSize))
    if(mainBoard(1)(3)!=0) display.drawString(nbrCoordinates(mainBoard(1)(3), oneDigitLength4), nbrHeight(mainBoard(1)(3),oneDigitHeight2), s"${mainBoard(1)(3)}", nbrColor, nbrSizeValue(mainBoard(1)(3), nbrSize))

    if(mainBoard(2)(0)!=0) display.drawString(nbrCoordinates(mainBoard(2)(0), oneDigitLength1), nbrHeight(mainBoard(2)(0),oneDigitHeight3), s"${mainBoard(2)(0)}", nbrColor, nbrSizeValue(mainBoard(2)(0), nbrSize))
    if(mainBoard(2)(1)!=0) display.drawString(nbrCoordinates(mainBoard(2)(1), oneDigitLength2), nbrHeight(mainBoard(2)(1),oneDigitHeight3), s"${mainBoard(2)(1)}", nbrColor, nbrSizeValue(mainBoard(2)(1), nbrSize))
    if(mainBoard(2)(2)!=0) display.drawString(nbrCoordinates(mainBoard(2)(2), oneDigitLength3), nbrHeight(mainBoard(2)(2),oneDigitHeight3), s"${mainBoard(2)(2)}", nbrColor, nbrSizeValue(mainBoard(2)(2), nbrSize))
    if(mainBoard(2)(3)!=0)  display.drawString(nbrCoordinates(mainBoard(2)(3), oneDigitLength4), nbrHeight(mainBoard(2)(3),oneDigitHeight3), s"${mainBoard(2)(3)}", nbrColor, nbrSizeValue(mainBoard(2)(3), nbrSize))

    if(mainBoard(3)(0)!=0) display.drawString(nbrCoordinates(mainBoard(3)(0), oneDigitLength1), nbrHeight(mainBoard(3)(0),oneDigitHeight4), s"${mainBoard(3)(0)}", nbrColor, nbrSizeValue(mainBoard(3)(0), nbrSize))
    if(mainBoard(3)(1)!=0) display.drawString(nbrCoordinates(mainBoard(3)(1), oneDigitLength2), nbrHeight(mainBoard(3)(1),oneDigitHeight4), s"${mainBoard(3)(1)}", nbrColor, nbrSizeValue(mainBoard(3)(1), nbrSize))
    if(mainBoard(3)(2)!=0) display.drawString(nbrCoordinates(mainBoard(3)(2), oneDigitLength3), nbrHeight(mainBoard(3)(2),oneDigitHeight4), s"${mainBoard(3)(2)}", nbrColor, nbrSizeValue(mainBoard(3)(2), nbrSize))
    if(mainBoard(3)(3)!=0) display.drawString(nbrCoordinates(mainBoard(3)(3), oneDigitLength4), nbrHeight(mainBoard(3)(3),oneDigitHeight4), s"${mainBoard(3)(3)}", nbrColor, nbrSizeValue(mainBoard(3)(3), nbrSize))


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
      if (highestValue == 2048) println("WINNER !")
    }

  }
  def looooooooooser(): Boolean = {
    //Initializing the variable nbrFullCases
    var nbrFullCases: Int = 0
    //Navigating the entire ArrayOfDim
    for (i <- mainBoard.indices) {
      for (j <- mainBoard(0).indices) {
        //Counts how many cases are full
        if (mainBoard(i)(j) != 0) nbrFullCases += 1
      }
    }
    //There are 16 cases. If all of them are full, the player lost
    if (nbrFullCases == 16) {
      println("Looooooseeeeeeeeeeeeeeeeeer")
      return true
    } else {
      return false
    }

  }

  def getFreeSpacesPosition(): Array[Array[Int]] = {
    var freeSpaceArray : Array[Array[Int]] = Array.ofDim(getFreeSpacesNumber(), 2)

    var freeSpaceArrayIndices : Int = 0;
    for(x <- mainBoard.indices){
      for(y <- mainBoard(x).indices){
        if (mainBoard(x)(y) == 0){
          freeSpaceArray(freeSpaceArrayIndices)(0) = x
          freeSpaceArray(freeSpaceArrayIndices)(1) = y
          freeSpaceArrayIndices += 1
        }
      }
    }

    return freeSpaceArray
  }

  def getFreeSpacesNumber(): Int = {
    var freeSpace: Int = 0;

    for(x <- mainBoard.indices){
      for(y <- mainBoard(x).indices){
        if (mainBoard(x)(y) == 0){
          freeSpace += 1
        }
      }
    }
    return freeSpace
  }

  def printBoard = {
    println("Board = ______________________")
    for(x <- mainBoard.indices){
      println(mainBoard(x).mkString("/"))
    }
  }

  def isRunning() : Boolean = {
    true
  } //Forcage temporaire de isRunning

  class GridTile(var posX : Int,var posY: Int, var width : Int, var height: Int) {

  }

  def getGrid() : Unit = {
    //Displays the Grid
    display.setColor(Color.BLACK)
    display.drawString(70, 50, "Super Fun 2048", Font.MONOSPACED, Font.BOLD, 30, Color.orange)

    display.setColor(Color.gray)
    display.drawFillRect(55,190, 290, 290)

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

  //if number of older board is in the top of a 0, move 1 by 1 to the direction until it's not in the top anymore

  private def nbrCoordinates(in : Int, oneDigit : Int) : Int = {
    if(in > 1000){
      return oneDigit - 12
    }else if(in > 100){
      return oneDigit - 11
    }else if(in > 10){
      return oneDigit -12
    }else{
      return oneDigit
    }
  }

  private def nbrSizeValue(in : Int, oneDigit : Int) : Int = {
    if(in > 1000){
      return oneDigit - 20
    }else if(in > 100){
      return oneDigit - 14
    }else{
      return oneDigit
    }
  }

  private def nbrHeight(in : Int, oneDigit : Int) : Int = {
    if(in > 1000){
      return oneDigit - 7
    }else if(in > 100){
      return oneDigit - 5
    }else{
      return oneDigit
    }
  }

  private def caseColor(in : Int): Color = {
    in match {
      case 0 => Color.lightGray
      case 2 => Color.red
      case 4 => Color.orange
      case 8 => Color.cyan
      case 16 => Color.blue
      case 32 => Color.magenta
      case 64 => Color.pink
      case 128 => Color.red
      case 256 => Color.green
      case 512 => Color.cyan
      case 1024 => Color.yellow
      case 2048 => Color.orange
    }
  }

  class TileToMove(
    var xStartId: Int,
    var yStartId: Int,
    var xEndId: Int,
    var yEndId: Int,
    var posX: Int,
    var posY: Int,
    var xMoovement: Int,
    var yMoovement: Int,
    var color: java.awt.Color,
    var active: Boolean) {

    override def toString:String = {
      return(s" xStartId = $xStartId | yStart = $yStartId || xEndId = $xEndId | yEndId = yEndId || xmov = $xMoovement | ymov = $yMoovement")
    }
  }
}