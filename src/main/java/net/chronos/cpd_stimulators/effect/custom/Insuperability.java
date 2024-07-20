package net.chronos.cpd_stimulators.effect.custom;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.effect.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;

@EventBusSubscriber(modid = CPDStimulators.MOD_ID)
public class Insuperability extends MobEffect {
    public Insuperability(MobEffectCategory category, int color) {
        super(category, color);
    }

    @SubscribeEvent
    public static void onMovementInputUpdate(MovementInputUpdateEvent event) {
        if (!event.getEntity().isLocalPlayer()) return;
        if (!event.getEntity().hasEffect(ModEffects.INSUPERABILITY.getDelegate())) return;

        event.getInput().leftImpulse = 0f;
        event.getInput().forwardImpulse = 0f;
        event.getInput().jumping = false;
    }
}

