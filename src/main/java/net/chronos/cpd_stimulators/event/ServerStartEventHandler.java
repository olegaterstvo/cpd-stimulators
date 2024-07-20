package net.chronos.cpd_stimulators.event;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

@EventBusSubscriber(modid = CPDStimulators.MOD_ID)
public class ServerStartEventHandler {
    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event){
        CPDStimulators.LOGGER.info("SERVER STARTED");

    }
}
