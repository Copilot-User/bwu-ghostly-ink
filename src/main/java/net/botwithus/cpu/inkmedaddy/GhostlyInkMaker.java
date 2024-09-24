package net.botwithus.cpu.inkmedaddy;

import net.botwithus.api.game.hud.inventories.Backpack;
import net.botwithus.api.util.collection.Pair;
import net.botwithus.cpu.inkmedaddy.states.OpenInkCraftingMenuState;
import net.botwithus.cpu.util.BotState;
import net.botwithus.cpu.util.CustomLogger;
import net.botwithus.cpu.util.FuzzyRandom;
import net.botwithus.cpu.util.IdleState;
import net.botwithus.internal.scripts.ScriptDefinition;
import net.botwithus.rs3.game.Client;
import net.botwithus.rs3.script.LoopingScript;
import net.botwithus.rs3.script.config.ScriptConfig;
import net.botwithus.rs3.script.Execution;

public class GhostlyInkMaker extends LoopingScript {

    public GhostlyInkMaker(String name, ScriptConfig config, ScriptDefinition definition) {
        super(name, config, definition);
        CustomLogger.setScript(this);
    }

    private BotState initialState = new IdleState(FuzzyRandom::longWait, (script) -> {
        if (containsIngredients()) {
            return new Pair<>(false, new OpenInkCraftingMenuState());
        }
        return new Pair<>(false, Constants.bankStateFactory.get());
    });

    private BotState state = initialState;

    private long lastStateChange = System.currentTimeMillis();
    private BotState lastState = state;

    @Override
    public void resume() {
        super.resume();
        CustomLogger.log("Resuming script.");
        this.lastStateChange = System.currentTimeMillis();
        this.state = initialState;
    }

    @Override
    public void onLoop() {
        if (Client.getLocalPlayer() == null) {
            CustomLogger.log("Player is null, waiting.");
            Execution.delay(FuzzyRandom.longWait());
            return;
        }

        if (Client.getLocalPlayer().isMoving()) {
            CustomLogger.log("Player is moving, waiting.");
            Execution.delay(FuzzyRandom.shortWait());
            return;
        }

        if (state == null) {
            CustomLogger.log("State is null, pausing script.");
            this.pause();
            return;
        }

        if (System.currentTimeMillis() - lastStateChange > 120000) {
            CustomLogger.log("State has not changed in 2 minutes, restarting script.");
            state = initialState;
            return;
        }

        try {
            CustomLogger.log("Processing state: " + state.getClass().getSimpleName());
            lastState = state;
            state = state.process(this);

            if (state != lastState) { // referential equality works because we don't generally new up the same state
                lastStateChange = System.currentTimeMillis();
            }
        } catch (Exception e) {
            CustomLogger.log("Error processing state: " + state.getClass().getSimpleName());
            CustomLogger.log(e.getMessage());
            // print out the stack trace using CustomLogger.log()
            for (StackTraceElement element : e.getStackTrace()) {
                CustomLogger.log(element.toString());
            }
        }
    }

    private boolean containsIngredients() {
        return Backpack.contains(Constants.ashesPattern) &&
                Backpack.contains(Constants.vialPattern) &&
                Backpack.contains(Constants.necroplasmPattern);
    }
}
