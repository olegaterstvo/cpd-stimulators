package net.chronos.cpd_stimulators.entity;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.entity.custom.Sanitar;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, CPDStimulators.MOD_ID);
    public static final DeferredHolder<EntityType<?>, EntityType<Sanitar>> SANITAR = ENTITY_TYPES.register("sanitar", () -> EntityType.Builder.of(Sanitar::new, MobCategory.MONSTER).sized(.6f, 1.95f).build("sanitar"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
