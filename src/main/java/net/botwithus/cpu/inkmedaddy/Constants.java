package net.botwithus.cpu.inkmedaddy;

import java.util.function.Supplier;
import java.util.regex.Pattern;

import net.botwithus.api.game.hud.inventories.Backpack;
import net.botwithus.api.util.collection.Pair;
import net.botwithus.cpu.inkmedaddy.states.OpenInkCraftingMenuState;
import net.botwithus.cpu.util.BankState;
import net.botwithus.cpu.util.BotState;
import net.botwithus.cpu.util.FuzzyRandom;
import net.botwithus.cpu.util.IdleState;

public class Constants {
    public static final Pattern necroplasmPattern = Pattern.compile(".*necroplasm.*", Pattern.CASE_INSENSITIVE);
    public static final Pattern inkPattern = Pattern.compile(".*ghostly ink.*", Pattern.CASE_INSENSITIVE);
    public static final Pattern ashesPattern = Pattern.compile(".*ashes.*", Pattern.CASE_INSENSITIVE);
    public static final Pattern vialPattern = Pattern.compile(".*vial.*", Pattern.CASE_INSENSITIVE);
    public static final String action = "Craft";

    private static final Supplier<BotState> successfullyBanked = () -> {
        return new IdleState(FuzzyRandom::shortWait,
                (s) -> new Pair<>(!containsIngredients(), new OpenInkCraftingMenuState()));
    };

    public static final Supplier<BankState> bankStateFactory = () -> new BankState(successfullyBanked);

    public static boolean containsIngredients() {
        return Backpack.contains(Constants.ashesPattern) &&
                Backpack.contains(Constants.vialPattern) &&
                Backpack.contains(Constants.necroplasmPattern);
    }
}
