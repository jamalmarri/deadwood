import java.util.Random;

/**
 * A utility class that provides "dice-like" functionality
 * to a game needing a variable number of Dice.
 *
 * @author Jamal Marri
 * @author Megan Theimer
 * @version 1.0
 * @see Random
 */
public final class Dice {
    private static final Random r = new Random();

    private Dice() {
    }

    /**
     * Generates a random integer between 1 and 6 a specified
     * amount of times.
     *
     * @param amountToRoll the number of rolls to perform.
     * @return the resulting rolls in the form of an integer array.
     */
    public static int[] roll(int amountToRoll) {
        int[] rolls = new int[amountToRoll];
        for (int i = 0; i < rolls.length; i++) {
            rolls[i] = r.nextInt(6) + 1;
        }
        return rolls;
    }
}
