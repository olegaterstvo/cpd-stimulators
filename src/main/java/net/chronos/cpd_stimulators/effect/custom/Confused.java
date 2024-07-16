package net.chronos.cpd_stimulators.effect.custom;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.effect.ModEffects;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.Random;

@EventBusSubscriber(modid = CPDStimulators.MOD_ID)
public class Confused extends MobEffect {
    public Confused(MobEffectCategory category, int color) {
        super(category, color);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onMovementInputUpdate(MovementInputUpdateEvent event) {
        if (!event.getEntity().hasEffect(ModEffects.CONFUSED.getDelegate())) return;

        event.getInput().forwardImpulse = -event.getInput().forwardImpulse;
        event.getInput().leftImpulse = -event.getInput().leftImpulse;
    }

    @SubscribeEvent
    public static void onDamageTaken(LivingDamageEvent.Pre event) {
        if (event.getEntity().getEffect(ModEffects.STRESS_RESISTANCE.getDelegate()) != null) return;

        if (event.getSource().is(DamageTypes.EXPLOSION) || event.getSource().is(DamageTypes.PLAYER_EXPLOSION)){
            Random rnd = new Random();
            if (rnd.nextInt(4) != 0) return; //25% chance

            int duration = rnd.nextInt(60, 140);
            event.getEntity().addEffect(new MobEffectInstance(ModEffects.CONFUSED.getDelegate(), duration, 0));
        }
    }
}