import java.util.HashSet;

/**
 * Represents a Set on the board of the game.
 * There are 10 of these Sets, each with their
 * own name, Card, and Parts that the Player can
 * interact with. This is a child class of the Room
 * class, as it represents a specific type of Room.
 *
 * @author Jamal Marri
 * @author Megan Theimer
 * @version 1.0
 * @see Room
 * @see Card
 */
public class Set extends Room {
    private final int defaultTakes;
    private final HashSet<Part> parts;
    private Card card;
    private int takesLeft;

    /**
     * Class constructor.
     *
     * @param name         the name of this Set to be displayed to the user.
     * @param neighbors    the neighboring Rooms of this Set.
     * @param defaultTakes the default amount of takes for this Set used for resetting takes between movies.
     * @param parts        the HashSet of Parts that this Set contains.
     * @see HashSet
     */
    public Set(String name, HashSet<Room> neighbors, int defaultTakes, HashSet<Part> parts) {
        super(name, neighbors);
        this.defaultTakes = defaultTakes;
        this.parts = parts;
    }

    /**
     * Gets the Card on this Set, to check the star
     * roles that a Player can possibly attain.
     *
     * @return the current Card for this Set.
     */
    public Card getCard() {
        return card;
    }

    /**
     * Sets the Card on this Set, to determine the
     * potential star roles that a Player can take,
     * as well as the name and budget of the movie.
     *
     * @param card the new Card for this Set.
     */
    public void setCard(Card card) {
        this.card = card;
    }

    /**
     * Gets the number of takes left for this Set, to
     * determine when a new Card should be attached to it.
     *
     * @return the number of takes left for this Set.
     */
    public int getTakesLeft() {
        return takesLeft;
    }

    /**
     * Sets the takes that are left on this Set,
     * for when a Player successfully acts.
     *
     * @param takesLeft the new number of takes left on this Set.
     */
    public void setTakesLeft(int takesLeft) {
        this.takesLeft = takesLeft;
    }

    /**
     * Gets the default amount of takes for this Set, which
     * is very useful for resetting the number of takes left
     * at the end of a movie.
     *
     * @return the default number of takes for this Set.
     */
    public int getDefaultTakes() {
        return defaultTakes;
    }

    /**
     * Gets the HashSet of Parts associated with this Set,
     * which is used to find all of the Players associated
     * with them.
     *
     * @return the HashSet of parts associated with this Set.
     * @see HashSet
     */
    public HashSet<Part> getParts() {
        return parts;
    }

    /**
     * Adds a new Part to this Set's 'parts' HashSet, for when
     * the game adds every Set's Parts.
     *
     * @param part the new part to be added to the 'parts' HashSet.
     */
    public void addPart(Part part) {
        parts.add(part);
    }
}
