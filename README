# Deadwood
### Implemented by Jamal Marri and Megan Theimer

## Steps to compile:

### Option A:
1. Simply compile yourself using 'mkdir -p out;javac -d out/ src/*' at root of repository.
2. Run using 'java -cp out/ Deadwood' at root of repository.

### Option B:
1. Run the "make" script (./make) or "make.bat" batch file depending on your operating system at the root of the repository.
2. The program will compile the game, run it, and then delete the class files it just generated.

## Playing the game:

### Choosing the number of players
Upon first starting the game, you will be prompted to enter a number of players. This number must be between
2 and 8, as the game can only support 2-8 people.

### The first turn
After entering a valid number of players, the game will immediately start with Player 1's prompt. Every player
will see this prompt when it is their turn. Info contained in this prompt will help you understand your current
position, status, and the amount of money and credits you currently have. Note: all input in the game is
case-sensitive.

### Actions
The following are the possible actions one can take in their turn. Some actions depend on you being on a
particular room, or type of room, on the board to be usuable.

#### list
This command will list all of the players in the game, and the room that they are currently positioned in. You
can list all players at any point during your turn and as many times as you'd like.

#### move
This command will allow you to move to an adjacent space, only if you are not currently acting in a movie.
You will be prompted to enter a room name from the list of adjacent rooms. You can only move once per turn,
and moving will lock you out from any other actions until your next turn. However, if you move to a set with
an unfinished movie, the game will give you the opportunity to take a part in that set if you can and would
like to.

#### take
This command will allow you to take a part, if you are in a set which has an unfinished movie, are not already
acting, and if there is at least one part you can possibly take. You will be prompted to enter a part's name
from a list of all 'on the card' and 'off the card' parts this set currently has. You may only take a part
once per turn, and taking a part will lock you out from any other actions until your next turn.

#### rehearse
Once you're in a movie, you will have the opportunity to rehearse. Rehearsing requires no other input and
will simply add to the number of times you've rehearsed during this movie, which subracts from the amount
you need to roll in order to successfully act. You may only rehearse once per turn, and rehearsing will
lock you out from any other actions until your next turn.

#### act
Once you feel confident that you can roll a number equal to or greater than the current movie's budget minus
the number of times you've rehearsed, you can try acting. Acting will roll a random number between 1 and 6.
If the number rolled is equal to or exceeds the target number, you will successfully complete one take, and
be rewarded either 1 credit and 1 dollar, or 2 credits, depending on whether or not you were playing 'on the
card'. Additionally, if at least one player was playing 'on the card' during this movie, x number of dice will
be rolled, where x is the movie budget. The numbers from this dice roll will be distributed among the 'on the
card' players as dollars acting in this movie, in order from greatest to least roll/level, wrapping around the
players as necessary. 'Off the card' players will recieve x dollars, where x is the level of their role. You
may only act once per turn, and acting will lock you out from any other actions until your next turn.

#### upgrade
If you happen to come across the 'Casting Office' room, you can use the upgrade command while on it. This will
allow you to spend your dollars or credits to upgrade your rank, allowing for more possible parts, and adding
significantly to your score at the end of the game. You will be prompted to enter a new rank greater than your
current one. If you enter your current rank, the upgrade will be canceled, and you can take a different action.
If you can afford the higher rank you chose with at least one type of currency, you will be prompted to choose
which currency you would like to use. After choosing, the appropriate amount of the chosen currency will be
subtracted from the current amount you have, and your new rank will be awarded. You may only upgrade once per
turn, and upgrading will lock you out from any other actions until your next turn.

#### end
This command will end your current turn, and start the next player's turn. You may use this command before
performing any action, if you would like to be skipped.
