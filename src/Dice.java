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
public class Dice {
    private static boolean objectExists = false;
    private static Dice dice;
    private static Random r;

    private Dice() {
        r = new Random();
    }

    public static Dice getInstance() {
        if (!objectExists) {
            dice = new Dice();
            objectExists = true;
        }
        return dice;
    }

    /**
     * Generates a random integer between 1 and 6 a specified
     * amount of times.
     *
     * @param amountToRoll the number of rolls to perform.
     * @return the resulting rolls in the form of an integer array.
     */
    public int[] roll(int amountToRoll) {
        int[] rolls = new int[amountToRoll];
        for (int i = 0; i < rolls.length; i++) {
            rolls[i] = r.nextInt(6) + 1;
        }
        return rolls;
    }
}
