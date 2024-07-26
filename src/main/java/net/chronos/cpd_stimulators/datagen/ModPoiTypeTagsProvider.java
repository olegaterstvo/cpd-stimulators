package net.chronos.cpd_stimulators.datagen;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.villager.ModVillagers;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.PoiTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModPoiTypeTagsProvider extends PoiTypeTagsProvider {
    public ModPoiTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, CPDStimulators.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(PoiTypeTags.ACQUIRABLE_JOB_SITE)
                .addOptional(ResourceLocation.parse(ModVillagers.CENTRIFUGE_POI.getRegisteredName()));
    }
}
