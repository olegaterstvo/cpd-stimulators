package net.chronos.cpd_stimulators.effect.custom;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.effect.ModEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

@EventBusSubscriber(modid = CPDStimulators.MOD_ID)
public class Exhaustion extends MobEffect {
    private static AttributeModifier modifier;
    public Exhaustion(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if ( livingEntity.getAttribute(Attributes.MAX_HEALTH).hasModifier(ResourceLocation.fromNamespaceAndPath("cpd_stimulators", "exhaustion")) ){
            modifier = livingEntity.getAttribute(Attributes.MAX_HEALTH).getModifier(ResourceLocation.fromNamespaceAndPath("cpd_stimulators", "exhaustion"));

            livingEntity.getAttribute(Attributes.MAX_HEALTH).removeModifier(modifier);
        }
        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public void onEffectStarted(LivingEntity livingEntity, int amplifier) {
        float NEW_MAX_HEALTH = livingEntity.getMaxHealth() - (4.0f + amplifier * 4.0f);
        if (NEW_MAX_HEALTH > 20)
            NEW_MAX_HEALTH = 20;
        if (livingEntity.getHealth() > NEW_MAX_HEALTH){
            livingEntity.setHealth(NEW_MAX_HEALTH);
        }
        modifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("cpd_stimulators", "exhaustion"),
                (4.0f + amplifier * 4.0f) * -1,
                AttributeModifier.Operation.ADD_VALUE
        );
        if ( !livingEntity.getAttribute(Attributes.MAX_HEALTH).hasModifier(ResourceLocation.fromNamespaceAndPath("cpd_stimulators", "exhaustion")) ) {
            livingEntity.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(modifier);
        }
        super.onEffectStarted(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        if (duration == 1){
            return true;
        }
        return false;
    }

    @SubscribeEvent
    public static void effectEvent(MobEffectEvent.Remove event){
        if (event.getEffectInstance().is(ModEffects.EXHAUSTION)){
            if ( event.getEntity().getAttribute(Attributes.MAX_HEALTH).hasModifier(ResourceLocation.fromNamespaceAndPath("cpd_stimulators", "exhaustion")) ) {
                modifier = event.getEntity().getAttribute(Attributes.MAX_HEALTH).getModifier(ResourceLocation.fromNamespaceAndPath("cpd_stimulators", "exhaustion"));


                event.getEntity().getAttribute(Attributes.MAX_HEALTH).removeModifier(modifier);
            }
        }
    }

}
