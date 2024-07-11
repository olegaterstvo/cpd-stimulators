package net.chronos.cpd_stimulators.item;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashSet;
import java.util.Set;

public class ModItemGroup {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CPDStimulators.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CHRONOSPROJECT =
        CREATIVE_TABS.register("chronosproject",
                () -> {
                    CreativeModeTab.Builder builder = CreativeModeTab.builder();
                    builder.displayItems((parameters, output) -> {
                        Set<Item> addedItems = new HashSet<>();

                        ModItems.ITEMS.getEntries()
                                .stream()
                                .map((item) -> item.get().asItem())
                                .filter(addedItems::add)
                                .forEach(output::accept);
                    });

                    builder.icon(() -> new ItemStack(ModItems.PROPITAL_INJECTOR.get()));
                    builder.title(Component.translatable("itemGroup.cpd_stimulators"));

                    return builder.build();
                });

    public static void register(IEventBus eventBus){
        CREATIVE_TABS.register(eventBus);
    }
}
