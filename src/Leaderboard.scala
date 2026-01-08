import hevs.graphics.FunGraphics
import java.awt.{Color, Font}
import java.io.{BufferedReader, FileNotFoundException, FileReader}

class Leaderboard (val display : FunGraphics){
  var lines : Array[String] = Array.empty

  def run() : Unit = {

    display.clear()
    display.drawString (100, 50, "Leaderboard", Font.MONOSPACED, Font.BOLD, 30, Color.orange)
    readfile()


  }

  def readfile() : Unit = {
    val fileName: String = "score.txt"

    try {
      val fr = new FileReader(fileName)
      val inputReader = new BufferedReader(fr)
      lines = new Array[String](inputReader.lines().count().toInt)

      var line = inputReader.readLine()
      println(line)

      line = inputReader.readLine()
      println(line)

      inputReader.close()
    } catch {
      case e: FileNotFoundException =>
        println("File not found !")
        e.printStackTrace()
      case e: Exception =>
        println(s"Something bad happened during read ${e.getMessage()}")
    }
  }

  def writefile(username : String, score : Int) : Unit = {

  }
}
