
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


## Screen Shots : 
Please look inside of /screenshots folder
Thx to you


## Code Structure : //Only main class, object and methods

The main Object is Start
- The key Listener is there
- - It catch the directions and the Esc key
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

The leaderBoard affiche les 5 meilleurs score inscrits dans le fichier leaderboard (ajoutés à chaque jeu)