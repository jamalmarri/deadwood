import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Deadwood {
    private static Scanner scan;
    private static Player[] players;
    private static ArrayList<Card> deck;
    private static int scenesLeft;
    private static int daysLeft;
    private static Room trailer;
    private static Room castingOffice;
    private static boolean gameIsRunning;

    public static void main(String[] args) {
        init();
        String input;
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
        // Initialize Scanner for reading user input
        scan = new Scanner(System.in);
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

        // Close Scanner
        scan.close();

        // Initialize array of playerCount players
        players = new Player[playerCount];
        System.out.println("Players: " + players.length);

        // Establish how many days will pass based on amount of players
        if (players.length <= 3) {
            daysLeft = 3;
        } else {
            daysLeft = 4;
        }

        // Initialize root Rooms
        trailer = new Room("Trailer", new HashSet<>());
        castingOffice = new Room("Casting Office", new HashSet<>());

        // TODO: Establish all neighbor relations

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
            players[i].setCurrentRoom(trailer);
        }

        scenesLeft = 10;
        gameIsRunning = true;
    }

    private static void tick(Player player, int playerNumber) {
        // Print player's information
        System.out.println("Player " + playerNumber + " | Rank: " + player.getRank() + " | Dollars: " + player.getDollars() +
                " | Credits: " + player.getCredits() + " | Current Space: " + player.getCurrentRoom().getName());

        // Print neighboring rooms of player's current room
        System.out.print("Neighboring rooms:");
        for (Room neighbor : player.getCurrentRoom().getNeighbors()) {
            System.out.print(" " + neighbor.getName());
        }
        System.out.println();

        //TODO: Actual game logic HERE
        // This is a placeholder to stop the game from looping infinitely.
        daysLeft = players.length - playerNumber;
    }

    private static void tallyScores() {
        int[] scores = new int[players.length];
        for (int i = 0; i < players.length; i++) {
            scores[i] = players[i].getScore();
        }
        System.out.println("Game Over!");
        System.out.println("Scores:");
        for (int i = 0; i < scores.length; i++) {
            System.out.println("Player " + (i + 1) + ": " + scores[i]);
        }

    }
}