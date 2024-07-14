package net.chronos.cpd_stimulators.command;


import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.procedure.ClearDeferredEffects;
import net.minecraft.commands.Commands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.Objects;

@EventBusSubscriber(modid = CPDStimulators.MOD_ID)
public class ClearDeferred {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("cleardeferred").executes(arguments -> {
            ClearDeferredEffects.execute(Objects.requireNonNull(arguments.getSource().getPlayer()));
            return 0;
        }));
    }
}

