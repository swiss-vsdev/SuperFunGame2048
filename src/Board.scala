class Board {
  var mainBoard : Array[Array[Int]] = Array.ofDim(4,4)

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

    if( freeSN <= 0){ // Vérifier si c'est possible de faire un moove, dans ce cas pas de looser mais pas de score+ non plus
      //looooooooooser() // Logique de loose ne devrait pas être ici
    } else {
      var x = getFreeSpacesPosition()(rand2)(0)
      var y = getFreeSpacesPosition()(rand2)(1)

      mainBoard(x)(y) = numToAdd
    }
    printBoard
  }

  def mooveTiles(direction : Int) = {
    // 0 = bas
    // 1 = gauche
    // 2 = haut
    // 3 = droite

  }

  def score()= {

  }

  def show() = {

  }

  def winner() ={

  }

  def looooooooooser() ={
    print("Looooooseeeeeeeeeeeeeeeeeer")
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
}