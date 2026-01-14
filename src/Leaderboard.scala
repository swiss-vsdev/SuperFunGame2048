import Start.isOn
import hevs.graphics.FunGraphics
import hevs.graphics.utils.GraphicsBitmap
import java.awt.{Color, Font}
import java.io.{BufferedReader, File, FileNotFoundException, FileOutputStream, FileReader, PrintWriter}
import javax.sound.sampled.AudioSystem


class Leaderboard(val display: FunGraphics) {

  def run(): Unit = {
    display.clear()
    display.drawString(100, 50, "Leaderboard", Font.MONOSPACED, Font.BOLD, 30, Color.orange)
    val scores: Array[String] = readfile()
    var outString: String = ""
    val firstLine: String = "USERNAME\t\t\t\t\t\t\t\tSCORE"
    var user1: String = ""
    var score1: String = "0"
    var user2: String = ""
    var score2: String = "0"
    var user3: String = ""
    var score3: String = "0"
    var user4: String = ""
    var score4: String = "0"
    var user5: String = ""
    var score5: String = "0"
    var nbrCnt: Int = 1


    //Draw the scores on the window
    for (j <- 0 to 1) {
      for (i <- scores.indices) {
        if (i % 2 != 0) {
          val username: String = scores(i - 1)
          val score: String = scores(i)
          println(s"User : $username - Score : $score")

          if (score.toInt >= score1.toInt) {
            user1 = username
            score1 = score
          } else if (score.toInt >= score2.toInt) {
            user2 = username
            score2 = score
          } else if (score.toInt >= score3.toInt) {
            user3 = username
            score3 = score
          } else if (score.toInt >= score4.toInt) {
            user4 = username
            score4 = score
          } else if (score.toInt >= score5.toInt) {
            user5 = username
            score5 = score
          }
          outString = s"$user1;$score1;$user2;$score2;$user3;$score3;$user4;$score4;$user5;$score5"
        }
      }
    }

    display.drawString(60, 100, firstLine, Font.MONOSPACED, Font.BOLD, 20, Color.black)
    //println(outString)

    val outArray: Array[String] = outString.split(";")
    for (i <- outArray.indices) {
      if (i % 2 == 0) {
        display.drawString(25, (140 + (i * 20)), s"${nbrCnt}. ${outArray(i)}", Font.MONOSPACED, Font.BOLD, 20, Color.black)
        nbrCnt += 1
      }
      if (i % 2 != 0) {
        if (outArray(i) != "0") display.drawString(270, (120 + (i * 20)), s"${outArray(i)}", Font.MONOSPACED, Font.BOLD, 20, Color.black)
      }
    }
    nbrCnt = 1
    val gb = new GraphicsBitmap("/Assets/bonus.jpg")

    val musicfile = new File("./src/Assets/epicmusic.wav")
    val clip = AudioSystem.getClip()
    val audio = AudioSystem.getAudioInputStream(musicfile)
    clip.open(audio)
    clip.start()

    //Should I comment this ?? :| or should I let my mate comment it ? :p
    while (isOn) {
      if(isOn) {
        for (j <- 0 to 364 by 74) {
          if (isOn) {
            for (i <- 0 to 364) {
              display.drawPicture((i - j), 560, gb)
              Thread.sleep(2)
            }
          }
        }
      }
      if(isOn) Thread.sleep(500)
    }

    clip.stop()

  }
  def readfile(): Array[String] = {
    // Read the leaderboard file on the disk
    val fileName: String = "src/score.txt"

    try {
      val fr = new FileReader(fileName)
      val inputReader = new BufferedReader(fr)

      var line: String = ""

      while (inputReader.ready() && isOn) {
        line += inputReader.readLine()
      }

      inputReader.close()
      line.split(";")

    } catch {
      case e: FileNotFoundException =>
        println("File not found !")
        e.printStackTrace()
        val out = new Array[String](0)
        out
      case e: Exception =>
        println(s"Something bad happened during read ${e.getMessage()}")
        val out = new Array[String](0)
        out
    }
  }

  def writefile(username: String, score: Int): Unit = {
    // Write the leaderboard file on the user drive
    try {

      val fs = new FileOutputStream("src/score.txt", true)
      val pw = new PrintWriter(fs)

      pw.println(s"$username;$score;")
      pw.close()

    } catch {
      case e: Exception =>
        println("File can't be written")
        e.printStackTrace()
    }

    println("Writing done")

  }
}
