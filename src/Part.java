/**
 * Represents a part that the player plays,
 * either on a Card or on a Set.
 *
 * @author Jamal Marri
 * @author Megan Theimer
 * @version 1.0
 * @see Card
 * @see Set
 */
public class Part {
    private final String name;
    private final String line;
    private final int level;
    private Player playerOnPart;

    /**
     * Class constructor.
     *
     * @param name  the name of this part, to be displayed to the user.
     * @param line  the line the player will 'speak', to be displayed to the user.
     * @param level the minimum level or 'rank' the player must be to attain this part.
     */
    public Part(String name, String line, int level) {
        this.name = name;
        this.line = line;
        this.level = level;
    }

    /**
     * Gets the name of this part for displaying to the user.
     *
     * @return the name of this part.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the line of this part for displaying to the user.
     *
     * @return the line for this part.
     */
    public String getLine() {
        return line;
    }

    /**
     * Gets the level of this part for verifying the player meets the minimum rank requirement.
     *
     * @return the level requirement for this part.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the player object currently occupying this part, if there is one.
     * This can be helpful for ensuring two players can't get the same part.
     *
     * @return the player object associated with this part.
     * @see Player
     */
    public Player getPlayerOnPart() {
        return playerOnPart;
    }

    /**
     * Sets the player that is occupying this part, for the use of ensuring
     * no other player can attain it, and for any other use of referencing
     * the player object.
     *
     * @param playerOnPart the player object to be assigned to this part.
     * @see Player
     */
    public void setPlayerOnPart(Player playerOnPart) {
        this.playerOnPart = playerOnPart;
    }
}
