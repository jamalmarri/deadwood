/**
 * Represents a Player who is playing the game,
 * including their position, inventory, and status.
 *
 * @author Jamal Marri
 * @author Megan Theimer
 * @version 1.0
 * @see Room
 */
public class Player {
    private final String pathToDice;
    private Room currentRoom;
    private int dollars;
    private int credits;
    private int rank;
    private boolean hasMoved;
    private boolean waitingForAction;
    private boolean isActing;
    private boolean isOnCard;
    private Part currentPart;
    private int timesRehearsedThisScene;

    /**
     * Default constructor that initializes a Player
     * in a situation where no special rules apply.
     */
    public Player(int playerNumber) {
        this(playerNumber, 0, 1);
    }

    /**
     * Class constructor with inputted starter credits
     * and rank, used when special rules apply to a game.
     *
     * @param credits the number of credits this Player will start with.
     * @param rank    the rank this Player will start at.
     */
    public Player(int playerNumber, int credits, int rank) {
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

    public String getPathToDice() {
        return pathToDice;
    }

    /**
     * Gets this Player's current Room, used to determine what actions
     * this Player can take at the current time.
     *
     * @return the Room that this Player is currently in.
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Sets the Room that this Player is now in,
     * used when this Player moves between Rooms.
     *
     * @param currentRoom the Room that this Player is now in.
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     * Gets the number of dollars this Player currently has,
     * used for updating that amount.
     *
     * @return the number of dollars this Player has.
     */
    public int getDollars() {
        return dollars;
    }

    /**
     * Sets the number of dollars this Player has, used when
     * this Player completes a shot or is involved in a bonus
     * payout.
     *
     * @param dollars the new amount of dollars this Player will have.
     */
    public void setDollars(int dollars) {
        this.dollars = dollars;
    }

    /**
     * Gets the number of credits this Player currently has,
     * used for updating that amount.
     *
     * @return the number of credits this Player has.
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Sets the number of credits this Player has, used when
     * this Player completes a shot.
     *
     * @param credits the new amount of dollars this Player will have.
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * Gets this Players current rank, used when upgrading that rank
     * or when checking if this Player can take a certain Part.
     *
     * @return this Player's current rank.
     */
    public int getRank() {
        return rank;
    }

    /**
     * Sets this Player's rank, used during upgrading.
     *
     * @param rank this Player's new rank.
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * Gets whether or not this Player has already performed an action during this turn,
     * used to ensure this Player doesn't perform more than one action.
     *
     * @return the boolean that represents whether this Player is waiting for an action to be performed.
     */
    public boolean isNotWaitingForAction() {
        return !waitingForAction;
    }

    /**
     * Sets whether or not this Player has already performed an action during this turn,
     * used after this Player has performed an action in the game.
     *
     * @param waitingForAction the boolean that represents whether this Player is waiting for an action to be performed.
     */
    public void setWaitingForAction(boolean waitingForAction) {
        this.waitingForAction = waitingForAction;
    }

    /**
     * Gets whether or not this Player is currently acting, used
     * to determine what this Player can do at the current time.
     *
     * @return whether or not this Player is acting.
     */
    public boolean isActing() {
        return isActing;
    }

    /**
     * Sets whether or not this Player is currently acting, used
     * when this Player starts or ends a scene.
     *
     * @param acting whether or not this Player is acting.
     */
    public void setActing(boolean acting) {
        isActing = acting;
    }

    /**
     * Gets whether or not this Player has a Part that is on a
     * Card, used when determining their reward for a successful
     * shot and whether or not a bonus payout will apply at the end
     * of the scene.
     *
     * @return whether or not this Player has a Part that is on a Card.
     */
    public boolean isOnCard() {
        return isOnCard;
    }

    /**
     * Sets whether or not this Player has a Part that is on a Card, used when
     * a Player takes a Part.
     *
     * @param onCard whether or not this Player has a Part that is on a Card.
     */
    public void setOnCard(boolean onCard) {
        isOnCard = onCard;
    }

    /**
     * Gets this Player's current Part, used to determine what their
     * payout will be for successfully acting and to check for a bonus
     * payout.
     *
     * @return this Player's current Part.
     */
    public Part getCurrentPart() {
        return currentPart;
    }

    /**
     * Sets this Player's current Part, used when the Player takes a new
     * Part from a Set or Card.
     *
     * @param currentPart the new Part for this Player.
     */
    public void setCurrentPart(Part currentPart) {
        this.currentPart = currentPart;
    }

    /**
     * Gets the number of times this Player has rehearsed during the current scene,
     * used for applying the rehearsal bonus while attempting to act.
     *
     * @return the number of times this Player has rehearsed during the current scene.
     */
    public int getTimesRehearsedThisScene() {
        return timesRehearsedThisScene;
    }

    /**
     * Sets the new number of times this Player has rehearsed during the current scene,
     * used when this Player has just rehearsed.
     *
     * @param timesRehearsedThisScene the new number of time this Player has rehearsed during the current scene.
     */
    public void setTimesRehearsedThisScene(int timesRehearsedThisScene) {
        this.timesRehearsedThisScene = timesRehearsedThisScene;
    }

    /**
     * Gets this Player's current score, which is a combination of 5 times their rank,
     * their dollars, and their credits.
     *
     * @return this Player's current score based off of their rank, dollars, and credits.
     */
    public int getScore() {
        return (rank * 5) + dollars + credits;
    }
}
