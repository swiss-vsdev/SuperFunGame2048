class Board {
  var mainBoard : Array[Array[Int]] = Array.ofDim(4,4)
  var scoreValue : Int = 0

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
    printBoard
  }

  def moveTiles(direction : Int) = {
    // 0 = bas
    // 1 = gauche
    // 2 = haut
    // 3 = droite
    for (i <- mainBoard.indices){
      for (j <- mainBoard(0).indices){

      }
    }

  }

  def score(): Int = {
      scoreValue
  }

  def show() = {

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
}