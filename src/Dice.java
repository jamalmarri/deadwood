import java.util.Random;

public final class Dice {
    private static final Random r = new Random();

    private Dice() {
    }

    public static int[] roll(int amountToRoll) {
        int[] rolls = new int[amountToRoll];
        for (int i = 0; i < rolls.length; i++) {
            rolls[i] = r.nextInt(6) + 1;
            System.out.println(rolls[i]);
        }
        return rolls;
    }
}
