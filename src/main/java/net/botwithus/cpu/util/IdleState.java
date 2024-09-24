package net.botwithus.cpu.util;

import java.util.function.Function;
import java.util.function.Supplier;

import net.botwithus.api.util.collection.Pair;
import net.botwithus.rs3.game.Client;
import net.botwithus.rs3.script.Execution;
import net.botwithus.rs3.script.LoopingScript;

public class IdleState implements BotState {

    private final Supplier<Integer> delayFactory;
    private final Function<LoopingScript, Pair<Boolean, BotState>> shouldIdle;

    public IdleState(Supplier<Integer> delayFactory) {
        this(delayFactory, (script) -> new Pair<>(false, null));
    }

    public IdleState(Supplier<Integer> delayFactory, Function<LoopingScript, Pair<Boolean, BotState>> shouldIdle) {
        this.delayFactory = delayFactory;
        this.shouldIdle = shouldIdle;
    }

    @Override
    public BotState process(LoopingScript script) {
        CustomLogger.log("Idling for a bit...");
        Execution.delay(delayFactory.get());
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
