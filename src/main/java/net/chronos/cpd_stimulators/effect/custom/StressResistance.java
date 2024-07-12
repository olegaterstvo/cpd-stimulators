package net.chronos.cpd_stimulators.effect.custom;

import net.chronos.cpd_stimulators.effect.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class StressResistance extends MobEffect {
    public StressResistance(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        if (livingEntity.getEffect(ModEffects.CONFUSED.getDelegate()) != null){
            livingEntity.removeEffect(ModEffects.CONFUSED.getDelegate());
        }
        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
