package net.chronos.cpd_stimulators.effect.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class Antidote extends MobEffect {
    public Antidote(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        if (livingEntity.hasEffect(MobEffects.POISON)) {
            livingEntity.removeEffect(MobEffects.POISON);
        }
        if (livingEntity.hasEffect(MobEffects.WITHER)) {
            livingEntity.removeEffect(MobEffects.WITHER);
        }
        if (livingEntity.hasEffect(MobEffects.CONFUSION.getDelegate())) {
            livingEntity.removeEffect(MobEffects.CONFUSION.getDelegate());
        }
        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
