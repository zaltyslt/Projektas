package lt.techin.schedule.utils;

import java.util.Random;

public class RandomUtils {
    public static final String randomNumber(int length) {
        int min = (int) Math.pow(10, length - 1);
        int max = (int) Math.pow(10, length); // bound is exclusive
        Random random = new Random();
        return Integer.toString(random.nextInt(max - min) + min);
    }

    public static final String randomString(int length) {
        int min = 'a';
        int max = 'z'; // bound is exclusive
        Random random = new Random();
        String result = "";
        for (int i = 0; i < length; i++) {
            result += Character.toString(random.nextInt(max - min) + min);
        }
        return result;
    }
}
