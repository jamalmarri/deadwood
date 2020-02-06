import java.util.HashSet;

public class Card {
    private final String name;
    private final int sceneNumber;
    private final String description;
    private final int budget;
    private final HashSet<Part> parts;

    public Card(String name, int sceneNumber, String description, int budget, HashSet<Part> parts) {
        this.name = name;
        this.sceneNumber = sceneNumber;
        this.description = description;
        this.budget = budget;
        this.parts = parts;
    }

    public String getName() {
        return name;
    }

    public int getSceneNumber() {
        return sceneNumber;
    }

    public String getDescription() {
        return description;
    }

    public int getBudget() {
        return budget;
    }

    public HashSet<Part> getParts() {
        return parts;
    }
}
