package net.botwithus.cpu.util;

import net.botwithus.rs3.script.LoopingScript;

public interface BotState {
    BotState process(LoopingScript script);
}
