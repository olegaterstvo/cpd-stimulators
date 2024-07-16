package net.chronos.cpd_stimulators.effect.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class Photopsia extends MobEffect {
    public Photopsia(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        pLivingEntity.removeEffect(MobEffects.NIGHT_VISION);
        pLivingEntity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 100, 0, false, false, false));
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        if (pDuration % 90 == 0) return true;
        return false;
    }

    @Override
    public void onEffectStarted(LivingEntity pLivingEntity, int pAmplifier) {
        super.onEffectStarted(pLivingEntity, pAmplifier);
        pLivingEntity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 100, 0, false, false, false));
    }
}

