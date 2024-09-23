package net.botwithus.cpu.inkmedaddy.states;

import java.util.function.Supplier;

import net.botwithus.api.game.hud.inventories.Backpack;
import net.botwithus.api.util.collection.Pair;
import net.botwithus.cpu.inkmedaddy.Constants;
import net.botwithus.cpu.util.BotState;
import net.botwithus.cpu.util.CustomLogger;
import net.botwithus.cpu.util.FuzzyRandom;
import net.botwithus.cpu.util.IdleState;
import net.botwithus.rs3.game.hud.interfaces.Interfaces;
import net.botwithus.rs3.script.Execution;
import net.botwithus.rs3.script.LoopingScript;

public class OpenInkCraftingMenuState implements BotState {

    @Override
    public BotState process(LoopingScript script) {
        CustomLogger.setScript(script);

        Supplier<Boolean> shouldProceed = () -> !Interfaces.isOpen(1371);

        if (Backpack.interact(Constants.necroplasmPattern, Constants.action)) {
            var waitRange = FuzzyRandom.shortWaitRange();
            return new IdleState(waitRange.getLeft(), waitRange.getRight(),
                    (s) -> new Pair<>(shouldProceed.get(), new ClickMakeInkState()));

        }
        Execution.delay(FuzzyRandom.longWait());
        if (shouldProceed.get()) {
            return new ClickMakeInkState();
        }
        return Constants.bankStateFactory.get();
    }
}
