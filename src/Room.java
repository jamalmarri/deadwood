import java.util.HashSet;

/**
 * Represents a generic Room that the Player can be in, and sometimes interact with.
 *
 * @author Jamal Marri
 * @author Megan Theimer
 * @see Set
 * @see Player
 */
public class Room {
    /** The name of this Room. */
    private final String name;

    /** The starting x-coordinate for any player on this Room. */
    private final int x;

    /** The starting y-coordinate for any player on this Room. */
    private final int y;

    /** The neighboring Rooms of this Room. */
    private final HashSet<Room> neighbors;

    /** The Players that are on this Room. */
    private final HashSet<Player> players;

    /**
     * Class constructor.
     *
     * @param name the name of this Room
     * @param x the starting x-coordinate for any player on this Room
     * @param y the starting y-coordinate for any player on this Room
     * @param neighbors the neighboring Rooms of this Room
     * @see HashSet
     */
    public Room(String name, int x, int y, HashSet<Room> neighbors) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.neighbors = neighbors;
        players = new HashSet<>();
    }

    /**
     * Gets the name of this Room.
     *
     * @return the name of this Room
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the starting x-coordinate for any player on this Room.
     *
     * @return the starting x-coordinate for any player on this Room
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the starting y-coordinate for any player on this Room.
     *
     * @return the starting y-coordinate for any player on this Room
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the HashSet of this Room's neighbors.
     *
     * @return the HashSet of neighboring Rooms for this Room
     * @see HashSet
     */
    public HashSet<Room> getNeighbors() {
        return neighbors;
    }

    /**
     * Adds the inputted Room into the 'neighbors' HashSet.
     *
     * @param neighbor the new Room to add to the 'neighbors' HashSet
     */
    public void addNeighbor(Room neighbor) {
        neighbors.add(neighbor);
    }

    /**
     * Gets the HashSet of this Room's players.
     *
     * @return the HashSet of Players for this Room
     * @see HashSet
     * @see Player
     */
    public HashSet<Player> getPlayers() {
        return players;
    }

    /**
     * Adds the inputted Player into the 'players' HashSet.
     *
     * @param player the new Player to add to the 'players' HashSet
     * @see Player
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Removes the inputted Player from the 'players' HashSet.
     *
     * @param player the Player to remove from the 'players' HashSet
     * @see Player
     */
    public void removePlayer(Player player) {
        players.remove(player);
    }
}
