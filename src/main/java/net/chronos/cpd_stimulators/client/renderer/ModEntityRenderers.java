package net.chronos.cpd_stimulators.client.renderer;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.entity.ModEntities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = CPDStimulators.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEntityRenderers {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.SANITAR.get(), SanitarRenderer::new);
    }
}
