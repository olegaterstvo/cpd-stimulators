package net.chronos.cpd_stimulators.command;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.text.MessageFormat;

@EventBusSubscriber(modid = CPDStimulators.MOD_ID)
public class ClearDeferred {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("cleardeferred").requires(source -> source.hasPermission(1)).executes(arguments -> {
            Player player = arguments.getSource().getPlayer();

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

            return 0;
        }));
    }
}

