import java.util.HashSet;

/**
 * Represents a Card in the deck for the game.
 * Usually, this object is interacted with through
 * the Set that it is currently associated with,
 * if applicable.
 *
 * @author Jamal Marri
 * @author Megan Theimer
 * @version 1.0
 * @see Set
 */
public class Card {
    private final String name;
    private final int sceneNumber;
    private final String description;
    private final int budget;
    private final HashSet<Part> parts;

    /**
     * Class constructor.
     *
     * @param name        the name of this Card to be displayed to the user.
     * @param sceneNumber the scene number of this Card to be displayed to the user.
     * @param description the description of this Card to be displayed to the user.
     * @param budget      the budget of this Card to be used for acting and bonus payouts.
     * @param parts       the HashSet of parts that this Card contains.
     */
    public Card(String name, int sceneNumber, String description, int budget, HashSet<Part> parts) {
        this.name = name;
        this.sceneNumber = sceneNumber;
        this.description = description;
        this.budget = budget;
        this.parts = parts;
    }

    /**
     * Gets the name of this Card for displaying to the user.
     *
     * @return the name of this Card to be displayed to the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the scene number of this Card for displaying to the user.
     *
     * @return the scene number of this Card to be displayed to the user.
     */
    public int getSceneNumber() {
        return sceneNumber;
    }

    /**
     * Gets the description of this Card for displaying to the user.
     *
     * @return the description of this Card to be displayed to the user.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the budget of this Card for verifying if the Player has
     * successfully acted on their part, and to determine the amount
     * of dice to roll in the event of a bonus payout.
     *
     * @return the budget of the Card.
     */
    public int getBudget() {
        return budget;
    }

    /**
     * Gets the HashSet of part objects associated with the Card.
     * This is useful for determining all of the Players who are
     * applicable to a bonus payout.
     *
     * @return the HashSet of parts that the Card contains.
     * @see HashSet
     */
    public HashSet<Part> getParts() {
        return parts;
    }
}
