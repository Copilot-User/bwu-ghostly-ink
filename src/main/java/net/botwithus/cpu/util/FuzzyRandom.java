package net.botwithus.cpu.util;

import java.security.SecureRandom;
import java.util.Random;

public class FuzzyRandom {

    // TODO: Improve this by recording a session and generating a distribution to
    // sample from

    private static final Random rand = new SecureRandom();

    private static int computeStdDev(int plusOrMinus) {
        return plusOrMinus / 4;
    }

    public static int shortWait() {
        int mean = 150;
        int stdDev = computeStdDev(50);
        return (int) Math.round(rand.nextGaussian(mean, stdDev));
    }

    public static int longWait() {
        int mean = 1500;
        int stdDev = computeStdDev(500);
        return (int) Math.round(rand.nextGaussian(mean, stdDev));
    }
}
