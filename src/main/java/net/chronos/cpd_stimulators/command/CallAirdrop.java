package net.chronos.cpd_stimulators.command;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.event.ServerStartEventHandler;
import net.minecraft.commands.Commands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = CPDStimulators.MOD_ID)
public class CallAirdrop {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("callairdrop").requires(source -> source.hasPermission(1)).executes(arguments -> {
            ServerStartEventHandler.executeTask(arguments.getSource().getServer());
            return 0;
        }));
    }
}

