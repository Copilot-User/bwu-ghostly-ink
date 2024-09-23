package net.botwithus.cpu.util;

import net.botwithus.rs3.script.Script;

public class CustomLogger {
    private static Script script;
    private static boolean debug = true;

    public static void setScript(Script script) {
        CustomLogger.script = script;
    }

    public static void log(String message) {
        if (debug) {
            script.println(message);
        }
    }
}
