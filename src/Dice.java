import java.util.Random;

/**
 * A utility class that provides "dice-like" functionality to a game needing a variable number of
 * Dice.
 *
 * @author Jamal Marri
 * @author Megan Theimer
 * @see Random
 */
public class Dice {
    /** Whether or not the Dice object has been initialized. */
    private static boolean objectExists = false;

    /** The one Dice object for this Singleton class. */
    private static Dice dice;

    /** The Random object this Dice uses to generate pseudo-random integers. */
    private final Random r;

    /** Class constructor. */
    private Dice() {
        r = new Random();
    }

    /**
     * Ensures exactly one Dice exists and returns it.
     *
     * @return the Dice object
     */
    public static Dice getInstance() {
        // Check if a Dice object already exists
        if (!objectExists) {
            // Create a new Dice object if one doesn't already exist
            dice = new Dice();
            objectExists = true;
        }
        return dice;
    }

    /**
     * Generates a pseudo-random integer between 1 and 6 a specified amount of times.
     *
     * @param amountToRoll the number of rolls to perform
     * @return the resulting rolls in the form of an integer array
     */
    public int[] roll(int amountToRoll) {
        // Declare and initialize new integer array of rolls
        int[] rolls = new int[amountToRoll];
        // For every integer in the rolls array, generate a pseudo-random integer between 1 and 6
        for (int i = 0; i < rolls.length; i++) {
            rolls[i] = r.nextInt(6) + 1;
        }
        return rolls;
    }
}
