import java.util.InputMismatchException;
import java.util.Scanner;

public class Controller {
    protected Scanner scan;

    public Controller() {
        scan = new Scanner(System.in);
    }

    public void writePrompt(Player player, int playerNumber) {
        // Separator to make output easier to read
        System.out.println("------------------------------------------------------------");

        // Print player's information
        System.out.println("Player " + playerNumber + " | Rank: " + player.getRank() + " | Dollars: " + player.getDollars() +
                " | Credits: " + player.getCredits() + " | Current Space: " + player.getCurrentRoom().getName());

        // Print neighboring rooms of player's current room
        System.out.print("Neighboring rooms:");
        for (Room neighbor : player.getCurrentRoom().getNeighbors()) {
            System.out.print(" " + neighbor.getName());
        }

        System.out.println();
    }

    public void writeResponse(int action, int outputType) {
        switch (action) {
            case 0:
                switch (outputType) {
                    case 0:
                        System.out.println("Moved to new room!");
                        break;
                    default:
                        break;
                }
            case 1:
                switch (outputType) {
                    default:
                        break;
                }
            case 2:
                switch (outputType) {
                    default:
                        break;
                }
            case 3:
                switch (outputType) {
                    default:
                        break;
                }
            case 4:
                switch (outputType) {
                    default:
                        break;
                }
            default:
                break;
        }
    }

    public void writeScores(int[] scores) {
        System.out.println("Game Over!");
        System.out.println("Scores:");
        for (int i = 0; i < scores.length; i++) {
            System.out.println("Player " + (i + 1) + ": " + scores[i]);
        }
        scan.close();
    }

    public int getInput(int inputType) {
        switch (inputType) {
            case 0:
                return getPlayerCount();
            case 1:
                return getPlayerAction();
            default:
                return -1;
        }
    }

    private int getPlayerCount() {
        // Initialize Scanner for reading user input
        System.out.print("Enter number of players [2-8]: ");
        int playerCount = 0;

        // Request correct input until it is received
        while (playerCount < 2 | playerCount > 8) {
            try {
                playerCount = scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please try again: ");
                scan.next();
                continue;
            }
            if (!(playerCount >= 2 && playerCount <= 8)) {
                System.out.print("Invalid amount of players [2-8]. Please try again: ");
            }
        }

        scan.nextLine();
        return playerCount;
    }

    private int getPlayerAction() {
        System.out.println("What would you like to do?");
        System.out.println("(move, take, rehearse, act, upgrade)");
        String input;
        while (true) {
            input = scan.nextLine();
            switch (input) {
                case "move":
                    return 0;
                case "take":
                    return 1;
                case "rehearse":
                    return 2;
                case "act":
                    return 3;
                case "upgrade":
                    return 4;
                default:
                    System.out.print("Invalid action. Please try again: ");
                    break;
            }
        }
    }

    public Room getRoom(Player player) {
        System.out.println("Which room would you like to move to?");
        // Print neighboring rooms of player's current room
        System.out.print("Neighboring rooms:");
        for (Room neighbor : player.getCurrentRoom().getNeighbors()) {
            System.out.print(" " + neighbor.getName());
        }
        System.out.println();
        String input;
        while (true) {
            input = scan.nextLine();
            for (Room neighbor : player.getCurrentRoom().getNeighbors()) {
                if (neighbor.getName().equals(input)) {
                    return neighbor;
                }
            }
            System.out.print("Invalid room name. Please try again: ");
        }
    }
}
