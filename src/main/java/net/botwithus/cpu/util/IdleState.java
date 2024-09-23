package net.botwithus.cpu.util;

import java.util.Random;
import java.util.function.Function;

import net.botwithus.api.util.collection.Pair;
import net.botwithus.rs3.game.Client;
import net.botwithus.rs3.script.Execution;
import net.botwithus.rs3.script.LoopingScript;

public class IdleState implements BotState {

    private final int lowerBound;
    private final int upperBound;
    private final Function<LoopingScript, Pair<Boolean, BotState>> shouldIdle;
    private final Random rand = new Random();

    public IdleState(int lowerBound, int upperBound) {
        this(lowerBound, upperBound, (script) -> new Pair<>(false, null));
    }

    public IdleState(int lowerBound, int upperBound,
            Function<LoopingScript, Pair<Boolean, BotState>> shouldIdle) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.shouldIdle = shouldIdle;
    }

    @Override
    public BotState process(LoopingScript script) {
        CustomLogger.log("Idling for a bit...");
        Execution.delay(rand.nextInt(lowerBound, upperBound));
        var notLoaded = Client.getLocalPlayer() == null;
        if (notLoaded) {
            return this;
        }

        var pair = shouldIdle.apply(script);
        CustomLogger.log("Should idle: " + pair.getLeft());
        CustomLogger.log("Next state: " + pair.getRight().getClass().getSimpleName());
        if (pair.getLeft()) {
            return this;
        }
        return pair.getRight();
    }
}
