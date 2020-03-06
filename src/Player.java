/**
 * Represents a Player who is playing the game, including their position, inventory, and status.
 *
 * @author Jamal Marri
 * @author Megan Theimer
 * @see Room
 * @see Part
 */
public class Player {
    /** The path to the image of this Player's dice to display on the Screen. */
    private final String pathToDice;

    /** The current Room that this Player is in. */
    private Room currentRoom;

    /** The amount of dollars this Player has. */
    private int dollars;

    /** The amount of credits this Player has. */
    private int credits;

    /** This Player's current rank. */
    private int rank;

    /** Whether or not this Player has moved during this turn. */
    private boolean hasMoved;

    /** Whether or not this Player has upgraded their rank during this turn. */
    private boolean hasUpgraded;

    /** Whether or not the game is still waiting for this Player to make an action this turn. */
    private boolean waitingForAction;

    /** Whether or not this Player is currently acting on a Part. */
    private boolean isActing;

    /** Whether or not this Player is currently acting on a Part contained in a Card. */
    private boolean isOnCard;

    /** The current Part that this Player is associated with. */
    private Part currentPart;

    /** The number of times this Player has rehearsed for their current Part. */
    private int timesRehearsedThisScene;

    /**
     * Default constructor used when no special rules apply to a game.
     *
     * @param playerNumber this Player's number, used to determine their dice color
     */
    public Player(int playerNumber) {
        // Execute parameterised constructor with default values
        this(playerNumber, 0, 1);
    }

    /**
     * Parameterised class constructor used when special rules apply to a game.
     *
     * @param playerNumber this Player's number, used to determine their dice color
     * @param credits the number of credits this Player will start with
     * @param rank the rank this Player will start at
     */
    public Player(int playerNumber, int credits, int rank) {
        // Assign this Player's dice color based on what their number is
        switch (playerNumber) {
            case 0:
                pathToDice = "img/dice/red/";
                break;
            case 1:
                pathToDice = "img/dice/blue/";
                break;
            case 2:
                pathToDice = "img/dice/green/";
                break;
            case 3:
                pathToDice = "img/dice/yellow/";
                break;
            case 4:
                pathToDice = "img/dice/violet/";
                break;
            case 5:
                pathToDice = "img/dice/pink/";
                break;
            case 6:
                pathToDice = "img/dice/orange/";
                break;
            case 7:
                pathToDice = "img/dice/cyan/";
                break;
            default:
                pathToDice = "img/dice/white/";
                break;
        }
        this.credits = credits;
        this.rank = rank;
    }

    /**
     * Gets the path to the image of this Player's dice.
     *
     * @return the path to the image of this Player's dice
     */
    public String getPathToDice() {
        return pathToDice;
    }

    /**
     * Gets this Player's current Room.
     *
     * @return the Room that this Player is currently in
     * @see Room
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Sets the Room that this Player is now in.
     *
     * @param currentRoom the Room that this Player is now in
     * @see Room
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     * Gets the number of dollars this Player currently has.
     *
     * @return the number of dollars this Player has
     */
    public int getDollars() {
        return dollars;
    }

    /**
     * Sets the number of dollars this Player has.
     *
     * @param dollars the new amount of dollars this Player will have
     */
    public void setDollars(int dollars) {
        this.dollars = dollars;
    }

    /**
     * Gets the number of credits this Player currently has.
     *
     * @return the number of credits this Player has
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Sets the number of credits this Player has.
     *
     * @param credits the new amount of credits this Player will have
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * Gets this Players current rank.
     *
     * @return this Player's current rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * Sets this Player's rank.
     *
     * @param rank this Player's new rank
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * Gets whether or not this Player has moved during this turn.
     *
     * @return the boolean representing whether or not this Player has moved during this turn
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Sets whether or not this Player has moved during this turn.
     *
     * @param hasMoved the boolean representing whether or not this Player has moved during this
     *     turn
     */
    public void setMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * Gets whether or not this Player has upgraded their rank during this turn.
     *
     * @return the boolean that represents whether or not this Player has upgraded their rank during
     *     this turn
     */
    public boolean hasUpgraded() {
        return hasUpgraded;
    }

    /**
     * Sets whether or not this Player has upgraded their rank during this turn.
     *
     * @param hasUpgraded the boolean that represents whether or not this Player has upgraded their
     *     rank during this turn
     */
    public void setUpgraded(boolean hasUpgraded) {
        this.hasUpgraded = hasUpgraded;
    }

    /**
     * Gets whether or not this Player has performed an action during this turn.
     *
     * @return the inverted boolean that represents whether or not this Player is waiting for an
     *     action to be performed
     */
    public boolean isNotWaitingForAction() {
        return !waitingForAction;
    }

    /**
     * Sets whether or not this Player has performed an action during this turn.
     *
     * @param waitingForAction the boolean that represents whether this Player is waiting for an
     *     action to be performed
     */
    public void setWaitingForAction(boolean waitingForAction) {
        this.waitingForAction = waitingForAction;
    }

    /**
     * Gets whether or not this Player is currently acting.
     *
     * @return whether or not this Player is acting
     */
    public boolean isActing() {
        return isActing;
    }

    /**
     * Sets whether or not this Player is currently acting.
     *
     * @param acting whether or not this Player is acting
     */
    public void setActing(boolean acting) {
        isActing = acting;
    }

    /**
     * Gets whether or not this Player has a Part that is on a Card.
     *
     * @return whether or not this Player has a Part that is on a Card
     */
    public boolean isOnCard() {
        return isOnCard;
    }

    /**
     * Sets whether or not this Player has a Part that is on a Card.
     *
     * @param onCard whether or not this Player has a Part that is on a Card
     */
    public void setOnCard(boolean onCard) {
        isOnCard = onCard;
    }

    /**
     * Gets this Player's current Part.
     *
     * @return this Player's current Part
     * @see Part
     */
    public Part getCurrentPart() {
        return currentPart;
    }

    /**
     * Sets this Player's current Part.
     *
     * @param currentPart the new Part for this Player
     * @see Part
     */
    public void setCurrentPart(Part currentPart) {
        this.currentPart = currentPart;
    }

    /**
     * Gets the number of times this Player has rehearsed during the current scene.
     *
     * @return the number of times this Player has rehearsed during the current scene
     */
    public int getTimesRehearsedThisScene() {
        return timesRehearsedThisScene;
    }

    /**
     * Sets the new number of times this Player has rehearsed during the current scene.
     *
     * @param timesRehearsedThisScene the new number of time this Player has rehearsed during the
     *     current scene
     */
    public void setTimesRehearsedThisScene(int timesRehearsedThisScene) {
        this.timesRehearsedThisScene = timesRehearsedThisScene;
    }

    /**
     * Gets this Player's current score, which is the sum of 5 times their rank, their dollars, and
     * their credits.
     *
     * @return this Player's current score based off of their rank, dollars, and credits
     */
    public int getScore() {
        return (rank * 5) + dollars + credits;
    }
}
