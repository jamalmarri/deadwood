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

public class Deadwood {

    private static final String ext = ".png";
    private static final String dicePath = "img/dice/white/";
    private static final String takePath = "img/shot.png";
    private static final int takeSize = 47;
    private static Screen screen = new Screen();
    private static Board board = Board.getInstance();
    private static Dice dice = Dice.getInstance();
    private static Player[] players;
    private static int currentPlayer = -1;
    private static int scenesLeft;
    private static int totalDays;
    private static int currentDay;

    @FXML
    private Pane cardsPane;
    @FXML
    private Pane takesPane;
    @FXML
    private Pane playersPane;
    @FXML
    private Label playerLabel;
    @FXML
    private Label dollarsLabel;
    @FXML
    private Label creditsLabel;
    @FXML
    private Label rehearsalsLabel;
    @FXML
    private MenuButton moveMenu;
    @FXML
    private MenuButton takeMenu;
    @FXML
    private Button rehearseButton;
    @FXML
    private Button actButton;
    @FXML
    private MenuButton upgradeMenu;
    @FXML
    private Button endButton;
    @FXML
    private ImageView dice1;
    @FXML
    private ImageView dice2;
    @FXML
    private ImageView dice3;
    @FXML
    private ImageView dice4;
    @FXML
    private ImageView dice5;
    @FXML
    private ImageView dice6;
    @FXML
    private Label dayLabel;

    public static void main(String[] args) {
        new JFXPanel();
        Platform.runLater(() -> screen.start(new Stage()));
    }

    static void initPlayers(int playerCount) {
        players = new Player[playerCount];
        screen.switchScene();
    }

    @FXML
    public void initialize() {
        if (players.length <= 3) {
            totalDays = 3;
        } else {
            totalDays = 4;
        }
        currentDay = 1;

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
            Image diceImage = new Image(players[i].getPathToDice() + players[i].getRank() + ext);
            ImageView diceView = new ImageView(diceImage);
            diceView.setFitWidth(20);
            diceView.setFitHeight(20);
            playersPane.getChildren().add(i, diceView);
        }
        resetDay();
    }

    private void resetDay() {
        for (int i = 0; i < players.length; i++) {
            if (currentPlayer > -1) {
                players[i].getCurrentRoom().removePlayer(players[i]);
            }

            players[i].setCurrentRoom(board.getTrailers());
            players[i].getCurrentRoom().addPlayer(players[i]);
            if (players[i].isActing()) {
                players[i].setActing(false);
                players[i].setOnCard(false);
                players[i].setTimesRehearsedThisScene(0);
                players[i].getCurrentPart().setPlayerOnPart(null);
                players[i].setCurrentPart(null);
            }
            players[i].setMoved(false);
            players[i].setWaitingForAction(true);

            ImageView diceView = (ImageView) playersPane.getChildren().get(i);
            diceView.setFitWidth(20);
            diceView.setFitHeight(20);
            diceView.setX(i * 20 + players[i].getCurrentRoom().getX());
            diceView.setY(players[i].getCurrentRoom().getY());
        }
        takesPane.getChildren().clear();
        cardsPane.getChildren().clear();
        for (int i = 0; i < 10; i++) {
            cardsPane.getChildren().add(new ImageView());
        }
        for (Room room : board.getRooms().values()) {
            if (room instanceof Set) {
                Set set = (Set) room;
                set.setTakesLeft(set.getDefaultTakes());

                Card card = board.getDeck().remove(board.getDeck().size() - 1);
                set.setCard(card);

                ImageView cardView = new ImageView("img/cardback" + ext);
                cardView.setX(set.getCardX());
                cardView.setY(set.getCardY());
                cardsPane.getChildren().set(set.getId(), cardView);
            }
        }
        scenesLeft = 10;

        if (currentPlayer < 0) {
            end();
        }
    }

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

    @FXML
    private void tick() {
        playerLabel.setText("Player " + (currentPlayer + 1));
        dollarsLabel.setText("Dollars: " + players[currentPlayer].getDollars());
        creditsLabel.setText("Credits: " + players[currentPlayer].getCredits());
        rehearsalsLabel.setText("Rehearsals: " +
                players[currentPlayer].getTimesRehearsedThisScene());
        dayLabel.setText("Day " + currentDay);
        ImageView diceView = (ImageView) playersPane.getChildren().get(currentPlayer);
        diceView.setImage(new Image(players[currentPlayer].getPathToDice()
                + players[currentPlayer].getRank() + ext));

        moveMenu.getItems().clear();
        takeMenu.getItems().clear();
        upgradeMenu.getItems().clear();

        moveMenu.setDisable(players[currentPlayer].isActing()
                | players[currentPlayer].hasMoved()
                | players[currentPlayer].isNotWaitingForAction());
        if (players[currentPlayer].getCurrentRoom() instanceof Set) {
            takeMenu.setDisable(players[currentPlayer].isActing()
                    | !(players[currentPlayer].getCurrentRoom() instanceof Set)
                    | players[currentPlayer].isNotWaitingForAction()
                    | ((Set) players[currentPlayer].getCurrentRoom()).getTakesLeft() < 1);
            rehearseButton.setDisable(!players[currentPlayer].isActing()
                    | players[currentPlayer].isNotWaitingForAction()
                    | (players[currentPlayer].getTimesRehearsedThisScene() >=
                    ((Set) players[currentPlayer].getCurrentRoom()).getCard().getBudget() - 1));
        } else {
            takeMenu.setDisable(true);
            rehearseButton.setDisable(true);
        }
        actButton.setDisable(!players[currentPlayer].isActing()
                | players[currentPlayer].isNotWaitingForAction());
        upgradeMenu.setDisable(players[currentPlayer].getCurrentRoom() != board.getCastingOffice()
                | players[currentPlayer].isNotWaitingForAction()
                | players[currentPlayer].hasUpgraded());

        if (!moveMenu.isDisabled()) {
            for (Room neighbor : players[currentPlayer].getCurrentRoom().getNeighbors()) {
                MenuItem neighborItem = new MenuItem(neighbor.getName());
                neighborItem.setOnAction(actionEvent -> move(neighborItem.getText()));
                moveMenu.getItems().add(neighborItem);
            }
        }

        if (!takeMenu.isDisabled()) {
            boolean noPartAvailable = true;
            for (Part part : ((Set) players[currentPlayer].getCurrentRoom()).getParts()) {
                noPartAvailable = isNoPartAvailable(noPartAvailable, part);
            }
            for (Part part : ((Set) players[currentPlayer].getCurrentRoom()).getCard().getParts()) {
                noPartAvailable = isNoPartAvailable(noPartAvailable, part);
            }
            takeMenu.setDisable(noPartAvailable);
        }

        if (!upgradeMenu.isDisabled()) {
            for (int i = players[currentPlayer].getRank() - 1;
                 i < board.getUpgradeDollarPrices().length; i++) {
                if (players[currentPlayer].getDollars() >= board.getUpgradeDollarPrices()[i]) {

                    MenuItem dollarItem = new MenuItem("Rank " + (i + 2) + ": "
                            + board.getUpgradeDollarPrices()[i] + " dollars");
                    dollarItem.setOnAction(actionEvent -> upgrade(dollarItem.getText()));
                    upgradeMenu.getItems().add(dollarItem);
                }
                if (players[currentPlayer].getCredits() >= board.getUpgradeCreditPrices()[i]) {
                    MenuItem creditItem = new MenuItem("Rank " + (i + 2) + ": "
                            + board.getUpgradeCreditPrices()[i] + " credits");
                    creditItem.setOnAction(actionEvent -> upgrade(creditItem.getText()));
                    upgradeMenu.getItems().add(creditItem);
                }
            }
            upgradeMenu.setDisable(upgradeMenu.getItems().size() < 1);
        }
    }

    private boolean isNoPartAvailable(boolean noPartAvailable, Part part) {
        if (part.getLevel() <= players[currentPlayer].getRank()
                && part.getPlayerOnPart() == null) {
            noPartAvailable = false;
            MenuItem partItem = new MenuItem(part.getName());
            partItem.setOnAction(actionEvent -> take(partItem.getText()));
            takeMenu.getItems().add(partItem);
        }
        return noPartAvailable;
    }

    private void move(String roomString) {
        players[currentPlayer].getCurrentRoom().removePlayer(players[currentPlayer]);
        players[currentPlayer].setCurrentRoom(board.getRooms().get(roomString));
        players[currentPlayer].getCurrentRoom().addPlayer(players[currentPlayer]);

        ImageView diceView = (ImageView) playersPane.getChildren().get(currentPlayer);
        diceView.setX(currentPlayer * 20 + players[currentPlayer].getCurrentRoom().getX());
        diceView.setY(players[currentPlayer].getCurrentRoom().getY());

        if (players[currentPlayer].getCurrentRoom() instanceof Set) {
            Set set = (Set) players[currentPlayer].getCurrentRoom();
            int id = set.getId();
            ImageView cardView = (ImageView) cardsPane.getChildren().get(id);
            cardView.setImage(new Image(set.getCard().getPathToCard()));
        }

        players[currentPlayer].setMoved(true);
        tick();
    }

    private void take(String partString) {
        for (Part part : ((Set) players[currentPlayer].getCurrentRoom()).getParts()) {
            if (part.getName().equals(partString)) {
                players[currentPlayer].setCurrentPart(part);
                players[currentPlayer].getCurrentPart().setPlayerOnPart(players[currentPlayer]);
            }
        }
        for (Part part : ((Set) players[currentPlayer].getCurrentRoom()).getCard().getParts()) {
            if (part.getName().equals(partString)) {
                players[currentPlayer].setCurrentPart(part);
                players[currentPlayer].getCurrentPart().setPlayerOnPart(players[currentPlayer]);
                players[currentPlayer].setOnCard(true);
            }
        }

        players[currentPlayer].setActing(true);
        players[currentPlayer].setTimesRehearsedThisScene(0);

        ImageView diceView = (ImageView) playersPane.getChildren().get(currentPlayer);
        diceView.setFitWidth(players[currentPlayer].getCurrentPart().getDiceSize());
        diceView.setFitHeight(players[currentPlayer].getCurrentPart().getDiceSize());
        if (players[currentPlayer].isOnCard()) {
            diceView.setX(((Set) players[currentPlayer].getCurrentRoom()).getCardX()
                    + players[currentPlayer].getCurrentPart().getX());
            diceView.setY(((Set) players[currentPlayer].getCurrentRoom()).getCardY()
                    + players[currentPlayer].getCurrentPart().getY());
        } else {
            diceView.setX(players[currentPlayer].getCurrentPart().getX());
            diceView.setY(players[currentPlayer].getCurrentPart().getY());
        }

        players[currentPlayer].setWaitingForAction(false);
        tick();
    }

    @FXML
    private void rehearse() {
        players[currentPlayer].setTimesRehearsedThisScene(
                players[currentPlayer].getTimesRehearsedThisScene() + 1);
        players[currentPlayer].setWaitingForAction(false);
        tick();
    }

    @FXML
    private void act() {
        int target = ((Set) players[currentPlayer].getCurrentRoom()).getCard().getBudget()
                - players[currentPlayer].getTimesRehearsedThisScene();
        int attempt = dice.roll(1)[0];
        dice1.setImage(new Image(dicePath + attempt + ext));

        if (attempt >= target) {
            ((Set) players[currentPlayer].getCurrentRoom()).setTakesLeft(
                    ((Set) players[currentPlayer].getCurrentRoom()).getTakesLeft() - 1);
            ImageView takeView = new ImageView(new Image(takePath));
            takeView.setFitWidth(takeSize);
            takeView.setFitHeight(takeSize);
            int defaultTakes = ((Set) players[currentPlayer].getCurrentRoom()).getDefaultTakes();
            int takesRemaining = ((Set) players[currentPlayer].getCurrentRoom()).getTakesLeft();
            int takeNumber = (defaultTakes - takesRemaining) - 1;
            takeView.setX(((Set) players[currentPlayer].getCurrentRoom()).getTakeXValues()
                    [takeNumber]);
            takeView.setY(((Set) players[currentPlayer].getCurrentRoom()).getTakeYValues()
                    [takeNumber]);
            takesPane.getChildren().add(takeView);

            if (players[currentPlayer].isOnCard()) {
                players[currentPlayer].setCredits(players[currentPlayer].getCredits() + 2);
            } else {
                players[currentPlayer].setCredits(players[currentPlayer].getCredits() + 1);
                players[currentPlayer].setDollars(players[currentPlayer].getDollars() + 1);
            }
        }

        if (((Set) players[currentPlayer].getCurrentRoom()).getTakesLeft() < 1) {
            checkForBonusMoney();
        }

        players[currentPlayer].setWaitingForAction(false);
        if (currentDay <= totalDays) {
            tick();
        }
    }

    private void checkForBonusMoney() {
        // Check if a bonus applies
        boolean hasStarPlayer = false;
        for (Part part : ((Set) players[currentPlayer].getCurrentRoom()).getCard().getParts()) {
            if (part.getPlayerOnPart() != null) {
                hasStarPlayer = true;
                break;
            }
        }

        if (hasStarPlayer) {
            // Generate array of bonus dollars
            int[] bonus = dice.roll(
                    ((Set) players[currentPlayer].getCurrentRoom()).getCard().getBudget());
            switch (bonus.length) {
                case 6:
                    dice6.setImage(new Image(dicePath + bonus[5] + ext));
                    dice6.setOpacity(1);
                case 5:
                    dice5.setImage(new Image(dicePath + bonus[4] + ext));
                    dice5.setOpacity(1);
                case 4:
                    dice4.setImage(new Image(dicePath + bonus[3] + ext));
                    dice4.setOpacity(1);
                case 3:
                    dice3.setImage(new Image(dicePath + bonus[2] + ext));
                    dice3.setOpacity(1);
                case 2:
                    dice2.setImage(new Image(dicePath + bonus[1] + ext));
                    dice2.setOpacity(1);
                case 1:
                    dice1.setImage(new Image(dicePath + bonus[0] + ext));
                    dice1.setOpacity(1);
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
                    orderedStars[playerPointer].setDollars(orderedStars[playerPointer].getDollars()
                            + bonus[i]);
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
                    part.getPlayerOnPart().setDollars(part.getPlayerOnPart().getDollars()
                            + part.getLevel());
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

    private void tickScene() {
        scenesLeft--;
        if (scenesLeft < 2) {
            tickDay();
        }
    }

    private void upgrade(String upgradeString) {
        String[] upgradeArray = upgradeString.split(" ");
        int newRank = Integer.parseInt(upgradeArray[1].substring(0, 1));
        if (upgradeArray[3].equals("dollars")) {
            players[currentPlayer].setDollars(players[currentPlayer].getDollars()
                    - board.getUpgradeDollarPrices()[newRank - 2]);
        } else {
            players[currentPlayer].setCredits(players[currentPlayer].getCredits()
                    - board.getUpgradeCreditPrices()[newRank - 2]);
        }
        players[currentPlayer].setRank(newRank);
        players[currentPlayer].setUpgraded(true);
        tick();
    }

    @FXML
    private void end() {
        if (currentPlayer > -1) {
            players[currentPlayer].setMoved(false);
            players[currentPlayer].setUpgraded(false);
            players[currentPlayer].setWaitingForAction(true);
        }

        currentPlayer++;
        if (currentPlayer >= players.length) {
            currentPlayer = 0;
        }

        dice1.setImage(new Image(dicePath + 1 + ext));
        dice2.setOpacity(0);
        dice3.setOpacity(0);
        dice4.setOpacity(0);
        dice5.setOpacity(0);
        dice6.setOpacity(0);

        tick();
    }

    private void tickDay() {
        scenesLeft = 10;
        currentDay++;
        if (currentDay > totalDays) {
            tallyScores();
        } else {
            resetDay();
        }
    }

    private void tallyScores() {
        // Store player scores in an integer array
        int[] scores = new int[players.length];
        for (int i = 0; i < players.length; i++) {
            scores[i] = players[i].getScore();
        }
        dollarsLabel.setOpacity(0);
        creditsLabel.setOpacity(0);
        moveMenu.setOpacity(0);
        moveMenu.setDisable(true);
        takeMenu.setOpacity(0);
        takeMenu.setDisable(true);
        rehearseButton.setOpacity(0);
        rehearseButton.setDisable(true);
        actButton.setOpacity(0);
        actButton.setDisable(true);
        upgradeMenu.setOpacity(0);
        upgradeMenu.setDisable(true);
        endButton.setOpacity(0);
        endButton.setDisable(true);
        dice1.setOpacity(0);
        dice2.setOpacity(0);
        dice3.setOpacity(0);
        dice4.setOpacity(0);
        dice5.setOpacity(0);
        dice6.setOpacity(0);
        dayLabel.setOpacity(0);

        playerLabel.setText("Scores: ");
        rehearsalsLabel.setText("");
        for (int i = 0; i < scores.length; i++) {
            rehearsalsLabel.setText(rehearsalsLabel.getText() + "\nPlayer " + (i + 1) + ": "
                    + scores[i]);
        }
    }
}
