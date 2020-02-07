import java.util.HashSet;

/**
 * Represents a generic Room that the Player
 * can be in, and sometimes interact with.
 *
 * @author Jamal Marri
 * @author Megan Theimer
 * @version 1.0
 * @see Set
 */
public class Room {
    private final String name;
    private final HashSet<Room> neighbors;
    private HashSet<Player> players;

    /**
     * Class constructor.
     *
     * @param name      the name of this Room to be displayed to the user.
     * @param neighbors the neighboring Rooms of this Room.
     * @see HashSet
     */
    public Room(String name, HashSet<Room> neighbors) {
        this.name = name;
        this.neighbors = neighbors;
    }

    /**
     * Gets the name of this Room for displaying to the user.
     *
     * @return the name of this Room to be displayed to the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the HashSet of this Room's neighbors, for checking
     * where the Player can move from this current Room.
     *
     * @return the HashSet of neighboring Rooms for this Room
     * @see HashSet
     */
    public HashSet<Room> getNeighbors() {
        return neighbors;
    }

    public HashSet<Player> getPlayers() {
        return players;
    }

    public void setPlayers(HashSet<Player> players) {
        this.players = players;
    }
}
