import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Deadwood {
    private static final Controller controller = new Controller();
    private static final ArrayList<Card> deck = new ArrayList<>();
    private static final HashMap<String, Room> rooms = new HashMap<>();
    protected static int[] upgradeCreditPrices;
    protected static int[] upgradeDollarPrices;
    private static Player[] players;
    private static int scenesLeft;
    private static int daysLeft;
    private static Room trailers;
    private static Room castingOffice;
    private static boolean gameIsRunning;

    public static void main(String[] args) {
        init();
        while (gameIsRunning) {
            for (int i = 0; i < players.length; i++) {
                tick(players[i], i + 1);
                if (scenesLeft < 2) {
                    daysLeft--;
                }
                if (daysLeft < 1) {
                    gameIsRunning = false;
                    break;
                }
            }
        }
        tallyScores();
    }

    private static void init() {
        // Get number of players from user input
        int playerCount = controller.getInput(0);

        // Initialize array of playerCount players
        players = new Player[playerCount];

        // Establish how many days will pass based on amount of players
        if (players.length <= 3) {
            daysLeft = 3;
        } else {
            daysLeft = 4;
        }

        // Initialize root Rooms
        trailers = new Room("Trailers", new HashSet<>());
        castingOffice = new Room("Casting Office", new HashSet<>());

        // TODO: BEGIN TESTING RELATIONS
        trailers.addNeighbor(castingOffice);
        Set set = new Set("Test Set", new HashSet<>(), 1, new HashSet<>());
        Card card = new Card("Test Card", 0, "Test Card Description", 1, new HashSet<>());
        trailers.addNeighbor(set);
        set.addPart(new Part("Part name", "Part line", 0));
        set.setCard(card);
        set.getCard().addPart(new Part("Card part name", "Part line", 6));
        // TODO: END TESTING RELATIONS

        // Initialize remaining rooms
        initRooms();

        // Initialize deck
        initCards();

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

        resetDay();
        gameIsRunning = true;
    }

    private static void initCards() {
        // TODO: Get card data from XML
        Collections.shuffle(deck);
    }

    private static void initRooms() {
        // TODO: Get room data from XML
        linkRooms();
    }

    private static void linkRooms() {
        // TODO: Establish all neighbor relations
    }

    private static void tick(Player player, int playerNumber) {
        int inputCode;
        int outputType = -1;
        while (outputType < 0) {
            controller.writePrompt(player, playerNumber);
            inputCode = controller.getInput(1);
            switch (inputCode) {
                case 0:
                    outputType = move(player);
                    controller.writeResponse(inputCode, outputType);
                    break;
                case 1:
                    outputType = takePart(player);
                    controller.writeResponse(inputCode, outputType);
                    break;
                case 2:
                    outputType = rehearse(player);
                    controller.writeResponse(inputCode, outputType);
                    break;
                case 3:
                    outputType = act(player);
                    controller.writeResponse(inputCode, outputType);
                    if (player.getCurrentRoom() instanceof Set && ((Set) player.getCurrentRoom()).getTakesLeft() < 1) {
                        scenesLeft--;
                        controller.writeResponse(5, checkForBonusMoney(player));
                    }
                    break;
                case 4:
                    outputType = upgrade(player);
                    controller.writeResponse(inputCode, outputType);
                    break;
                default:
                    break;
            }
        }

        // This is a placeholder to stop the game from looping infinitely.
        daysLeft = players.length - playerNumber;
    }

    private static int move(Player player) {
        if (player.isActing()) {
            return -1;
        } else {
            player.getCurrentRoom().removePlayer(player);
            player.setCurrentRoom(controller.getRoom(player));
            player.getCurrentRoom().addPlayer(player);
            return 0;
        }
    }

    private static int takePart(Player player) {
        if (!(player.getCurrentRoom() instanceof Set)) {
            return -1;
        } else if (player.isActing()) {
            return -2;
        } else if (((Set) player.getCurrentRoom()).getTakesLeft() < 1) {
            return -3;
        } else {
            player.setCurrentPart(controller.getPart(player));
            player.getCurrentPart().setPlayerOnPart(player);
            player.setActing(true);
            return 0;
        }
    }

    private static int rehearse(Player player) {
        if (!player.isActing()) {
            return -1;
        }

        Set currentSet = (Set) player.getCurrentRoom();
        int budget = currentSet.getCard().getBudget();
        if ((player.getTimesRehearsedThisScene() - budget) < 1) {
            return 1;
        }

        player.setTimesRehearsedThisScene(player.getTimesRehearsedThisScene() + 1);
        return 0;
    }

    private static int act(Player player) {
        if (!player.isActing()) {
            return -1;
        }

        Set currentSet = (Set) player.getCurrentRoom();
        int budget = currentSet.getCard().getBudget();
        int target = budget - player.getTimesRehearsedThisScene();
        int roll = Dice.roll(1)[0];
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
        } else {
            if (!player.isOnCard()) {
                player.setDollars(1 + player.getDollars());
                return 2;
            } else {
                return 3;
            }
        }
    }

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
            return -1;
        }

        // Get array of bonus money
        int[] bonus = Dice.roll(((Set) player.getCurrentRoom()).getCard().getBudget());
        Arrays.sort(bonus);

        // Generate array of star players based on their part's level
        Player[] orderedStars = new Player[6];
        for (int i = 0; i < orderedStars.length; i++) {
            for (Part part : ((Set) player.getCurrentRoom()).getCard().getParts()) {
                if (part.getPlayerOnPart() != null) {
                    orderedStars[i] = part.getPlayerOnPart();
                    break;
                }
            }
        }

        // Apply money to stars
        int playerPointer = -1;
        for (int i = bonus.length - 1; i >= 0; i--) {
            playerPointer++;
            for (; orderedStars[playerPointer] != null; playerPointer++) {
                if (playerPointer >= orderedStars.length - 1) {
                    if (orderedStars[playerPointer] != null) {
                        orderedStars[playerPointer].setDollars(orderedStars[playerPointer].getDollars() + bonus[i]);
                    }
                    playerPointer = 0;
                }
            }
            orderedStars[playerPointer].setDollars(orderedStars[playerPointer].getDollars() + bonus[i]);
        }

        // Apply money to extras
        for (Part part : ((Set) player.getCurrentRoom()).getParts()) {
            if (part.getPlayerOnPart() != null) {
                part.getPlayerOnPart().setDollars(player.getDollars() + player.getCurrentPart().getLevel());
            }
        }

        // Remove players from parts
        for (Part part : (((Set) player.getCurrentRoom()).getParts())) {
            part.setPlayerOnPart(null);
        }

        // Remove parts from players
        for (Player playerInSet : player.getCurrentRoom().getPlayers()) {
            playerInSet.setCurrentPart(null);
            playerInSet.setActing(false);
        }
        return 0;
    }

    private static int upgrade(Player player) {
        if (player.getCurrentRoom() != castingOffice) {
            return -1;
        }

        int newRank = controller.getRank(player);

        if (newRank < 1) {
            return -2;
        }

        player.setRank(newRank);
        return 0;
    }

    private static void resetDay() {
        // Reset players
        for (Player player : players) {
            player.setCurrentRoom(trailers);
            player.setActing(false);
        }

        // Reset sets
        for (Room room : rooms.values()) {
            if (room instanceof Set) {
                ((Set) room).setTakesLeft(((Set) room).getDefaultTakes());
                ((Set) room).setCard(deck.get(0));
                deck.remove(0);
            }
        }
        scenesLeft = 10;
    }

    private static void tallyScores() {
        int[] scores = new int[players.length];
        for (int i = 0; i < players.length; i++) {
            scores[i] = players[i].getScore();
        }
        controller.writeScores(scores);
    }
}