package net.botwithus.cpu.inkmedaddy.states;

import net.botwithus.api.util.collection.Pair;
import net.botwithus.cpu.util.BotState;
import net.botwithus.cpu.util.FuzzyRandom;
import net.botwithus.cpu.util.IdleState;
import net.botwithus.rs3.game.hud.interfaces.Interfaces;
import net.botwithus.rs3.game.minimenu.MiniMenu;
import net.botwithus.rs3.game.minimenu.actions.ComponentAction;
import net.botwithus.rs3.script.Execution;
import net.botwithus.rs3.script.LoopingScript;

public class ClickMakeInkState implements BotState {

    @Override
    public BotState process(LoopingScript script) {
        if (MiniMenu.interact(ComponentAction.DIALOGUE.getType(), 0, -1, 89784350)) {
            return new IdleState(FuzzyRandom::shortWait,
                    (s) -> new Pair<>(Interfaces.isOpen(1486) || Interfaces.isOpen(1251),
                            new WaitUntilInkCraftedState()));
        }
        Execution.delay(FuzzyRandom.longWait());
        if (Interfaces.isOpen(1486) || Interfaces.isOpen(1251)) {
            return new OpenInkCraftingMenuState();
        }
        return this;
    }
}
