import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Represents the Board of the game, including a deck of Cards, a map of all Rooms, and direct
 * references to the 'Trailers' and 'Casting Office' Rooms.
 *
 * @author Jamal Marri
 * @author Megan Theimer
 * @see Room
 */
public class Board {
    /** Whether or not the Board object has been initialized. */
    private static boolean objectExists = false;

    /** The one Board object for this Singleton class. */
    private static Board board;

    /** The Trailers Room. */
    private final Room trailers = new Room("Trailers", 1020, 275, new HashSet<>());

    /** The Casting Office Room. */
    private final Room castingOffice = new Room("Casting Office", 20, 475, new HashSet<>());

    /** The XMLParser that reads all Room, Card, and pricing information. */
    private final XMLParser parser = XMLParser.getInstance();

    /** The upgrade credit prices. */
    private int[] upgradeCreditPrices;

    /** The upgrade dollar prices. */
    private int[] upgradeDollarPrices;

    /** The deck of all Cards. */
    private ArrayList<Card> deck;

    /** All of the Rooms on this Board. */
    private HashMap<String, Room> rooms;

    /**
     * This constructor simply starts the process of reading all Card and Room information from the
     * game's XML files.
     */
    private Board() {
        // Initialize deck
        initCards();
        // Initialize rooms
        initRooms();
        // Initialize upgrade prices
        initPrices();
    }

    /**
     * Ensures exactly one Board exists and returns it.
     *
     * @return the Board object
     */
    public static Board getInstance() {
        // Check if a Board object already exists
        if (!objectExists) {
            // Create a new Board object if one doesn't already exist
            board = new Board();
            objectExists = true;
        }
        return board;
    }

    /** Reads and stores all Card information from the 'cards' XML file for the game. */
    private void initCards() {
        // Initialize the deck of all cards
        deck = parser.readCards();
        // Shuffle the deck
        Collections.shuffle(deck);
    }

    /** Reads and stores all Room information from the 'board' XML file for the game. */
    private void initRooms() {
        // Initialize the map of all rooms
        rooms = parser.readRooms();
        // Add special case rooms to the map
        rooms.put("Trailers", trailers);
        rooms.put("Casting Office", castingOffice);
        // Begin neighbor initialization
        linkRooms();
    }

    /**
     * Establishes all neighbor connections between the Rooms of the game, using information from
     * the 'boards' XML file.
     */
    private void linkRooms() {
        HashSet<String> neighborNames;
        // For every room in the rooms map
        for (String roomName : rooms.keySet()) {
            // Initialize neighbor names set using the parsed neighbor names
            neighborNames = parser.readNeighbors(roomName);
            // For every neighbor name in neighbor names HashSet
            for (String neighborName : neighborNames) {
                // Special case for 'Trailers' room
                if (neighborName.equals("trailer")) {
                    neighborName = "Trailers";
                    // Special case for 'Casting Office' room
                } else if (neighborName.equals("office")) {
                    neighborName = "Casting Office";
                }
                // Add neighbor to room's neighbors HashSet
                rooms.get(roomName).addNeighbor(rooms.get(neighborName));
            }
        }
    }

    /** Reads and stores all upgrade pricing information from the 'board' XML file for the game. */
    private void initPrices() {
        // Store parsed upgrade credit pricing
        upgradeCreditPrices = parser.readPricing("credit");
        // Store parsed upgrade dollar pricing
        upgradeDollarPrices = parser.readPricing("dollar");
    }

    /**
     * Gets the array of credit rank upgrade pricing, used when a Player upgrades their rank.
     *
     * @return the integer array of credit pricing for this Board
     */
    public int[] getUpgradeCreditPrices() {
        return upgradeCreditPrices;
    }

    /**
     * Gets the array of dollar rank upgrade pricing, used when a Player upgrades their rank.
     *
     * @return the integer array of dollar pricing for this Board
     */
    public int[] getUpgradeDollarPrices() {
        return upgradeDollarPrices;
    }

    /**
     * Gets the ArrayList of Cards, used to determine the movie on a particular Set.
     *
     * @return the ArrayList of Cards for this Board
     * @see ArrayList
     */
    public ArrayList<Card> getDeck() {
        return deck;
    }

    /**
     * Gets the HashMap of Rooms, used to navigate around this Board.
     *
     * @return the HashMap of Rooms for this Board
     * @see HashMap
     */
    public HashMap<String, Room> getRooms() {
        return rooms;
    }

    /**
     * Gets the direct reference to the 'Trailers' Room for when Player positions are reset at the
     * end of each day in the game.
     *
     * @return the 'Trailers' Room on this Board
     * @see Room
     */
    public Room getTrailers() {
        return trailers;
    }

    /**
     * Gets the direct reference to the 'Casting Office' Room to check if a Player can upgrade their
     * rank.
     *
     * @return the 'Casting Office' Room on this Board
     * @see Room
     */
    public Room getCastingOffice() {
        return castingOffice;
    }
}
