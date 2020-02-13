import java.util.Arrays;

public class Deadwood {
    private static final Board board = new Board();
    private static final Screen screen = new Screen(board);
    private static Player[] players;
    private static int scenesLeft;
    private static int daysLeft;
    private static boolean gameIsRunning;

    public static void main(String[] args) {
        init();
        while (gameIsRunning) {
            for (int i = 0; i < players.length; i++) {
                tick(players[i], i + 1);
                if (scenesLeft < 2) {
                    daysLeft--;
                    resetDay();
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

        resetDay();
        gameIsRunning = true;
    }

    private static void tick(Player player, int playerNumber) {
        int inputCode;
        int outputType = -1;
        while (outputType < 0) {
            screen.writePlayerInfo(player, playerNumber);
            inputCode = screen.getPlayerAction();
            switch (inputCode) {
                case 0:
                    outputType = move(player);
                    screen.writeResponse(inputCode, outputType);
                    if (player.getCurrentRoom() instanceof Set && !player.isActing() && ((Set) player.getCurrentRoom()).getTakesLeft() > 0 && screen.attemptPart(player)) {
                        screen.writeResponse(1, takePart(player));
                    }
                    break;
                case 1:
                    outputType = takePart(player);
                    screen.writeResponse(inputCode, outputType);
                    break;
                case 2:
                    outputType = rehearse(player);
                    screen.writeResponse(inputCode, outputType);
                    break;
                case 3:
                    outputType = act(player);
                    screen.writeResponse(inputCode, outputType);
                    if (player.getCurrentRoom() instanceof Set && ((Set) player.getCurrentRoom()).getTakesLeft() < 1 && player.isActing()) {
                        scenesLeft--;
                        screen.writeResponse(5, checkForBonusMoney(player));
                    }
                    break;
                case 4:
                    outputType = upgrade(player);
                    screen.writeResponse(inputCode, outputType);
                    break;
                default:
                    break;
            }
        }
    }

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

    private static int takePart(Player player) {
        boolean partPossible = false;
        for (Part part : ((Set) player.getCurrentRoom()).getParts()) {
            if (part.getLevel() <= player.getRank() && part.getPlayerOnPart() == null) {
                partPossible = true;
                break;
            }
        }
        for (Part part : ((Set) player.getCurrentRoom()).getCard().getParts()) {
            if (part.getLevel() <= player.getRank() && part.getPlayerOnPart() == null) {
                partPossible = true;
                break;
            }
        }

        if (!(player.getCurrentRoom() instanceof Set)) {
            return -1;
        } else if (player.isActing()) {
            return -2;
        } else if (((Set) player.getCurrentRoom()).getTakesLeft() < 1) {
            return -3;
        } else if (!partPossible) {
            return -4;
        } else {
            player.setCurrentPart(screen.getPart(player));
            player.getCurrentPart().setPlayerOnPart(player);
            player.setActing(true);
            player.setOnCard(((Set) player.getCurrentRoom()).getCard().getParts().contains(player.getCurrentPart()));
            return 0;
        }
    }

    private static int rehearse(Player player) {
        if (!player.isActing()) {
            return -1;
        }

        Set currentSet = (Set) player.getCurrentRoom();
        int budget = currentSet.getCard().getBudget();
        if ((budget - player.getTimesRehearsedThisScene()) < 2) {
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
        for (Part part : ((Set) player.getCurrentRoom()).getCard().getParts()) {
            if (part.getPlayerOnPart() != null) {
                orderedStars[part.getLevel() - 1] = part.getPlayerOnPart();
            }
        }

        // Apply money to stars
        int playerPointer = orderedStars.length - 1;
        for (int i = bonus.length - 1; i >= 0; ) {
            if (orderedStars[playerPointer] != null) {
                System.out.println("Adding " + bonus[i] + " dollars to Player playing " + orderedStars[playerPointer].getCurrentPart().getName() + ".");
                orderedStars[playerPointer].setDollars(orderedStars[playerPointer].getDollars() + bonus[i]);
                i--;
            }
            playerPointer--;
            if (playerPointer < 0) {
                playerPointer = orderedStars.length - 1;
            }
        }

        // Apply money to extras
        for (Part part : ((Set) player.getCurrentRoom()).getParts()) {
            if (part.getPlayerOnPart() != null) {
                System.out.println("Adding " + part.getLevel() + " dollars to Player playing " + orderedStars[playerPointer].getCurrentPart().getName() + ".");
                part.getPlayerOnPart().setDollars(player.getDollars() + player.getCurrentPart().getLevel());
            }
        }

        // Remove players from parts and vice versa
        for (Player playerOnSet : player.getCurrentRoom().getPlayers()) {
            playerOnSet.setActing(false);
            playerOnSet.setOnCard(false);
            playerOnSet.setTimesRehearsedThisScene(0);
            player.getCurrentPart().setPlayerOnPart(null);
            playerOnSet.setCurrentPart(null);
        }
        return 0;
    }

    private static int upgrade(Player player) {
        if (player.getCurrentRoom() != board.getCastingOffice()) {
            return -1;
        }

        int newRank = screen.getRank(player);

        if (newRank < 1) {
            return -2;
        }

        String currency = screen.getCurrency(player, newRank);

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

    private static void resetDay() {
        // Reset players
        for (Player player : players) {
            player.setCurrentRoom(board.getTrailers());
            player.setActing(false);
        }

        // Reset sets
        for (Room room : board.getRooms().values()) {
            if (room instanceof Set) {
                ((Set) room).setTakesLeft(((Set) room).getDefaultTakes());
                ((Set) room).setCard(board.getDeck().get(board.getDeck().size() - 1));
                board.getDeck().remove(board.getDeck().size() - 1);
            }
        }
        scenesLeft = 10;

        if (gameIsRunning) {
            screen.writeResetMessage();
        }
    }

    private static void tallyScores() {
        int[] scores = new int[players.length];
        for (int i = 0; i < players.length; i++) {
            scores[i] = players[i].getScore();
        }
        screen.writeScores(scores);
    }
}
