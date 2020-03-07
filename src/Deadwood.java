import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Arrays;

/** Represents the game of Deadwood, including Players, a Board, Dice, and a Screen. */
public class Deadwood {

    /** The extension for all images read by this game. */
    private static final String ext = ".png";

    /** The path to the folder containing all six sides of the white dice. */
    private static final String dicePath = "img/dice/white/";

    /** The path to the image used to represent a successful take. */
    private static final String takePath = "img/shot" + ext;

    /** The width and height of the image used to represent a successful take. */
    private static final int takeSize = 47;

    /** The Screen used to display the status of this game. */
    private static Screen screen = new Screen();

    /** The Board that contains all of the Rooms and Cards in this game. */
    private static Board board = Board.getInstance();

    /** The Dice that is rolled by this game. */
    private static Dice dice = Dice.getInstance();

    /** The Players in this game. */
    private static Player[] players;

    /** The number of the currently active Player in this game. */
    private static int currentPlayer = -1;

    /** The number of scenes left for the current day. */
    private static int scenesLeft;

    /** The total number of days in this game. */
    private static int totalDays;

    /** The number of the current day. */
    private static int currentDay;

    /** The Pane of all Card images being displayed on the Screen. */
    @FXML private Pane cardsPane;

    /** The Pane of all take images being displayed on the Screen. */
    @FXML private Pane takesPane;

    /** The Pane of all Player dice images being displayed on the Screen. */
    @FXML private Pane playersPane;

    /** The Label which displays the currently active Player. */
    @FXML private Label playerLabel;

    /** The Label which displays the amount of dollars the currently active Player has. */
    @FXML private Label dollarsLabel;

    /** The Label which displays the amount of credits the currently active Player has. */
    @FXML private Label creditsLabel;

    /**
     * The Label which displays the amount of times the currently active Player has rehearsed for
     * their current Part.
     */
    @FXML private Label rehearsalsLabel;

    /**
     * The MenuButton which provides MenuItems representing different Rooms to the Player, that they
     * can move to, if they can move during the current turn.
     */
    @FXML private MenuButton moveMenu;

    /**
     * The MenuButton which provides MenuItems representing different Parts to the Player, that they
     * can take, if they can take a Part during the current turn.
     */
    @FXML private MenuButton takeMenu;

    /**
     * The Button which allows Players to rehearse for their Part, if they have one and can still
     * rehearse for it.
     */
    @FXML private Button rehearseButton;

    /** The Button which allows Players to act their Part, if they have one. */
    @FXML private Button actButton;

    /**
     * The MenuButton which provides MenuItems representing different ranks, which the Player can
     * use to upgrade their current rank, if they have the dollars or credits, and if they are on
     * the Casting Office Room.
     */
    @FXML private MenuButton upgradeMenu;

    /**
     * The Button which allows Players to end their turn at any time, whether they have made an
     * action or not.
     */
    @FXML private Button endButton;

    /**
     * The ImageView containing the Image that represents the first dice of six that Players will
     * roll throughout the course of the game.
     */
    @FXML private ImageView dice1;

    /**
     * The ImageView containing the Image that represents the second dice of six that Players will
     * roll throughout the course of the game.
     */
    @FXML private ImageView dice2;

    /**
     * The ImageView containing the Image that represents the third dice of six that Players will
     * roll throughout the course of the game.
     */
    @FXML private ImageView dice3;

    /**
     * The ImageView containing the Image that represents the fourth dice of six that Players will
     * roll throughout the course of the game.
     */
    @FXML private ImageView dice4;

    /**
     * The ImageView containing the Image that represents the fifth dice of six that Players will
     * roll throughout the course of the game.
     */
    @FXML private ImageView dice5;

    /**
     * The ImageView containing the Image that represents the sixth dice of six that Players will
     * roll throughout the course of the game.
     */
    @FXML private ImageView dice6;

    /** The Label which displays the current day. */
    @FXML private Label dayLabel;

    /**
     * The entry point of the application, which prepares the JavaFX environment and toolkit by
     * instantiating JFXPanel before executing the Screen's start method in the JavaFX Application
     * Thread.
     *
     * @param args the command-line arguments passed to the application
     */
    public static void main(String[] args) {
        // Prepare JavaFX environment and toolkit
        new JFXPanel();
        // Execute screen's start method in the JavaFX Application Thread
        Platform.runLater(() -> screen.start(new Stage()));
    }

    /**
     * Initializes the array of Players with size playerCount, then executes Screen's switchScene
     * method, which switches the visible Scene from the menu Scene to the game Scene.
     *
     * @param playerCount the amount of Players in the game
     */
    static void initPlayers(int playerCount) {
        players = new Player[playerCount];
        screen.switchScene();
    }

    /**
     * Initializes totalDays and all Players in the array of Players, using the length of the array
     * to determine their values. This method is called by the FXMLLoader after all FXML fields have
     * been initialized.
     */
    @FXML
    public void initialize() {
        // Determine number of days using number of players
        if (players.length <= 3) {
            totalDays = 3;
        } else {
            totalDays = 4;
        }
        currentDay = 1;
        // Determine starting rank and credits for each player using number of players
        for (int i = 0; i < players.length; i++) {
            switch (players.length) {
                case 5:
                    players[i] = new Player(i, 2, 1);
                    break;
                case 6:
                    players[i] = new Player(i, 4, 1);
                    break;
                case 7:
                case 8:
                    players[i] = new Player(i, 0, 2);
                    break;
                default:
                    players[i] = new Player(i);
                    break;
            }
            // Declare and initialize ImageView for player dice
            ImageView diceView =
                    new ImageView(players[i].getPathToDice() + players[i].getRank() + ext);
            // Add player dice ImageView to playersPane
            playersPane.getChildren().add(i, diceView);
        }
        resetDay();
    }

    /**
     * Resets the day by moving all Players to the 'Trailers' Room, resetting their acting status,
     * and then resetting the cardsPane and takesPane.
     */
    private void resetDay() {
        for (int i = 0; i < players.length; i++) {
            // Remove the player from their room, if the game has started
            if (currentPlayer > -1) {
                players[i].getCurrentRoom().removePlayer(players[i]);
            }
            // Set the player's current room to be the 'Trailers' room
            players[i].setCurrentRoom(board.getTrailers());
            players[i].getCurrentRoom().addPlayer(players[i]);
            // If the player was acting, reset their acting status
            if (players[i].isActing()) {
                players[i].setActing(false);
                players[i].setOnCard(false);
                players[i].setTimesRehearsedThisScene(0);
                players[i].getCurrentPart().setPlayerOnPart(null);
                players[i].setCurrentPart(null);
            }
            // Reset the player's action status
            players[i].setMoved(false);
            players[i].setWaitingForAction(true);
            // Reset the player's dice's position and size
            ImageView diceView = (ImageView) playersPane.getChildren().get(i);
            diceView.setFitWidth(20);
            diceView.setFitHeight(20);
            diceView.setX(i * 20 + players[i].getCurrentRoom().getX());
            diceView.setY(players[i].getCurrentRoom().getY());
        }
        // Clear takesPane and cardsPane
        takesPane.getChildren().clear();
        cardsPane.getChildren().clear();
        // Initialize a new temporary ImageView for every card that will be added to cardsPane
        for (int i = 0; i < 10; i++) {
            cardsPane.getChildren().add(new ImageView());
        }
        // For every room that is a set, reset its card and takes left
        for (Room room : board.getRooms().values()) {
            if (room instanceof Set) {
                Set set = (Set) room;
                // Reset takes left
                set.setTakesLeft(set.getDefaultTakes());
                // Pull a new card from the deck and set it on the set
                Card card = board.getDeck().remove(board.getDeck().size() - 1);
                set.setCard(card);
                // Declare and initialize new ImageView for the new card
                ImageView cardView = new ImageView("img/cardback" + ext);
                // Set ImageView's size using card's cardX and cardY
                cardView.setX(set.getCardX());
                cardView.setY(set.getCardY());
                // Add the ImageView to cardsPane using set's ID as the index
                cardsPane.getChildren().set(set.getId(), cardView);
            }
        }
        // Reset number of scenes left
        scenesLeft = 10;
        // If the game has not started yet, increment the current player to the first player
        if (currentPlayer < 0) {
            endTurn();
        }
    }

    /**
     * Detaches all Players who were acting on the Set that player is in from their current Parts
     * and vice-versa, and resets their acting status.
     *
     * @param player the Player whose current Set will have all of its Players' acting statuses
     *     reset
     */
    private void detachParts(Player player) {
        for (Player playerOnSet : player.getCurrentRoom().getPlayers()) {
            if (playerOnSet.isActing()) {
                playerOnSet.setActing(false);
                playerOnSet.setOnCard(false);
                playerOnSet.setTimesRehearsedThisScene(0);
                playerOnSet.getCurrentPart().setPlayerOnPart(null);
                playerOnSet.setCurrentPart(null);
            }
        }
    }

    /**
     * Ticks the game by resetting all Labels and Buttons to reflect the state of the current
     * Player.
     */
    @FXML
    private void tick() {
        // Update all Labels
        playerLabel.setText("Player " + (currentPlayer + 1));
        dollarsLabel.setText("Dollars: " + players[currentPlayer].getDollars());
        creditsLabel.setText("Credits: " + players[currentPlayer].getCredits());
        rehearsalsLabel.setText(
                "Rehearsals: " + players[currentPlayer].getTimesRehearsedThisScene());
        dayLabel.setText("Day " + currentDay);
        // Update player's dice's ImageView
        ImageView diceView = (ImageView) playersPane.getChildren().get(currentPlayer);
        diceView.setImage(
                new Image(
                        players[currentPlayer].getPathToDice()
                                + players[currentPlayer].getRank()
                                + ext));
        // Clear all MenuButtons
        moveMenu.getItems().clear();
        takeMenu.getItems().clear();
        upgradeMenu.getItems().clear();
        // Disable moveMenu if it doesn't apply to the current player
        moveMenu.setDisable(
                players[currentPlayer].isActing()
                        | players[currentPlayer].hasMoved()
                        | players[currentPlayer].isNotWaitingForAction());
        // Disable takeMenu and rehearseButton if they don't apply to the current player
        if (players[currentPlayer].getCurrentRoom() instanceof Set) {
            takeMenu.setDisable(
                    players[currentPlayer].isActing()
                            | !(players[currentPlayer].getCurrentRoom() instanceof Set)
                            | players[currentPlayer].isNotWaitingForAction()
                            | ((Set) players[currentPlayer].getCurrentRoom()).getTakesLeft() < 1);
            rehearseButton.setDisable(
                    !players[currentPlayer].isActing()
                            | players[currentPlayer].isNotWaitingForAction()
                            | (players[currentPlayer].getTimesRehearsedThisScene()
                                    >= ((Set) players[currentPlayer].getCurrentRoom())
                                                    .getCard()
                                                    .getBudget()
                                            - 1));
        } else {
            takeMenu.setDisable(true);
            rehearseButton.setDisable(true);
        }
        // Disable actButton if it doesn't apply to the current player
        actButton.setDisable(
                !players[currentPlayer].isActing()
                        | players[currentPlayer].isNotWaitingForAction());
        // Disable upgradeMenu if it doesn't apply to the current player
        upgradeMenu.setDisable(
                players[currentPlayer].getCurrentRoom() != board.getCastingOffice()
                        | players[currentPlayer].isNotWaitingForAction()
                        | players[currentPlayer].hasUpgraded());
        // If moveMenu isn't disabled, update its MenuItems
        if (!moveMenu.isDisabled()) {
            // Add all neighboring rooms as MenuItems
            for (Room neighbor : players[currentPlayer].getCurrentRoom().getNeighbors()) {
                MenuItem neighborItem = new MenuItem(neighbor.getName());
                neighborItem.setOnAction(actionEvent -> move(neighborItem.getText()));
                moveMenu.getItems().add(neighborItem);
            }
        }
        // If takeMenu isn't disabled, update its MenuItems
        if (!takeMenu.isDisabled()) {
            boolean noPartAvailable = true;
            // Add all off-the-card parts as MenuItems
            for (Part part : ((Set) players[currentPlayer].getCurrentRoom()).getParts()) {
                noPartAvailable = isNoPartAvailable(noPartAvailable, part);
            }
            // Add all on-the-card parts as MenuItems
            for (Part part : ((Set) players[currentPlayer].getCurrentRoom()).getCard().getParts()) {
                noPartAvailable = isNoPartAvailable(noPartAvailable, part);
            }
            takeMenu.setDisable(noPartAvailable);
        }
        // If upgradeMenu isn't disabled, update its MenuItems
        if (!upgradeMenu.isDisabled()) {
            // For all ranks higher than the player's,
            for (int i = players[currentPlayer].getRank() - 1;
                    i < board.getUpgradeDollarPrices().length;
                    i++) {
                // If they can afford the rank with dollars, add the option as a MenuItem
                if (players[currentPlayer].getDollars() >= board.getUpgradeDollarPrices()[i]) {
                    MenuItem dollarItem =
                            new MenuItem(
                                    "Rank "
                                            + (i + 2)
                                            + ": "
                                            + board.getUpgradeDollarPrices()[i]
                                            + " dollars");
                    dollarItem.setOnAction(actionEvent -> upgrade(dollarItem.getText()));
                    upgradeMenu.getItems().add(dollarItem);
                }
                // If they can afford the rank with credits, add the option as a MenuItem
                if (players[currentPlayer].getCredits() >= board.getUpgradeCreditPrices()[i]) {
                    MenuItem creditItem =
                            new MenuItem(
                                    "Rank "
                                            + (i + 2)
                                            + ": "
                                            + board.getUpgradeCreditPrices()[i]
                                            + " credits");
                    creditItem.setOnAction(actionEvent -> upgrade(creditItem.getText()));
                    upgradeMenu.getItems().add(creditItem);
                }
            }
            // If no valid options were found, disable upgradeMenu
            upgradeMenu.setDisable(upgradeMenu.getItems().size() < 1);
        }
    }

    /**
     * Checks if a Part is available to the current Player.
     *
     * @param noPartAvailable the boolean representing whether any Part is available to the current
     *     Player
     * @param part the Part to be checked for availability
     * @return the boolean representing whether any Part is available to the current Player
     */
    private boolean isNoPartAvailable(boolean noPartAvailable, Part part) {
        if (part.getLevel() <= players[currentPlayer].getRank() && part.getPlayerOnPart() == null) {
            noPartAvailable = false;
            MenuItem partItem = new MenuItem(part.getName());
            partItem.setOnAction(actionEvent -> take(partItem.getText()));
            takeMenu.getItems().add(partItem);
        }
        return noPartAvailable;
    }

    /**
     * Moves the current Player to the Room represented by roomString, then, if the Room is a Set,
     * flips the Card on the Set.
     *
     * @param roomString the name of the Room to move to
     */
    private void move(String roomString) {
        // Update the player's current room
        players[currentPlayer].getCurrentRoom().removePlayer(players[currentPlayer]);
        players[currentPlayer].setCurrentRoom(board.getRooms().get(roomString));
        players[currentPlayer].getCurrentRoom().addPlayer(players[currentPlayer]);
        // Update the player's dice's ImageView's location to reflect the changed room
        ImageView diceView = (ImageView) playersPane.getChildren().get(currentPlayer);
        diceView.setX(currentPlayer * 20 + players[currentPlayer].getCurrentRoom().getX());
        diceView.setY(players[currentPlayer].getCurrentRoom().getY());
        // If the new room is a set, flip its card over
        if (players[currentPlayer].getCurrentRoom() instanceof Set) {
            Set set = (Set) players[currentPlayer].getCurrentRoom();
            int id = set.getId();
            ImageView cardView = (ImageView) cardsPane.getChildren().get(id);
            cardView.setImage(new Image(set.getCard().getPathToCard()));
        }
        // Update player's status to reflect the fact that they just moved
        players[currentPlayer].setMoved(true);
        tick();
    }

    /**
     * Sets the current Player's Part to the Part represented by partString.
     *
     * @param partString the name of the Part that the current Player will claim
     */
    private void take(String partString) {
        // Locate the correct part, and attach it to the current player
        for (Part part : ((Set) players[currentPlayer].getCurrentRoom()).getParts()) {
            if (part.getName().equals(partString)) {
                players[currentPlayer].setCurrentPart(part);
                players[currentPlayer].getCurrentPart().setPlayerOnPart(players[currentPlayer]);
            }
        }
        // If the part hasn't been located yet, check the set's card
        if (players[currentPlayer].getCurrentPart() == null) {
            for (Part part : ((Set) players[currentPlayer].getCurrentRoom()).getCard().getParts()) {
                if (part.getName().equals(partString)) {
                    players[currentPlayer].setCurrentPart(part);
                    players[currentPlayer].getCurrentPart().setPlayerOnPart(players[currentPlayer]);
                    // Update the player's status to reflect that their part is on a card
                    players[currentPlayer].setOnCard(true);
                }
            }
        }
        // Update the player's status to reflect that they just claimed a part
        players[currentPlayer].setActing(true);
        players[currentPlayer].setTimesRehearsedThisScene(0);
        // Update the player's dice's ImageView's size based on the part's dice size
        ImageView diceView = (ImageView) playersPane.getChildren().get(currentPlayer);
        diceView.setFitWidth(players[currentPlayer].getCurrentPart().getDiceSize());
        diceView.setFitHeight(players[currentPlayer].getCurrentPart().getDiceSize());
        // Update the player's dice's ImageView's position based on whether or not their part came
        // from a card
        if (players[currentPlayer].isOnCard()) {
            diceView.setX(
                    ((Set) players[currentPlayer].getCurrentRoom()).getCardX()
                            + players[currentPlayer].getCurrentPart().getX());
            diceView.setY(
                    ((Set) players[currentPlayer].getCurrentRoom()).getCardY()
                            + players[currentPlayer].getCurrentPart().getY());
        } else {
            diceView.setX(players[currentPlayer].getCurrentPart().getX());
            diceView.setY(players[currentPlayer].getCurrentPart().getY());
        }
        // Update the player's status to reflect the fact that they just made an action
        players[currentPlayer].setWaitingForAction(false);
        tick();
    }

    /** Adds one to the number of times the current Player has rehearsed for their current Part. */
    @FXML
    private void rehearse() {
        players[currentPlayer].setTimesRehearsedThisScene(
                players[currentPlayer].getTimesRehearsedThisScene() + 1);
        // Update the player's status to reflect the fact that they just made an action
        players[currentPlayer].setWaitingForAction(false);
        tick();
    }

    /** Attempts to complete a take for the current Player's Part. */
    @FXML
    private void act() {
        // Calculate the target number that must be rolled to complete the take
        int target =
                ((Set) players[currentPlayer].getCurrentRoom()).getCard().getBudget()
                        - players[currentPlayer].getTimesRehearsedThisScene();
        // Roll a number from 1-6 and update dice1 to reflect this
        int attempt = dice.roll(1)[0];
        dice1.setImage(new Image(dicePath + attempt + ext));
        // If the roll is successful, update the game's status and player's status to reflect this
        if (attempt >= target) {
            // Update the set's number of takes left
            ((Set) players[currentPlayer].getCurrentRoom())
                    .setTakesLeft(
                            ((Set) players[currentPlayer].getCurrentRoom()).getTakesLeft() - 1);
            // Add a take ImageView in the correct position for this take
            ImageView takeView = new ImageView(takePath);
            takeView.setFitWidth(takeSize);
            takeView.setFitHeight(takeSize);
            int defaultTakes = ((Set) players[currentPlayer].getCurrentRoom()).getDefaultTakes();
            int takesRemaining = ((Set) players[currentPlayer].getCurrentRoom()).getTakesLeft();
            int takeNumber = (defaultTakes - takesRemaining) - 1;
            takeView.setX(
                    ((Set) players[currentPlayer].getCurrentRoom()).getTakeXValues()[takeNumber]);
            takeView.setY(
                    ((Set) players[currentPlayer].getCurrentRoom()).getTakeYValues()[takeNumber]);
            takesPane.getChildren().add(takeView);
            // Add the appropriate amount of credits and dollars to the player
            if (players[currentPlayer].isOnCard()) {
                players[currentPlayer].setCredits(players[currentPlayer].getCredits() + 2);
            } else {
                players[currentPlayer].setCredits(players[currentPlayer].getCredits() + 1);
                players[currentPlayer].setDollars(players[currentPlayer].getDollars() + 1);
            }
        }
        // If the movie on this set's card is completed, check if a bonus applies to all players on
        // the set
        if (((Set) players[currentPlayer].getCurrentRoom()).getTakesLeft() < 1) {
            checkForBonusMoney();
        }
        // Update the player's status to reflect the fact that they just made an action
        players[currentPlayer].setWaitingForAction(false);
        // Tick the game if it is still going
        if (currentDay <= totalDays) {
            tick();
        }
    }

    /** Checks if a bonus payout applies on the current Player's Set, then applies it if it does. */
    private void checkForBonusMoney() {
        // Check if a bonus applies
        boolean hasStarPlayer = false;
        for (Part part : ((Set) players[currentPlayer].getCurrentRoom()).getCard().getParts()) {
            if (part.getPlayerOnPart() != null) {
                hasStarPlayer = true;
                break;
            }
        }
        // Apply the bonus
        if (hasStarPlayer) {
            // Generate array of bonus dollars
            int[] bonus =
                    dice.roll(
                            ((Set) players[currentPlayer].getCurrentRoom()).getCard().getBudget());
            // Update dice1-dice6 to reflect all of the rolls
            switch (bonus.length) {
                case 6:
                    dice6.setImage(new Image(dicePath + bonus[5] + ext));
                    dice6.setOpacity(1);
                    // Fall through
                case 5:
                    dice5.setImage(new Image(dicePath + bonus[4] + ext));
                    dice5.setOpacity(1);
                    // Fall through
                case 4:
                    dice4.setImage(new Image(dicePath + bonus[3] + ext));
                    dice4.setOpacity(1);
                    // Fall through
                case 3:
                    dice3.setImage(new Image(dicePath + bonus[2] + ext));
                    dice3.setOpacity(1);
                    // Fall through
                case 2:
                    dice2.setImage(new Image(dicePath + bonus[1] + ext));
                    dice2.setOpacity(1);
                    // Fall through
                case 1:
                    dice1.setImage(new Image(dicePath + bonus[0] + ext));
                    dice1.setOpacity(1);
                    // Fall through
                default:
                    break;
            }
            Arrays.sort(bonus);
            // Generate array of star players ordered by their part's level
            Player[] orderedStars = new Player[6];
            for (Part part : ((Set) players[currentPlayer].getCurrentRoom()).getCard().getParts()) {
                if (part.getPlayerOnPart() != null) {
                    orderedStars[part.getLevel() - 1] = part.getPlayerOnPart();
                }
            }
            // Apply dollars to stars in reverse order, wrapping around as necessary
            int playerPointer = orderedStars.length - 1;
            for (int i = bonus.length - 1; i >= 0; ) {
                if (orderedStars[playerPointer] != null) {
                    orderedStars[playerPointer].setDollars(
                            orderedStars[playerPointer].getDollars() + bonus[i]);
                    i--;
                }
                playerPointer--;
                if (playerPointer < 0) {
                    playerPointer = orderedStars.length - 1;
                }
            }
            // Apply dollars to extras based on their part's level
            for (Part part : ((Set) players[currentPlayer].getCurrentRoom()).getParts()) {
                if (part.getPlayerOnPart() != null) {
                    part.getPlayerOnPart()
                            .setDollars(part.getPlayerOnPart().getDollars() + part.getLevel());
                }
            }
        }
        // Detach players from parts and vice versa
        detachParts(players[currentPlayer]);
        for (int i = 0; i < players.length; i++) {
            if (players[currentPlayer].getCurrentRoom().getPlayers().contains(players[i])) {
                ImageView diceView = (ImageView) playersPane.getChildren().get(i);
                diceView.setFitWidth(20);
                diceView.setFitHeight(20);
                diceView.setX(i * 20 + players[i].getCurrentRoom().getX());
                diceView.setY(players[i].getCurrentRoom().getY());
            }
        }
        tickScene();
    }

    /**
     * Subtracts one from the number of scenes left for the day, then checks if the day is still
     * active.
     */
    private void tickScene() {
        scenesLeft--;
        if (scenesLeft < 2) {
            tickDay();
        }
    }

    /**
     * Updates the current Player's rank to the rank represented by upgradeString.
     *
     * @param upgradeString the rank and cost of the rank to use for upgrading
     */
    private void upgrade(String upgradeString) {
        // Separate string by spaces
        String[] upgradeArray = upgradeString.split(" ");
        // Parse the new rank
        int newRank = Integer.parseInt(upgradeArray[1].substring(0, 1));
        // Subtract the appropriate of the appropriate currency from the player
        if (upgradeArray[3].equals("dollars")) {
            players[currentPlayer].setDollars(
                    players[currentPlayer].getDollars()
                            - board.getUpgradeDollarPrices()[newRank - 2]);
        } else {
            players[currentPlayer].setCredits(
                    players[currentPlayer].getCredits()
                            - board.getUpgradeCreditPrices()[newRank - 2]);
        }
        // Update the player's rank
        players[currentPlayer].setRank(newRank);
        // Update the player's status to reflect the fact that they just upgraded their rank
        players[currentPlayer].setUpgraded(true);
        tick();
    }

    /**
     * Ends the current Player's turn by resetting their status, incrementing the current Player,
     * and resetting the Dice.
     */
    @FXML
    private void endTurn() {
        // Reset player's status if the game has begun
        if (currentPlayer > -1) {
            players[currentPlayer].setMoved(false);
            players[currentPlayer].setUpgraded(false);
            players[currentPlayer].setWaitingForAction(true);
        }
        // Increment the current player and wrap around as necessary
        currentPlayer++;
        if (currentPlayer >= players.length) {
            currentPlayer = 0;
        }
        // Reset dice1-dice6
        dice1.setImage(new Image(dicePath + 1 + ext));
        dice2.setOpacity(0);
        dice3.setOpacity(0);
        dice4.setOpacity(0);
        dice5.setOpacity(0);
        dice6.setOpacity(0);
        tick();
    }

    /**
     * Adds one to the current day, resets the number of scenes left, and then checks if the game
     * has ended.
     */
    private void tickDay() {
        scenesLeft = 10;
        currentDay++;
        if (currentDay > totalDays) {
            tallyScores();
        } else {
            resetDay();
        }
    }

    /**
     * Tallies all of the Player's scores, hides away all game controls, and displays the scores
     * using playerLabel and rehearsalsLabel.
     */
    private void tallyScores() {
        // Store player scores in an integer array
        int[] scores = new int[players.length];
        for (int i = 0; i < players.length; i++) {
            scores[i] = players[i].getScore();
        }
        // Hide all unnecessary labels
        dollarsLabel.setOpacity(0);
        creditsLabel.setOpacity(0);
        dayLabel.setOpacity(0);
        // Hide all game controls
        moveMenu.setOpacity(0);
        takeMenu.setOpacity(0);
        rehearseButton.setOpacity(0);
        actButton.setOpacity(0);
        upgradeMenu.setOpacity(0);
        // Disable all game controls
        moveMenu.setDisable(true);
        takeMenu.setDisable(true);
        rehearseButton.setDisable(true);
        actButton.setDisable(true);
        upgradeMenu.setDisable(true);
        // Hide the dice
        dice1.setOpacity(0);
        dice2.setOpacity(0);
        dice3.setOpacity(0);
        dice4.setOpacity(0);
        dice5.setOpacity(0);
        dice6.setOpacity(0);
        // Use playerLabel and rehearsalsLabel to display the final scores
        playerLabel.setText("Scores: ");
        rehearsalsLabel.setText("");
        for (int i = 0; i < scores.length; i++) {
            rehearsalsLabel.setText(
                    rehearsalsLabel.getText() + "\nPlayer " + (i + 1) + ": " + scores[i]);
        }
        // Update endButton to have the functionality of ending the game
        endButton.setText("End Game");
        endButton.setOnAction(actionEvent -> Platform.exit());
    }
}
