package net.chronos.cpd_stimulators.procedure;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.minecraft.world.entity.player.Player;

public class ClearDeferredEffects {
    public static void execute(Player player) {
        for (int i = 0; i < player.getPersistentData().size(); i++) {
            if (player.getPersistentData().contains("deferred_" + i)) {
                player.getPersistentData().remove("deferred_" + i);
            };
        }
    }
}

