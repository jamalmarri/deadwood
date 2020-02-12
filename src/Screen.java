import java.util.InputMismatchException;
import java.util.Scanner;

public class Screen {
    protected Scanner scan;

    public Screen() {
        scan = new Scanner(System.in);
    }

    public void writePrompt(Player player, int playerNumber) {
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

    public void writeScores(int[] scores) {
        writeln("Game Over!");
        writeln("Scores:");
        for (int i = 0; i < scores.length; i++) {
            writeln("Player " + (i + 1) + ": " + scores[i]);
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

    private int getPlayerAction() {
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

    public int getRank(Player player) {
        int rank = player.getRank();
        writeln("");
        writeln("What rank would you like to upgrade to? You are currently rank " + rank + ". Enter your current rank to cancel.");
        for (int i = 0; i < Deadwood.upgradeCreditPrices.length; i++) {
            writeln("Rank " + (i + 1) + ": " + Deadwood.upgradeCreditPrices[i] + " Credits or " + Deadwood.upgradeDollarPrices[i] + " Dollars.");
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
                if (player.getCredits() < Deadwood.upgradeCreditPrices[rank - 1] && player.getDollars() < Deadwood.upgradeDollarPrices[rank - 1]) {
                    write("You don't have enough credits or dollars for that rank. Please try again: ");
                } else {
                    writeln("What currency would you like to use to purchase this rank? (credits, dollars)");
                    String currency;
                    while (true) {
                        currency = scan.nextLine();
                        switch (currency) {
                            case "credits":
                                if (player.getCredits() >= Deadwood.upgradeCreditPrices[rank - 1]) {
                                    player.setCredits(player.getCredits() - Deadwood.upgradeCreditPrices[rank - 1]);
                                    return rank;
                                } else {
                                    writeln("");
                                    write("You don't have enough of that currency for this rank. Please try again: ");
                                }
                                break;
                            case "dollars":
                                if (player.getDollars() >= Deadwood.upgradeDollarPrices[rank - 1]) {
                                    player.setDollars(player.getDollars() - Deadwood.upgradeDollarPrices[rank - 1]);
                                    return rank;
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
            }
        }
    }

    private void write(String output) {
        System.out.print(output);
    }

    private void writeln(String output) {
        write(output);
        System.out.println();
    }
}
