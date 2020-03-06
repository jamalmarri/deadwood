import java.util.HashSet;

/**
 * Represents a Card in the deck for the game. This object is interacted with through the Set that
 * it is currently placed in, and contains its own HashSet of Parts.
 *
 * @author Jamal Marri
 * @author Megan Theimer
 * @see Set
 * @see Part
 */
public class Card {
    /** The name for the movie represented on this Card. */
    private final String name;

    /** The budget for the movie represented on this Card. */
    private final int budget;

    /** The path to the image of this Card to display on the Screen. */
    private final String pathToCard;

    /** The HashSet of Parts that this Card contains. */
    private final HashSet<Part> parts;

    /**
     * Class constructor.
     *
     * @param name the name for the movie represented on this Card
     * @param budget the budget for the movie represented on this Card
     * @param pathToCard the path to the image of this Card to display on the Screen
     * @param parts the HashSet of Parts that this Card contains
     * @see HashSet
     */
    public Card(String name, int budget, String pathToCard, HashSet<Part> parts) {
        this.name = name;
        this.budget = budget;
        this.pathToCard = pathToCard;
        this.parts = parts;
    }

    /**
     * Gets the name for the movie represented on this Card.
     *
     * @return the name of this Card
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the budget for the movie represented on this Card.
     *
     * @return the budget of this Card
     */
    public int getBudget() {
        return budget;
    }

    /**
     * Gets the path to the image of this Card.
     *
     * @return the path to the image of this Card
     */
    public String getPathToCard() {
        return pathToCard;
    }

    /**
     * Gets the HashSet of Part objects associated with this Card.
     *
     * @return the HashSet of Parts that this Card contains
     * @see HashSet
     * @see Part
     */
    public HashSet<Part> getParts() {
        return parts;
    }

    /**
     * Adds a new Part to this Card's 'parts' HashSet.
     *
     * @param part the new Part to be added to the 'parts' HashSet
     * @see Part
     */
    public void addPart(Part part) {
        parts.add(part);
    }
}
