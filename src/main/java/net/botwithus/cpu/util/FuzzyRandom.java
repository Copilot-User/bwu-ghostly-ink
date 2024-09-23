package net.botwithus.cpu.util;

import java.util.Random;
import net.botwithus.api.util.collection.Pair;

public class FuzzyRandom {

    private static final Random rand = new Random();
    private static int smallFuzzRange = 25;
    private static int largeFuzzRange = 300;
    private static int smallFuzz = resetSmallFuzz();
    private static int largeFuzz = resetLargeFuzz();

    public static Pair<Integer, Integer> shortWaitRange() {
        int lowerBound = 100;
        int upperBound = 200;
        resetSmallFuzz();
        int min1 = Math.min(lowerBound - smallFuzz, lowerBound + smallFuzz);
        int max1 = Math.max(lowerBound - smallFuzz, lowerBound + smallFuzz);
        int min2 = Math.min(upperBound - smallFuzz, upperBound + smallFuzz);
        int max2 = Math.max(upperBound - smallFuzz, upperBound + smallFuzz);
        return new Pair<>(
                rand.nextInt(min1, max1),
                rand.nextInt(min2, max2));
    }

    public static Pair<Integer, Integer> longWaitRange() {
        int lowerBound = 1000;
        int upperBound = 2000;
        resetLargeFuzz();
        int min1 = Math.min(lowerBound - largeFuzz, lowerBound + largeFuzz);
        int max1 = Math.max(lowerBound - largeFuzz, lowerBound + largeFuzz);
        int min2 = Math.min(upperBound - largeFuzz, upperBound + largeFuzz);
        int max2 = Math.max(upperBound - largeFuzz, upperBound + largeFuzz);
        return new Pair<>(
                rand.nextInt(min1, max1),
                rand.nextInt(min2, max2));
    }

    public static int shortWait() {
        int lowerBound = 100;
        int upperBound = 200;
        resetSmallFuzz();
        int min = Math.min(lowerBound + smallFuzz, upperBound + smallFuzz);
        int max = Math.max(lowerBound + smallFuzz, upperBound + smallFuzz);
        return rand.nextInt(min, max);
    }

    public static int longWait() {
        int lowerBound = 1000;
        int upperBound = 2000;
        resetLargeFuzz();
        int min = Math.min(lowerBound + largeFuzz, upperBound + largeFuzz);
        int max = Math.max(lowerBound + largeFuzz, upperBound + largeFuzz);
        return rand.nextInt(min, max);
    }

    public static int smallFuzz(int lowerBound, int upperBound) {
        resetSmallFuzz();
        int min = Math.min(lowerBound + smallFuzz, upperBound + smallFuzz);
        int max = Math.max(lowerBound + smallFuzz, upperBound + smallFuzz);
        return rand.nextInt(min, max);
    }

    public static int largeFuzz(int lowerBound, int upperBound) {
        resetLargeFuzz();
        int min = Math.min(lowerBound + largeFuzz, upperBound + largeFuzz);
        int max = Math.max(lowerBound + largeFuzz, upperBound + largeFuzz);
        return rand.nextInt(min, max);
    }

    public static int resetSmallFuzz() {
        smallFuzz = rand.nextInt(smallFuzzRange);
        return smallFuzz;
    }

    public static int resetLargeFuzz() {
        largeFuzz = rand.nextInt(largeFuzzRange);
        return largeFuzz;
    }
}
