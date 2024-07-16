package net.chronos.cpd_stimulators.procedure;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.text.MessageFormat;

public class ClearDeferredEffects {
    public static void execute(Player player) {
        boolean hasAny = false;
        for (int i = 0; i < 50; i++) {
            if (player.getPersistentData().contains("deferred_" + i)) {
                player.getPersistentData().remove("deferred_" + i);
                hasAny = true;
            }
        }
        if (hasAny){
            player.sendSystemMessage(Component.literal(MessageFormat.format("Removed every deferred effect from {0}", player.getName().getString())));
        } else {
            player.sendSystemMessage(Component.literal("§cTarget has no deferred effects to remove"));
        }
    }
}

