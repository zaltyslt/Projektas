package utils;

import java.util.Random;

public class RandomUtils {
    public static final String randomString(int length) {
        int min = (int) Math.pow(10, length - 1);
        int max = (int) Math.pow(10, length); // bound is exclusive
        Random random = new Random();
        return Integer.toString(random.nextInt(max - min) + min);
    }
}
