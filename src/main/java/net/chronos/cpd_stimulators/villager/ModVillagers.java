package net.chronos.cpd_stimulators.villager;

import com.google.common.collect.ImmutableSet;
import net.chronos.cpd_stimulators.CPDStimulators;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, CPDStimulators.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, CPDStimulators.MOD_ID);

    // TODO: change poi
    public static final DeferredHolder<PoiType, PoiType> REDSTONE_POI = POI_TYPES.register("redstone_poi",
            () -> new PoiType(ImmutableSet.copyOf(Blocks.REDSTONE_BLOCK.getStateDefinition().getPossibleStates()),
                    1,1));
    public static final DeferredHolder<VillagerProfession, VillagerProfession> THERAPIST =
            VILLAGER_PROFESSIONS.register("therapist", () -> new VillagerProfession("therapist",
                    poiTypeHolder -> poiTypeHolder.getDelegate() == REDSTONE_POI.getDelegate(), poiTypeHolder -> poiTypeHolder.getDelegate() == REDSTONE_POI.getDelegate(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_CLERIC));


    public static void register(IEventBus eventBus){
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
