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
    private final HashSet<Player> players;

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
        players = new HashSet<>();
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

    /**
     * Gets the HashSet of this Room's players, for checking
     * what Players are currently in this Room.
     *
     * @return the HashSet of Players for this Room
     * @see HashSet
     */
    public HashSet<Player> getPlayers() {
        return players;
    }

    /**
     * Adds the inputted Room into the 'neighbors' HashSet, for when
     * the game connects all Rooms together.
     *
     * @param neighbor the new Room to add to the 'neighbors' HashSet.
     */
    public void addNeighbor(Room neighbor) {
        neighbors.add(neighbor);
    }

    /**
     * Adds the inputted Player into the 'players' HashSet, for when
     * a Player enters this Room.
     *
     * @param player the new Player to add to the 'players' HashSet.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Removes the inputted Player from the 'players' HashSet, for when
     * a Player leaves this Room.
     *
     * @param player the Player to remove from the 'players' HashSet.
     */
    public void removePlayer(Player player) {
        players.remove(player);
    }
}
