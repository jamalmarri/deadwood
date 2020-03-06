/**
 * Represents a Part that the Player plays, either on a Card or on a Set.
 *
 * @author Jamal Marri
 * @author Megan Theimer
 * @see Card
 * @see Set
 * @see Player
 */
public class Part {
    /** The name of this Part. */
    private final String name;

    /** This Part's minimum level or 'rank' a Player must be to attain it. */
    private final int level;

    /** This Part's x-coordinate, sometimes in relation to the Card that contains it. */
    private final int x;

    /** This Part's y-coordinate, sometimes in relation to the Card that contains it. */
    private final int y;

    /** The height and width a Player's dice should be when they are on this Part. */
    private final int diceSize;

    /** The Player who is acting on this Part. */
    private Player playerOnPart;

    /**
     * Class constructor.
     *
     * @param name the name of this Part
     * @param level the minimum level or 'rank' a Player must be to attain this Part
     * @param x this Part's x-coordinate, sometimes in relation to the Card that contains it
     * @param y this Part's y-coordinate, sometimes in relation to the Card that contains it
     * @param diceSize the height and width a Player's dice should be when they are on this Part
     */
    public Part(String name, int level, int x, int y, int diceSize) {
        this.name = name;
        this.level = level;
        this.x = x;
        this.y = y;
        this.diceSize = diceSize;
    }

    /**
     * Gets the name of this Part.
     *
     * @return the name of this Part
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the level of this Part.
     *
     * @return the level of this Part
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets this Part's x-coordinate, sometimes in relation to the Card that contains it.
     *
     * @return the x-coordinate of this Part
     */
    public int getX() {
        return x;
    }

    /**
     * Gets this Part's y-coordinate, sometimes in relation to the Card that contains it.
     *
     * @return the y-coordinate of this Part
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the height and width a Player's dice should be when they are on this Part.
     *
     * @return the dice size
     */
    public int getDiceSize() {
        return diceSize;
    }

    /**
     * Gets the Player object currently occupying this Part.
     *
     * @return the Player object acting on this Part
     * @see Player
     */
    public Player getPlayerOnPart() {
        return playerOnPart;
    }

    /**
     * Sets the Player that is acting on this Part.
     *
     * @param playerOnPart the Players object to be assigned to this Part
     * @see Player
     */
    public void setPlayerOnPart(Player playerOnPart) {
        this.playerOnPart = playerOnPart;
    }
}
