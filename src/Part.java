/**
 * Represents a Part that the Player plays,
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
     * @param name  the name of this Part, to be displayed to the user.
     * @param line  the line the Player will 'speak', to be displayed to the user.
     * @param level the minimum level or 'rank' the Player must be to attain this Part.
     */
    public Part(String name, String line, int level) {
        this.name = name;
        this.line = line;
        this.level = level;
    }

    /**
     * Gets the name of this Part for displaying to the user.
     *
     * @return the name of this Part.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the line of this Part for displaying to the user.
     *
     * @return the line for this Part.
     */
    public String getLine() {
        return line;
    }

    /**
     * Gets the level of this Part for verifying the Player meets the minimum rank requirement.
     *
     * @return the level requirement for this Part.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the Player object currently occupying this Part, if there is one.
     * This can be helpful for ensuring two Players can't get the same Part.
     *
     * @return the Player object associated with this Part.
     * @see Player
     */
    public Player getPlayerOnPart() {
        return playerOnPart;
    }

    /**
     * Sets the Player that is occupying this Part, for the use of ensuring
     * no other Player can attain it, and for any other use of referencing
     * the Player object.
     *
     * @param playerOnPart the Players object to be assigned to this Part.
     * @see Player
     */
    public void setPlayerOnPart(Player playerOnPart) {
        this.playerOnPart = playerOnPart;
    }
}
