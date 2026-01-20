
# Super Fun Game 2048 #
By Gabriel Zeizer and Aurélien Santi

## Description & *How To Play*
The game is a basic 2048 game
You can play the game with arrows W A S D :
Each one of these keys will throw the tiles to the equivalent direction
W will send them to the top of the board
A will send to the left
S Down
D Right

When the numbers are throwed to the border, if 2 équivalent numbers touch each other they will merge together

The goal it to make appear the 2048 number on your board.

It's not that easy and it requires a bit of strategy.
The biggest number you can do is 131072 but don't worry : You will not :) !
You think you can ? Prove it !

## After Git Clone (IntelliJ Setup)

1. Right-click `src` folder → **Mark Directory as** → **Sources Root** (turns blue)
2. **Add FunGraphics as Library**:
    - Right-click on `fun-Graphics-1.5.20.jar` → **Add as Library...** → **OK** (don't change anything, just click OK)
3. Open the object `Start` in the `src` folder → **Setup Scala SDK** (Top right corner) → (Select scala 2.13.16 in the dropdown menu) → **OK**
4. Stay in `Start` → **Run**

## Dependencies
- Scala 2.13.16
- Java 17
- FunGraphics 1.5.20


## Screenshots : 

![alt text](https://github.com/swiss-vsdev/SuperFunGame2048/blob/master/screenshots/Gimme%20your%20name%20my%20good%20sir.png?raw=true)
Game asking for a username before starting to enable the recording of your score

![alt text](https://github.com//swiss-vsdev/SuperFunGame2048/blob/master/screenshots/a%20boring%20screenshot.png?raw=true)
Image of the game while it's playing

![alt text](https://github.com/swiss-vsdev/SuperFunGame2048/blob/master/screenshots/Why%20is%20Gab%20everywhere.png?raw=true)
Game Leaderboard where you can see if you are better than me

## Detailled instructions for use

To start a game you have to first open the game into IntelliJ. You can then start the main Object **Start**.

You can navigate through the menus and play the game using the **W A S D** keys and the space bar to select.

- To start playing

  - Select "PLAY" in the menu (using the space bar)
  - Give a new username in the window and press "OK"
  - ENJOY ! (Play with W A S D)
  
- To check the leaderboard

  - Go back to the menu by hitting the "ESC" key
  - Select "Leaderboard" in the menu (using the space bar)
  - Check it out !
  
- To exit out of the game

  - Go back to the menu by hitting the "ESC" key
  - Select "Quit" in the menu (using the space bar)
  - See you soon !

**You can go back to the main menu at anytime by hitting the "ESC" key**

## Code Structure : (Only main class, object and methods)

The main Object is **Start**
- The key Listener is there
  - It catch the directions and the Esc key
- It open the Main menu

The MainMenu Class show and manage the MainMenu
- From here it opens the LeaderBoard or the Game

The Playing Class is where the game loops each anytime

The Board Class is the main class of the game
- It manage all the game Logic
- It Print the game

- looooooooooser() method is triggered when the player loose
- winner() is triggered when the player reach 2048 (but after that he can continue to play for more)
- show() displays the game on the playing windows
- moveTiles() trigger the movement for the whole board depending on the direction
- canMove() Verify the directions on which the player can go
- addNewTile() Each loop, add a 2 or a 4 randomly on a free tile

The leaderboard shows the 5 best scores written in the score.txt file (added at each game)