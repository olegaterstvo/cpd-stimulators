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
    public Overload(MobEffectCategory category, int color) { super(category, color); }

    private static float MAX_WEIGHT = 27648f;
    public static float STOP_SPRINTING_THRESHOLD = 13824f;

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onMovementInputUpdate(MovementInputUpdateEvent event) {
        if (!event.getEntity().hasEffect(ModEffects.OVERLOAD.getDelegate())) return;

        float modifier = MAX_WEIGHT;
        float multiplier = 1;

        if (event.getEntity().hasEffect(ModEffects.INCREASED_CARRYING_CAPACITY.getDelegate())) {
            int amplifier = event.getEntity().getEffect(ModEffects.INCREASED_CARRYING_CAPACITY.getDelegate()).getAmplifier();
            multiplier = amplifier == 0 ? 1.15f : amplifier == 1 ? 1.30f : amplifier == 2 ? 1.5f : 1f;
        }

        if (event.getEntity().hasEffect(ModEffects.INCREASED_CARRYING_CAPACITY.getDelegate())) modifier = MAX_WEIGHT * multiplier;

        float overload_amplifier = event.getEntity().getPersistentData().getFloat("overload_amplifier");
        float f = (float) Math.pow(overload_amplifier / modifier, 5);

        event.getInput().forwardImpulse = event.getInput().forwardImpulse * (1 - f);
        event.getInput().leftImpulse = event.getInput().leftImpulse * (1 - f);
    }
}