package net.chronos.cpd_stimulators.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties MAYO =
            new FoodProperties.Builder().nutrition(20).saturationModifier(1.0F).effect( ()->
                    new MobEffectInstance(
                            BuiltInRegistries.MOB_EFFECT.getHolder(ResourceLocation.fromNamespaceAndPath("toughasnails", "thirst")).isPresent() ?
                            BuiltInRegistries.MOB_EFFECT.getHolder(ResourceLocation.fromNamespaceAndPath("toughasnails", "thirst")).get() :
                            MobEffects.CONFUSION,
                    1200, 1), 1.0F).build();

}
