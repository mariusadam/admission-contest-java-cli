package helper.generator;

import java.util.Random;

/**
 * Created by marius on 11/6/16.
 */
public class RandomGenerator {

    private static Random random = new Random();
    public static final int DEFAULT_MAX_STRING_LENGTH = 20;
    public static final int DEFAULT_MIN_STRING_LENGTH = 5;

    public static String getRandomString() {
        return getRandomString(DEFAULT_MIN_STRING_LENGTH + Math.abs(getRandomInt(DEFAULT_MAX_STRING_LENGTH)));
    }

    public static String getRandomString(int length) {
        char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toUpperCase().toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars[random.nextInt(chars.length)]);
        }

        return sb.toString();
    }

    public static Integer getRandomInt(int upperBound) {
        return random.nextInt(upperBound);
    }

    public static Integer getRandomInt() {
        return random.nextInt();
    }

    public static Integer getRandomPositiveInt() {
        return Math.abs(random.nextInt());
    }
}
