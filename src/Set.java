import java.util.HashSet;

public class Set extends Room {
    private final int defaultTakes;
    private final HashSet<Part> parts;
    private Card card;
    private int takesLeft;

    public Set(String name, HashSet<Room> neighbors, int defaultTakes, HashSet<Part> parts) {
        super(name, neighbors);
        this.defaultTakes = defaultTakes;
        this.parts = parts;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public int getTakesLeft() {
        return takesLeft;
    }

    public void resetTakesLeft() {
        this.takesLeft = this.defaultTakes;
    }

    public HashSet<Part> getParts() {
        return parts;
    }
}
