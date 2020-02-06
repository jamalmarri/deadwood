public class Part {
    private final String name;
    private final String line;
    private final int level;
    private Player playerOnPart;

    public Part(String name, String line, int level) {
        this.name = name;
        this.line = line;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public String getLine() {
        return line;
    }

    public int getLevel() {
        return level;
    }

    public Player getPlayerOnPart() {
        return playerOnPart;
    }

    public void setPlayerOnPart(Player playerOnPart) {
        this.playerOnPart = playerOnPart;
    }
}
