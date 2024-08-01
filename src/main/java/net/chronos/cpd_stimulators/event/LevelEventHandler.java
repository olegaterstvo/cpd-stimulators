package net.chronos.cpd_stimulators.event;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.minecraft.core.particles.ParticleTypes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = CPDStimulators.MOD_ID)
public class LevelEventHandler {
    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Pre event){
        // Spawn particles for airdrop
        if(ServerStartEventHandler.blockPos != null) {
            if (Math.random() > 0.5f) {
                event.getLevel().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        ServerStartEventHandler.blockPos.getX() + 0.5f,
                        ServerStartEventHandler.blockPos.getY() + 0.5f,
                        ServerStartEventHandler.blockPos.getZ() + 0.5f,
                        0.03, 0.07, 0.02);
            }
        }
    }
}
