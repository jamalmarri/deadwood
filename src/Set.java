import java.util.HashSet;

/**
 * Represents a Set on the board of the game. There are 10 of these Sets, each with their own name,
 * Card, and Parts that the Player can interact with. This is a child class of the Room class, as it
 * represents a specific type of Room.
 *
 * @author Jamal Marri
 * @author Megan Theimer
 * @see Room
 * @see Part
 * @see Card
 */
public class Set extends Room {
    /** The ID of this Set, used to give its Card a constant index in the Cards Pane. */
    private final int id;

    /** The default amount of takes for this Set. */
    private final int defaultTakes;

    /** The x-coordinate of where this Set's Card will be placed on the Cards Pane. */
    private final int cardX;

    /** The y-coordinate of where this Set's Card will be placed on the Cards Pane. */
    private final int cardY;

    /** The integer array of x-coordinates for all takes on this Set. */
    private final int[] takeXValues;

    /** The integer array of y-coordinates for all takes on this Set. */
    private final int[] takeYValues;

    /** The HashSet of Parts that this Set contains. */
    private final HashSet<Part> parts;

    /** The amount of takes left for this Set's Card. */
    private int takesLeft;

    /** This Set's current Card. */
    private Card card;

    /**
     * Class constructor.
     *
     * @param name the name of this Set
     * @param x the starting x-coordinate for any player on this Set
     * @param y the starting y-coordinate for any player on this Set
     * @param neighbors the neighboring Rooms of this Set
     * @param id the ID of this Set, used to give its Card a constant index in the Cards Pane
     * @param defaultTakes the default amount of takes for this Set
     * @param cardX the x-coordinate of where this Set's Card will be placed on the Cards Pane
     * @param cardY the y-coordinate of where this Set's Card will be placed on the Cards Pane
     * @param takeXValues the integer array of x-coordinates for all takes on this Set
     * @param takeYValues the integer array of y-coordinates for all takes on this Set
     * @param parts the HashSet of Parts that this Set contains
     * @see HashSet
     */
    public Set(
            String name,
            int x,
            int y,
            HashSet<Room> neighbors,
            int id,
            int defaultTakes,
            int cardX,
            int cardY,
            int[] takeXValues,
            int[] takeYValues,
            HashSet<Part> parts) {
        super(name, x, y, neighbors);
        this.id = id;
        this.defaultTakes = defaultTakes;
        this.cardX = cardX;
        this.cardY = cardY;
        this.takeXValues = takeXValues;
        this.takeYValues = takeYValues;
        this.parts = parts;
        this.takesLeft = defaultTakes;
    }

    /**
     * Gets the ID of this Set, which is used to give its current Card a constant index on the Cards
     * Pane.
     *
     * @return this Set's integer ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the default amount of takes for this Set.
     *
     * @return the default number of takes for this Set
     */
    public int getDefaultTakes() {
        return defaultTakes;
    }

    /**
     * Gets the x-coordinate of where a Card should be placed on the Cards Pane.
     *
     * @return the x-coordinate of this Set
     */
    public int getCardX() {
        return cardX;
    }

    /**
     * Gets the y-coordinate of where a Card should be placed on the Cards Pane.
     *
     * @return the y-coordinate of this Set
     */
    public int getCardY() {
        return cardY;
    }

    /**
     * Gets the integer array of x-coordinates where takes should be placed on the Pane of all
     * takes.
     *
     * @return the integer array of x-coordinates
     */
    public int[] getTakeXValues() {
        return takeXValues;
    }

    /**
     * Gets the integer array of y-coordinates where takes should be placed on the Pane of all
     * takes.
     *
     * @return the integer array of y-coordinates
     */
    public int[] getTakeYValues() {
        return takeYValues;
    }

    /**
     * Gets the HashSet of Parts associated with this Set.
     *
     * @return the HashSet of parts associated with this Set
     * @see HashSet
     */
    public HashSet<Part> getParts() {
        return parts;
    }

    /**
     * Adds a new Part to this Set's 'parts' HashSet.
     *
     * @param part the new part to be added to the 'parts' HashSet
     */
    public void addPart(Part part) {
        parts.add(part);
    }

    /**
     * Gets the number of takes left for this Set.
     *
     * @return the number of takes left for this Set
     */
    public int getTakesLeft() {
        return takesLeft;
    }

    /**
     * Sets the takes that are left on this Set.
     *
     * @param takesLeft the new number of takes left on this Set
     */
    public void setTakesLeft(int takesLeft) {
        this.takesLeft = takesLeft;
    }

    /**
     * Gets the Card on this Set.
     *
     * @return the current Card for this Set
     */
    public Card getCard() {
        return card;
    }

    /**
     * Sets the Card on this Set.
     *
     * @param card the new Card for this Set
     */
    public void setCard(Card card) {
        this.card = card;
    }
}
