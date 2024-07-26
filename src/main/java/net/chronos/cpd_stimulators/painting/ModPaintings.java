package net.chronos.cpd_stimulators.painting;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPaintings {
    public static final DeferredRegister<PaintingVariant> PAINTINGS = DeferredRegister.create(Registries.PAINTING_VARIANT, CPDStimulators.MOD_ID);
    public static final DeferredHolder<PaintingVariant, PaintingVariant> MENDELEEV = PAINTINGS.register("mendeleev", () -> new PaintingVariant(112, 64, ResourceKey.create(Registries.PAINTING_VARIANT, ResourceLocation.fromNamespaceAndPath(CPDStimulators.MOD_ID, "mendeleev")).location()));
    public static final DeferredHolder<PaintingVariant, PaintingVariant> MENDELEEV_ = PAINTINGS.register("mendeleev_", () -> new PaintingVariant(112, 64, ResourceKey.create(Registries.PAINTING_VARIANT, ResourceLocation.fromNamespaceAndPath(CPDStimulators.MOD_ID, "mendeleev_")).location()));

    public static void register(IEventBus eventBus) {
        PAINTINGS.register(eventBus);
    }
}
