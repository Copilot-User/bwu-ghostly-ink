package net.botwithus.cpu.inkmedaddy.states;

import net.botwithus.api.util.collection.Pair;
import net.botwithus.cpu.inkmedaddy.Constants;
import net.botwithus.cpu.util.BotState;
import net.botwithus.cpu.util.FuzzyRandom;
import net.botwithus.cpu.util.IdleState;
import net.botwithus.rs3.script.LoopingScript;

public class WaitUntilInkCraftedState implements BotState {

    @Override
    public BotState process(LoopingScript script) {
        var waitRange = FuzzyRandom.shortWaitRange();
        return new IdleState(waitRange.getLeft(), waitRange.getRight(),
                (s) -> new Pair<>(Constants.containsIngredients(), Constants.bankStateFactory.get()));
    }

}
