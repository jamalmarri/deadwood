import java.util.Arrays;

/**
 * Represents the game Deadwood, and a
 * fully functioning implementation of it.
 *
 * @author Jamal Marri
 * @author Megan Theimer
 * @version 1.0
 * @see Board
 * @see Screen
 * @see Player
 */
public class Deadwood {
    private static final Board board = new Board();
    private static final Screen screen = new Screen(board);
    private static Player[] players;
    private static int scenesLeft;
    private static int daysLeft;
    private static boolean gameIsRunning;

    /**
     * Main loop of the game, which controls it
     * at the highest level.
     *
     * @param args the command-line arguments passed to the game.
     */
    public static void main(String[] args) {
        init();
        while (gameIsRunning) {
            // For every player, tick the game another turn
            for (int i = 0; i < players.length; i++) {
                tick(players[i], i + 1);
                if (scenesLeft < 2) {
                    daysLeft--;
                    if (daysLeft < 1) {
                        gameIsRunning = false;
                        break;
                    }
                    resetDay();
                }
            }
        }
        tallyScores();
    }

    /**
     * Starts the game by asking the user for the
     * number of Players, and then initializes all
     * of the Player objects and game variables.
     */
    private static void init() {
        // Get number of players from user input
        int playerCount = screen.getPlayerCount();

        // Initialize array of playerCount players
        players = new Player[playerCount];

        // Establish how many days will pass based on amount of players
        if (players.length <= 3) {
            daysLeft = 3;
        } else {
            daysLeft = 4;
        }

        // Initialize player objects based on amount of players
        for (int i = 0; i < players.length; i++) {
            switch (players.length) {
                case 5:
                    players[i] = new Player(2, 1);
                    break;
                case 6:
                    players[i] = new Player(4, 1);
                    break;
                case 7:
                case 8:
                    players[i] = new Player(0, 2);
                    break;
                default:
                    players[i] = new Player();
                    break;
            }
        }

        // First-time reset to position the players and deal out cards to all sets
        resetDay();
        // This boolean controls the main loop of the game
        gameIsRunning = true;
    }

    /**
     * Ticks one Player's turn by asking for their next action,
     * executing the appropriate method, and outputting the
     * response to the action attempt.
     * This tick will not end until a move returns a non-negative
     * integer.
     *
     * @param player       the Player whose turn is being ticked.
     * @param playerNumber the number representing the Player.
     */
    private static void tick(Player player, int playerNumber) {
        player.setWaitingForAction(true);
        int inputCode;
        // Output code is initialized to a negative value, so that
        // the game will not proceed until a valid action is made
        int outputType;
        while (true) {
            // Write the player's information and then prompt them
            // to enter their action this turn
            if (player.isWaitingForAction()) {
                screen.writePlayerInfo(player, playerNumber);
            }
            inputCode = screen.getPlayerAction(player);

            // Execute the method associated with the chosen action,
            // and then print the response to that attempt.
            switch (inputCode) {
                case 0:
                    screen.listPlayers(players);
                    break;
                case 1:
                    outputType = move(player);
                    screen.writeResponse(inputCode, outputType);
                    // If the player isn't already acting and moves to a set
                    // which has an active movie, ask the player if they want
                    // to try and take a part there
                    if (player.getCurrentRoom() instanceof Set && !player.isActing() && ((Set) player.getCurrentRoom()).getTakesLeft() > 0 && screen.attemptPart(player)) {
                        screen.writeResponse(2, takePart(player));
                    }
                    if (outputType > -1)
                    {
                        player.setWaitingForAction(false);
                    }
                    break;
                case 2:
                    outputType = takePart(player);
                    screen.writeResponse(inputCode, outputType);
                    if (outputType > -1)
                    {
                        player.setWaitingForAction(false);
                    }
                    break;
                case 3:
                    outputType = rehearse(player);
                    screen.writeResponse(inputCode, outputType);
                    if (outputType > -1)
                    {
                        player.setWaitingForAction(false);
                    }
                    break;
                case 4:
                    outputType = act(player);
                    screen.writeResponse(inputCode, outputType);
                    // If the player just successfully acted their part on
                    // a set and finished the movie, execute a check for a
                    // bonus payout and write the response to that attempt
                    if (player.getCurrentRoom() instanceof Set && ((Set) player.getCurrentRoom()).getTakesLeft() < 1 && player.isActing()) {
                        scenesLeft--;
                        screen.writeResponse(7, checkForBonusMoney(player));
                    }
                    if (outputType > -1)
                    {
                        player.setWaitingForAction(false);
                    }
                    break;
                case 5:
                    outputType = upgrade(player);
                    screen.writeResponse(inputCode, outputType);
                    if (outputType > -1)
                    {
                        player.setWaitingForAction(false);
                    }
                    break;
                case 6:
                    screen.writeResponse(inputCode, 0);
                    return;
                default:
                    break;
            }
        }
    }

    /**
     * Attempts to move the given Player to a new Room
     * adjacent to the room they already reside in.
     *
     * @param player the Player that will move.
     * @return an integer representing the outcome of this action.
     */
    private static int move(Player player) {
        if (player.isActing()) {
            return -1;
        } else {
            player.getCurrentRoom().removePlayer(player);
            player.setCurrentRoom(screen.getRoom(player));
            player.getCurrentRoom().addPlayer(player);
            return 0;
        }
    }

    /**
     * Attempts to give a Part to the given Player,
     * either from the Set that they are on, or the
     * Card on that Set.
     *
     * @param player the Player that will take a Part.
     * @return an integer representing the outcome of this action.
     */
    private static int takePart(Player player) {
        if (!(player.getCurrentRoom() instanceof Set)) {
            return -1;
        }

        boolean partPossible = false;
        // Check if any part on the set is able to be taken
        for (Part part : ((Set) player.getCurrentRoom()).getParts()) {
            if (part.getLevel() <= player.getRank() && part.getPlayerOnPart() == null) {
                partPossible = true;
                break;
            }
        }
        // Check if any part on the set's card is able to be taken
        for (Part part : ((Set) player.getCurrentRoom()).getCard().getParts()) {
            if (part.getLevel() <= player.getRank() && part.getPlayerOnPart() == null) {
                partPossible = true;
                break;
            }
        }

        if (player.isActing()) {
            return -2;
        } else if (((Set) player.getCurrentRoom()).getTakesLeft() < 1) {
            return -3;
        } else if (!partPossible) {
            return -4;
        } else {
            // Attach player to part and vice versa
            player.setCurrentPart(screen.getPart(player));
            player.getCurrentPart().setPlayerOnPart(player);
            // Set isActing to true and isOnCard to whether or not their part exists on the set's card
            player.setActing(true);
            player.setOnCard(((Set) player.getCurrentRoom()).getCard().getParts().contains(player.getCurrentPart()));
            return 0;
        }
    }

    /**
     * Attempts a rehearsal for the given Player, to
     * provide better chances of acting successfully.
     *
     * @param player the Player that will rehearse.
     * @return an integer representing the outcome of this action.
     */
    private static int rehearse(Player player) {
        if (!player.isActing()) {
            return -1;
        }

        Set currentSet = (Set) player.getCurrentRoom();
        int budget = currentSet.getCard().getBudget();

        // Rehearsing is unnecessary when acting success is already guaranteed
        if ((budget - player.getTimesRehearsedThisScene()) < 2) {
            return -2;
        }

        player.setTimesRehearsedThisScene(player.getTimesRehearsedThisScene() + 1);
        return 0;
    }

    /**
     * Attempts an acting action for the given Player,
     * to earn currency and make progress towards their
     * Part.
     *
     * @param player the Player that will act.
     * @return an integer representing the outcome of this action.
     */
    private static int act(Player player) {
        if (!player.isActing()) {
            return -1;
        }

        Set currentSet = (Set) player.getCurrentRoom();

        // Find the target amount that must be rolled and then roll the dice
        int budget = currentSet.getCard().getBudget();
        int target = budget - player.getTimesRehearsedThisScene();
        int roll = Dice.roll(1)[0];

        // If acting was a success give the appropriate amount of some currency
        if (roll >= target) {
            currentSet.setTakesLeft(currentSet.getTakesLeft() - 1);
            if (player.isOnCard()) {
                player.setCredits(2 + player.getCredits());
                return 0;
            } else {
                player.setDollars(1 + player.getDollars());
                player.setCredits(1 + player.getCredits());
                return 1;
            }
            // If acting was a failure, give currency to extras, and do nothing for stars
        } else {
            if (!player.isOnCard()) {
                player.setDollars(1 + player.getDollars());
                return 2;
            } else {
                return 3;
            }
        }
    }

    /**
     * Checks if a bonus payout applies to a given Player's Set,
     * and then applies an amount of dollars to all actors depending
     * on their Part in the movie.
     * This is always executed after a Player completes a movie on
     * their current Set.
     *
     * @param player the Player whose Set will be checked for a bonus payout.
     * @return an integer representing the outcome of this payout.
     */
    private static int checkForBonusMoney(Player player) {
        // Check if a bonus applies
        boolean hasStarPlayer = false;
        for (Part part : ((Set) player.getCurrentRoom()).getCard().getParts()) {
            if (part.getPlayerOnPart() != null) {
                hasStarPlayer = true;
                break;
            }
        }
        if (!hasStarPlayer) {
            // Detach players from parts and vice versa
            for (Player playerOnSet : player.getCurrentRoom().getPlayers()) {
                playerOnSet.setActing(false);
                playerOnSet.setOnCard(false);
                playerOnSet.setTimesRehearsedThisScene(0);
                player.getCurrentPart().setPlayerOnPart(null);
                playerOnSet.setCurrentPart(null);
            }
            return -1;
        }

        // Generate array of bonus dollars
        int[] bonus = Dice.roll(((Set) player.getCurrentRoom()).getCard().getBudget());
        Arrays.sort(bonus);

        // Generate array of star players ordered by their part's level
        Player[] orderedStars = new Player[6];
        for (Part part : ((Set) player.getCurrentRoom()).getCard().getParts()) {
            if (part.getPlayerOnPart() != null) {
                orderedStars[part.getLevel() - 1] = part.getPlayerOnPart();
            }
        }

        // Apply dollars to stars in reverse order, wrapping around as necessary
        int playerPointer = orderedStars.length - 1;
        for (int i = bonus.length - 1; i >= 0; ) {
            if (orderedStars[playerPointer] != null) {
                orderedStars[playerPointer].setDollars(orderedStars[playerPointer].getDollars() + bonus[i]);
                i--;
            }
            playerPointer--;
            if (playerPointer < 0) {
                playerPointer = orderedStars.length - 1;
            }
        }

        // Apply dollars to extras based on their part's level
        for (Part part : ((Set) player.getCurrentRoom()).getParts()) {
            if (part.getPlayerOnPart() != null) {
                part.getPlayerOnPart().setDollars(player.getDollars() + player.getCurrentPart().getLevel());
            }
        }

        // Detach players from parts and vice versa
        for (Player playerOnSet : player.getCurrentRoom().getPlayers()) {
            playerOnSet.setActing(false);
            playerOnSet.setOnCard(false);
            playerOnSet.setTimesRehearsedThisScene(0);
            player.getCurrentPart().setPlayerOnPart(null);
            playerOnSet.setCurrentPart(null);
        }

        return 0;
    }

    /**
     * Attempts to upgrade the given Player's rank based on the
     * amount of currency they have, and the rank they already are.
     *
     * @param player the Player that will have their rank upgraded.
     * @return an integer representing the outcome of this action.
     */
    private static int upgrade(Player player) {
        if (player.getCurrentRoom() != board.getCastingOffice()) {
            return -1;
        }

        // Get a valid rank greater than or equal to the player's current rank that they can afford
        int newRank = screen.getRank(player);

        // This occurs when the player cancels the upgrade by entering their current rank
        if (newRank < 1) {
            return -2;
        }

        // Get the currency the player wishes to use to purchase their new rank
        String currency = screen.getCurrency(player, newRank);

        // Remove the appropriate amount of the chosen currency from the player
        switch (currency) {
            case "credits":
                player.setCredits(player.getCredits() - board.getUpgradeCreditPrices()[newRank - 2]);
                break;
            case "dollars":
                player.setDollars(player.getDollars() - board.getUpgradeDollarPrices()[newRank - 2]);
                break;
            default:
                break;
        }

        player.setRank(newRank);
        return 0;
    }

    /**
     * Resets all Player's positions to the 'Trailers' Room,
     * deals out new Cards to every Set, and resets the number
     * of scenes left for the day.
     */
    private static void resetDay() {
        // Reset player positions and statuses
        for (Player player : players) {
            player.setActing(false);
            player.setOnCard(false);
            player.setTimesRehearsedThisScene(0);
            player.getCurrentPart().setPlayerOnPart(null);
            player.setCurrentPart(null);
            player.setCurrentRoom(board.getTrailers());
        }

        // Deal a new card to all sets
        for (Room room : board.getRooms().values()) {
            if (room instanceof Set) {
                ((Set) room).setTakesLeft(((Set) room).getDefaultTakes());
                ((Set) room).setCard(board.getDeck().get(board.getDeck().size() - 1));
                board.getDeck().remove(board.getDeck().size() - 1);
            }
        }

        // Reset the number of scenes left for the day
        scenesLeft = 10;

        // Output a message regarding this reset, if it isn't the first one
        if (gameIsRunning) {
            screen.writeResetMessage(daysLeft);
        }
    }

    /**
     * Gets the scores from every Player, stores them in
     * an integer array, and then outputs them along with
     * an end of game message.
     * This is always executed at the end of the game,
     * when there are no days left.
     */
    private static void tallyScores() {
        // Store player scores in an integer array
        int[] scores = new int[players.length];
        for (int i = 0; i < players.length; i++) {
            scores[i] = players[i].getScore();
        }

        // Output the scores along with an end of game message
        screen.writeScores(scores);
    }
}
