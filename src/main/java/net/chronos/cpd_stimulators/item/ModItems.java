package net.chronos.cpd_stimulators.item;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.item.custom.*;
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
    public static final DeferredItem<Item> ADRENALINE_INJECTOR = registerItem("adrenaline",
            () -> new Adrenaline(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> AHF1M_INJECTOR = registerItem("ahf1m",
            () -> new AHF1M(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> BTG2A2_INJECTOR = registerItem("btg2a2",
            () -> new BTG2A2(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> BTG3_INJECTOR = registerItem("btg3",
            () -> new BTG3(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> L1_INJECTOR = registerItem("l1",
            () -> new L1(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> MULE_INJECTOR = registerItem("mule",
            () -> new Mule(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> OBDOLBOS_INJECTOR = registerItem("obdolbos",
            () -> new Obdolbos(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> OBDOLBOS2_INJECTOR = registerItem("obdolbos2",
            () -> new Obdolbos2(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> P22_INJECTOR = registerItem("p22",
            () -> new P22(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> PERFOTORAN_INJECTOR = registerItem("perfotoran",
            () -> new Perfotoran(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> SJ1_INJECTOR = registerItem("sj1",
            () -> new SJ1(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> SJ6_INJECTOR = registerItem("sj6",
            () -> new SJ6(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> TRIMADOL_INJECTOR = registerItem("trimadol",
            () -> new Trimadol(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> ZAGUSTIN_INJECTOR = registerItem("zagustin",
            () -> new Zagustin(new Item.Properties().stacksTo(16)));



    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
    private static <T extends Item> DeferredItem<Item> registerItem(String name, Supplier<T> item){
        return ITEMS.register(name, item);
    }

}
