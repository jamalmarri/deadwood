import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Represents the Board of the game,
 * including a deck of Cards, a map of
 * all Rooms, and direct references to the
 * 'Trailers' and 'Casting Office' Rooms.
 */
public class Board {
    private final ArrayList<Card> deck = new ArrayList<>();
    private final HashMap<String, Room> rooms = new HashMap<>();
    private final Room trailers = new Room("Trailers", new HashSet<>());
    private final Room castingOffice = new Room("Casting Office", new HashSet<>());

    /**
     * This constructor simply starts
     * the process of reading all Card and
     * Room information from the game's XML
     * files.
     */
    public Board() {
        // Initialize deck
        initCards();
        // Initialize rooms
        initRooms();
    }

    /**
     * Reads and stores all Card information
     * from the 'cards' XML file for the game.
     */
    private void initCards() {
        // TODO: Get card data from XML
        Collections.shuffle(deck);
    }

    /**
     * Reads and stores all Card information
     * from the 'board' XML file for the game.
     */
    private void initRooms() {
        rooms.put("Trailers", trailers);
        rooms.put("Casting Office", castingOffice);

        // TODO: BEGIN TESTING RELATIONS
        trailers.addNeighbor(castingOffice);
        Set set = new Set("Test Set", new HashSet<>(), 1, new HashSet<>());
        Card card = new Card("Test Card", 0, "Test Card Description", 1, new HashSet<>());
        trailers.addNeighbor(set);
        set.addPart(new Part("Part name", "Part line", 0));
        set.setCard(card);
        set.getCard().addPart(new Part("Card part name", "Part line", 6));
        // TODO: END TESTING RELATIONS

        // TODO: Get room data from XML
        linkRooms();
    }

    /**
     * Establishes all neighbor connections
     * between the Rooms of the game, using
     * information from the 'boards' XML file.
     */
    private void linkRooms() {
        // TODO: Establish all neighbor relations
    }

    /**
     * Gets the ArrayList of Cards, used to
     * determine the movie on a particular Set.
     *
     * @return the ArrayList of Cards for this Board.
     * @see ArrayList
     */
    public ArrayList<Card> getDeck() {
        return deck;
    }

    /**
     * Gets the HashMap of Rooms, used to navigate
     * around this Board.
     *
     * @return the HashMap of Rooms for this Board.
     * @see HashMap
     */
    public HashMap<String, Room> getRooms() {
        return rooms;
    }

    /**
     * Gets the direct reference to the 'Trailers'
     * Room for when Player positions are reset at
     * the end of each day in the game.
     *
     * @return the 'Trailers' Room on this Board.
     * @see Room
     */
    public Room getTrailers() {
        return trailers;
    }

    /**
     * Gets the direct reference to the 'Casting
     * Office' Room to check if a Player can upgrade
     * their rank.
     *
     * @return the 'Casting Office' Room on this Board.
     * @see Room
     */
    public Room getCastingOffice() {
        return castingOffice;
    }
}