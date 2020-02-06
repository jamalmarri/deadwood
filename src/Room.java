import java.util.HashSet;

public class Room {
    private final String name;
    private final HashSet<Room> neighbors;

    public Room(String name, HashSet<Room> neighbors) {
        this.name = name;
        this.neighbors = neighbors;
    }

    public String getName() {
        return name;
    }

    public HashSet<Room> getNeighbors() {
        return neighbors;
    }
}
