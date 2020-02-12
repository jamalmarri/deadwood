import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Board {
    private final ArrayList<Card> deck = new ArrayList<>();
    private final HashMap<String, Room> rooms = new HashMap<>();
    private final Room trailers = new Room("Trailers", new HashSet<>());
    private final Room castingOffice = new Room("Casting Office", new HashSet<>());

    public Board() {
        // Initialize deck
        initCards();
        // Initialize rooms
        initRooms();
    }

    private void initCards() {
        // TODO: Get card data from XML
        Collections.shuffle(deck);
    }

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

    private void linkRooms() {
        // TODO: Establish all neighbor relations
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public HashMap<String, Room> getRooms() {
        return rooms;
    }

    public Room getTrailers() {
        return trailers;
    }

    public Room getCastingOffice() {
        return castingOffice;
    }
}