import javax.swing.{JFrame, JOptionPane, JPasswordField}

object Dialogs {
  var noAnswer: Boolean = false

  def getHiddenString(message: String): String = {
    val passwordField = new JPasswordField(10)
    val frame = new JFrame(message)
    // prompt the user to enter their name
    JOptionPane.showMessageDialog(frame, passwordField, message, JOptionPane.PLAIN_MESSAGE)
    val s = new String(passwordField.getPassword)
    if (s.length > 0) s
    else getHiddenString("Enter at least one character")
  }

  def getString(message: String): String = {
    noAnswer = false
    val frame = new JFrame(message)
    // prompt the user to enter their name
    try {
      val s = JOptionPane.showInputDialog(frame, message)
      if (s.length > 0) s
      else getString("Enter at least one character")
    } catch {
      case e: Exception =>
        noAnswer = true
        Start.mainMenu.run()
        ""
    }
  }

  def getChar(message: String): Char = {
    val frame = new JFrame("Input a character please")
    val s = JOptionPane.showInputDialog(frame, message)
    if (s == null) System.exit(-1)
    if (s.length == 1) s.charAt(0)
    else getChar("Just one character")
  }

  def displayMessage(message: String): Unit = {
    JOptionPane.showMessageDialog(null, message, null, JOptionPane.PLAIN_MESSAGE)
  }
}
