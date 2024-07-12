package net.chronos.cpd_stimulators.datagen;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CPDStimulators.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        List<DeferredHolder<Item, ? extends Item>> items = ModItems.ITEMS.getEntries().stream().toList();
        for (DeferredHolder<Item, ? extends Item> item : items){
            simpleItem((DeferredHolder<Item, Item>) item);
        }

//        simpleItem(ModItems.ETGC_INJECTOR);
//        simpleItem(ModItems.MELDONIN_INJECTOR);
//        simpleItem(ModItems.PNB_INJECTOR);
//        simpleItem(ModItems.PROPITAL_INJECTOR);
    }

    private ItemModelBuilder simpleItem(DeferredHolder<Item, Item> item){
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(CPDStimulators.MOD_ID, "item/" + item.getId().getPath()));
    }
}
