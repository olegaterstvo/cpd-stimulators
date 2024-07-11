package net.chronos.cpd_stimulators.item;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.item.custom.ETGc;
import net.chronos.cpd_stimulators.item.custom.Meldonin;
import net.chronos.cpd_stimulators.item.custom.PNB;
import net.chronos.cpd_stimulators.item.custom.Propital;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.Util;
import java.util.function.Supplier;
//import net.minecraft.ChatFormatting;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(CPDStimulators.MOD_ID);

    public static final DeferredItem<Item> PROPITAL_INJECTOR = registerItem("propital",
            () -> new Propital(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> ETGC_INJECTOR = registerItem("etgc",
            () -> new ETGc(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> PNB_INJECTOR = registerItem("pnb",
            () -> new PNB(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> MELDONIN_INJECTOR = registerItem("meldonin",
            () -> new Meldonin(new Item.Properties().stacksTo(16)));



    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
    private static <T extends Item> DeferredItem<Item> registerItem(String name, Supplier<T> item){
        return ITEMS.register(name, item);
    }

}
