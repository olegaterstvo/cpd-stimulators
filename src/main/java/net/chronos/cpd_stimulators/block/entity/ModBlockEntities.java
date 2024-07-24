package net.chronos.cpd_stimulators.block.entity;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CPDStimulators.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CentrifugeBlockEntity>> CENTRIFUGE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("centrifuge_block_entity", () ->
                    BlockEntityType.Builder.of(CentrifugeBlockEntity::new, ModBlocks.CENTRIFUGE.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
