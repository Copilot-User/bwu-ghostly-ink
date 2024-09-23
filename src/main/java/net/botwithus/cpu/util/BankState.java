package net.botwithus.cpu.util;

import java.util.function.Supplier;

import net.botwithus.api.game.hud.inventories.Bank;
import net.botwithus.rs3.script.Execution;
import net.botwithus.rs3.script.LoopingScript;

public class BankState implements BotState {

    private static long lastBankOpenTime = 0;

    private final int maxAttempts = 3;
    private final int attemptNumber;
    private final Supplier<BotState> onSuccess;
    private final Supplier<BotState> onFailure;

    public BankState(Supplier<BotState> onSuccess) {
        this(0, onSuccess, () -> null);
    }

    public BankState(Supplier<BotState> onSuccess, Supplier<BotState> onFailure) {
        this(0, onSuccess, onFailure);
    }

    private BankState(int attemptNumber, Supplier<BotState> onSuccess, Supplier<BotState> onFailure) {
        this.attemptNumber = attemptNumber;
        this.onSuccess = onSuccess;
        this.onFailure = onFailure;
    }

    @Override
    public BotState process(LoopingScript script) {
        if (System.currentTimeMillis() - lastBankOpenTime < 1000) {
            CustomLogger.log("Bank is still open. Skipping...");
            return this;
        }

        if (attemptNumber > maxAttempts) {
            return onFailure.get();
        }

        CustomLogger.log("Attempting to load last preset...");
        lastBankOpenTime = System.currentTimeMillis();
        if (!Bank.loadLastPreset()) {
            CustomLogger.log("Failed to load last preset. Retrying...");
            Execution.delay(FuzzyRandom.longWait());
            return new BankState(attemptNumber + 1, this.onSuccess, this.onFailure);
        }

        CustomLogger
                .log("Successfully loaded last preset. Moving on to: " + onSuccess.get().getClass().getSimpleName());
        return onSuccess.get();
    }
}
