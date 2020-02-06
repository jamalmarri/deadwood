public class Player {
    private Room currentRoom;
    private int dollars;
    private int credits;
    private int rank;
    private int timesRehearsedThisScene;

    public Player() {
        this.dollars = 0;
        this.credits = 0;
        this.rank = 1;
    }

    public Player(int credits, int rank) {
        this.dollars = 0;
        this.credits = credits;
        this.rank = rank;
    }

    public int getDollars() {
        return dollars;
    }

    public void setDollars(int dollars) {
        this.dollars = dollars;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getTimesRehearsedThisScene() {
        return timesRehearsedThisScene;
    }

    public void setTimesRehearsedThisScene(int timesRehearsedThisScene) {
        this.timesRehearsedThisScene = timesRehearsedThisScene;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public int getScore() {
        return (rank * 5) + dollars + credits;
    }
}
