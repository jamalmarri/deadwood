import java.util.HashSet;

/**
 * Represents a Set on the board of the game.
 * There are 10 of these sets, each with their
 * own name, Card, and parts that the Player can
 * interact with. This is a child of the Room
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
    private Card Card;
    private int takesLeft;

    /**
     * Class constructor.
     *
     * @param name         the name of this Card to be displayed to the user.
     * @param neighbors    the neighboring Rooms of this Card.
     * @param defaultTakes the default amount of takes for this Card for resetting takes between movies.
     * @param parts        the name of this Card to be displayed to the user.
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
        return Card;
    }

    /**
     * Sets the Card on this Set, to determine the
     * potential star roles that a Player can take,
     * as well as the name and budget of the movie.
     *
     * @param Card the new Card for this Set.
     */
    public void setCard(Card Card) {
        this.Card = Card;
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
     * Resets the takes that are left on this Set,
     * usually at the end of a scene on a Card.
     */
    public void resetTakesLeft() {
        this.takesLeft = this.defaultTakes;
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
}
