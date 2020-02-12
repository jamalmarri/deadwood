import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Represents the Screen that the user interacts
 * with, and handles all input and output for the game.
 */
public class Screen {
    protected Scanner scan;
    private Board board;

    /**
     * Class constructor.
     *
     * @param board the Board belonging to the game that this Screen is handling the I/O of.
     */
    public Screen(Board board) {
        scan = new Scanner(System.in);
        this.board = board;
    }

    /**
     * Writes the information that each user will see when beginning
     * their turn.
     *
     * @param player the Player whose prompt will be written.
     * @param playerNumber the number that identifies the Player.
     */
    public void writePlayerInfo(Player player, int playerNumber) {
        // Separator to make output easier to read
        writeln("------------------------------------------------------------");

        // Print player's information
        writeln("Player " + playerNumber + " | Rank: " + player.getRank() + " | Dollars: " + player.getDollars() +
                " | Credits: " + player.getCredits() + " | Current Space: " + player.getCurrentRoom().getName());

        // Print neighboring rooms of player's current room
        write("Neighboring rooms:");
        for (Room neighbor : player.getCurrentRoom().getNeighbors()) {
            write(" " + neighbor.getName());
        }

        writeln("");
    }

    /**
     * Writes the appropriate response to a given move
     * that the Player makes, using the given output type.
     *
     * @param action the action that was performed to initiate this response.
     * @param outputType the type of output to be written.
     */
    public void writeResponse(int action, int outputType) {
        writeln("");
        switch (action) {
            case 0:
                switch (outputType) {
                    case -1:
                        writeln("You cannot move rooms while in a movie.");
                        break;
                    case 0:
                        writeln("You moved to a new room!");
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                switch (outputType) {
                    case -3:
                        writeln("This movie has already been completed. Come back tomorrow.");
                        break;
                    case -2:
                        writeln("You cannot take a new part while in a movie.");
                        break;
                    case -1:
                        writeln("You are not on a set.");
                        break;
                    case 0:
                        writeln("You took a new part!");
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                switch (outputType) {
                    case -1:
                        writeln("You are not currently in a movie.");
                        break;
                    case 0:
                        writeln("You rehearsed for your part!");
                        break;
                    case 1:
                        writeln("You don't need to rehearse. Your acting success is already guaranteed.");
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                switch (outputType) {
                    case -1:
                        writeln("You are not currently in a movie.");
                        break;
                    case 0:
                        writeln("You successfully completed a shot! You got 2 credits.");
                        break;
                    case 1:
                        writeln("You successfully completed a shot! You got 1 credit and 1 dollar.");
                        break;
                    case 2:
                        writeln("You failed to complete this shot. You got 1 dollar.");
                        break;
                    case 3:
                        writeln("You failed to complete this shot.");
                        break;
                    default:
                        break;
                }
                break;
            case 4:
                switch (outputType) {
                    case -2:
                        writeln("You cancelled your upgrade.");
                        break;
                    case -1:
                        writeln("You are not in the Casting Office.");
                        break;
                    case 0:
                        writeln("You upgraded your rank!");
                        break;
                    default:
                        break;
                }
                break;
            case 5:
                if (outputType == 0) {
                    writeln("Bonus money was given to all players on set!");
                }
                writeln("Movie completed! Come back tomorrow!");
                break;
            default:
                break;
        }
    }

    /**
     * Writes all of the Players' scores at the end
     * of the game, using the inputted array of scores.
     *
     * @param scores the integer array representing all of the Players' scores.
     */
    public void writeScores(int[] scores) {
        writeln("Game Over!");
        writeln("Scores:");
        for (int i = 0; i < scores.length; i++) {
            writeln("Player " + (i + 1) + ": " + scores[i]);
        }
        scan.close();
    }

    /**
     * Writes a prompt for the user to input the number
     * of Players, then waits for valid input to return.
     *
     * @return the number of Players in the game.
     */
    public int getPlayerCount() {
        write("Enter number of players [2-8]: ");
        int playerCount = 0;

        // Request correct input until it is received
        while (playerCount < 2 | playerCount > 8) {
            try {
                playerCount = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {
                writeln("");
                write("Invalid input. Please try again: ");
                scan.nextLine();
                continue;
            }
            if (!(playerCount >= 2 && playerCount <= 8)) {
                writeln("");
                write("Invalid amount of players [2-8]. Please try again: ");
            }
        }
        return playerCount;
    }

    /**
     * Writes a prompt for the user to input their next action
     * for the current turn, then waits for valid input to return.
     *
     * @return the integer code representing a valid action.
     */
    public int getPlayerAction() {
        writeln("");
        writeln("What would you like to do?");
        writeln("(move, take, rehearse, act, upgrade)");
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
                    writeln("");
                    write("Invalid action. Please try again: ");
                    break;
            }
        }
    }

    /**
     * Writes a prompt for the user to input the Room they would
     * like to move to, then waits for a valid Room name to return.
     *
     * @return the Room the Player will move to.
     */
    public Room getRoom(Player player) {
        writeln("");
        writeln("Which room would you like to move to?");
        // Print neighboring rooms of player's current room
        write("Neighboring rooms:");
        for (Room neighbor : player.getCurrentRoom().getNeighbors()) {
            write(" " + neighbor.getName());
        }
        writeln("");
        String input;
        while (true) {
            input = scan.nextLine();
            for (Room neighbor : player.getCurrentRoom().getNeighbors()) {
                if (neighbor.getName().equals(input)) {
                    return neighbor;
                }
            }
            writeln("");
            write("Invalid room name. Please try again: ");
        }
    }

    /**
     * Writes a prompt for the user to input the Part they would
     * like to take, then waits for valid Part name to return.
     *
     * @return the Part the Player is going to take.
     */
    public Part getPart(Player player) {
        writeln("");
        writeln("Which part would you like to take?");

        writeln("On the card:");
        for (Part part : ((Set) player.getCurrentRoom()).getCard().getParts()) {
            writeln("(" + part.getLevel() + ") " + part.getName() + ": \"" + part.getLine() + "\"");
        }
        writeln("");

        writeln("Off the card:");
        for (Part part : ((Set) player.getCurrentRoom()).getParts()) {
            writeln("(" + part.getLevel() + ") " + part.getName() + ": \"" + part.getLine() + "\"");
        }

        String input;
        while (true) {
            input = scan.nextLine();
            for (Part part : ((Set) player.getCurrentRoom()).getCard().getParts()) {
                if (part.getName().equals(input)) {
                    if (player.getRank() >= part.getLevel()) {
                        player.setOnCard(true);
                        return part;
                    } else {
                        writeln("");
                        write("You are not a high enough rank for that part. Please try again: ");
                    }
                }
            }
            for (Part part : ((Set) player.getCurrentRoom()).getParts()) {
                if (part.getName().equals(input)) {
                    if (player.getRank() >= part.getLevel()) {
                        player.setOnCard(false);
                        return part;
                    } else {
                        writeln("");
                        write("You are not a high enough rank for that part. Please try again: ");
                    }
                }
            }
            writeln("");
            write("Invalid part name. Please try again: ");
        }
    }

    /**
     * Writes a prompt for the user to input the rank they would
     * like to upgrade to, then waits for valid input to return.
     *
     * @return the integer representing a valid rank.
     */
    public int getRank(Player player) {
        int rank = player.getRank();
        writeln("");
        writeln("What rank would you like to upgrade to? You are currently rank " + rank + ". Enter your current rank to cancel.");
        for (int i = 0; i < board.getUpgradeCreditPrices().length; i++) {
            writeln("Rank " + (i + 1) + ": " + board.getUpgradeCreditPrices()[i] + " Credits or " + board.getUpgradeDollarPrices()[i] + " Dollars.");
        }
        while (true) {
            try {
                rank = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {
                writeln("");
                write("Invalid input. Please try again: ");
                scan.nextLine();
                continue;
            }
            if (rank == player.getRank()) {
                return 0;
            } else if (rank < player.getRank()) {
                writeln("");
                write("Your rank is already higher than the chosen rank. Please try again: ");
            } else {
                writeln("");
                if (player.getCredits() < board.getUpgradeCreditPrices()[rank - 1] && player.getDollars() < board.getUpgradeDollarPrices()[rank - 1]) {
                    write("You don't have enough credits or dollars for that rank. Please try again: ");
                } else {
                    return rank;
                }
            }
        }
    }

    /**
     * Writes a prompt for the user to input the currency they wish
     * to use for their upgrade, then waits for valid input to return.
     *
     * @return the String representing the currency the player will use.
     */
    public String getCurrency(Player player, int rank) {
        writeln("What currency would you like to use to purchase this rank? (credits, dollars)");
        String currency;
        while (true) {
            currency = scan.nextLine();
            switch (currency) {
                case "credits":
                    if (player.getCredits() >= board.getUpgradeCreditPrices()[rank - 1]) {
                        return currency;
                    } else {
                        writeln("");
                        write("You don't have enough of that currency for this rank. Please try again: ");
                    }
                    break;
                case "dollars":
                    if (player.getDollars() >= board.getUpgradeDollarPrices()[rank - 1]) {
                        return currency;
                    } else {
                        writeln("");
                        write("You don't have enough of that currency for this rank. Please try again: ");
                    }
                    break;
                default:
                    writeln("");
                    write("Invalid currency type. Please try again: ");
                    break;
            }
        }
    }

    /**
     * Writes the given output to the System.out output stream.
     *
     * @param output the String to print to the output stream.
     */
    private void write(String output) {
        System.out.print(output);
    }

    /**
     * Writes the given output, then writes an empty line.
     *
     * @param output the String to print to the output stream.
     */
    private void writeln(String output) {
        write(output);
        System.out.println();
    }
}
