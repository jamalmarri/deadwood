import java.util.ArrayList;
import java.util.HashSet;

public class Deadwood {
    private static final Controller controller = new Controller();
    private static Player[] players;
    private static ArrayList<Card> deck;
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
        trailers.addNeighbor(castingOffice);

        linkRooms();

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

        scenesLeft = 10;
        resetDay();
        gameIsRunning = true;
    }

    private static void linkRooms() {
        // TODO: Establish all neighbor relations
        return;
    }

    private static void tick(Player player, int playerNumber) {

        controller.writePrompt(player, playerNumber);
        int inputCode = controller.getInput(1);

        switch(inputCode) {
            case 0:
                controller.writeResponse(inputCode, move(player));
                break;
            case 1:
                controller.writeResponse(inputCode, takePart(player));
                break;
            case 2:
                controller.writeResponse(inputCode, rehearse(player));
                break;
            case 3:
                controller.writeResponse(inputCode, act(player));
                break;
            case 4:
                controller.writeResponse(inputCode, upgrade(player));
                break;
            default:
                break;
        }

        // This is a placeholder to stop the game from looping infinitely.
        daysLeft = players.length - playerNumber;
    }

    private static int move(Player player) {
        player.getCurrentRoom().removePlayer(player);
        player.setCurrentRoom(controller.getRoom(player));
        player.getCurrentRoom().addPlayer(player);
        return 0;
    }

    private static int takePart(Player player) {
        return 0;
    }

    private static int rehearse(Player player) {
        if (!(player.getCurrentRoom() instanceof Set) && player.isActing()) {
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
        if (!(player.getCurrentRoom() instanceof Set) && player.isActing()) {
            return -1;
        }

        Set currentSet = (Set) player.getCurrentRoom();
        int budget = currentSet.getCard().getBudget();
        int target = budget - player.getTimesRehearsedThisScene();
        int roll = Dice.roll(1)[0];
        if (roll >= target) {
            currentSet.setTakesLeft(currentSet.getTakesLeft() - 1);
            if (currentSet.getTakesLeft() < 1) {
                scenesLeft--;
            }
            if (player.isOnCard()) {
                player.setDollars(2 + player.getDollars());
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
        return 0;
    }

    private static int upgrade(Player player) {
        return 0;
    }

    private static void resetDay() {
        for (int i = 0; i < players.length; i++) {
            players[i].setCurrentRoom(trailers);
        }
        //TODO: Deal out cards
    }

    private static void tallyScores() {
        int[] scores = new int[players.length];
        for (int i = 0; i < players.length; i++) {
            scores[i] = players[i].getScore();
        }
        controller.writeScores(scores);
    }
}