package org.demos;

import java.util.ArrayList;
import java.util.Random;

public class RandomPrinter {

    private static final int VALUES_TO_GENERATE = 1_000;

    public static void main(String[] rags) {
        final Random random = new Random();

        System.out.println("Hello from Java! ☕");

        final var values = new ArrayList<Integer>();
        for (int i = 0; i < VALUES_TO_GENERATE; i++) {
            int randomValue = random.nextInt();
            System.out.println("Here is a random value: " + randomValue);
            values.add(randomValue);
        }
    }
}
