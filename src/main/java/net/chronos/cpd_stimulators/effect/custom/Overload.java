package net.chronos.cpd_stimulators.effect.custom;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.effect.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;

@EventBusSubscriber(modid = CPDStimulators.MOD_ID)
public class Overload extends MobEffect {
    public Overload(MobEffectCategory category, int color) {
        super(category, color);
    }

    private static float MAX_WEIGHT = 27648f * 1.75f;

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onMovementInputUpdate(MovementInputUpdateEvent event) {
        if (!event.getEntity().hasEffect(ModEffects.OVERLOAD.getDelegate())) return;

        float overload_amplifier = event.getEntity().getPersistentData().getFloat("overload_amplifier");
        event.getInput().forwardImpulse = event.getInput().forwardImpulse * (1 - overload_amplifier / MAX_WEIGHT);
        event.getInput().leftImpulse = event.getInput().leftImpulse * (1 - overload_amplifier / MAX_WEIGHT);
    }
}