package net.chronos.cpd_stimulators.trade;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.item.ModItems;
import net.chronos.cpd_stimulators.villager.ModVillagers;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import java.util.List;
import java.util.Optional;

@EventBusSubscriber(modid = CPDStimulators.MOD_ID)
public class ModTrades {
    @SubscribeEvent
    public static void registerCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == ModVillagers.THERAPIST.get()){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // TODO: balance trades
            trades.get(1 /* trade level */ ).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    Optional.of(new ItemCost(Items.REDSTONE, 1)),
                    new ItemStack(ModItems.ADRENALINE_INJECTOR.get(), 1),
                    4,8,0.01f
            ));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.AHF1M_INJECTOR.get(), 1),
                    4,8,0.01f
            ));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.BTG2A2_INJECTOR.get(), 1),
                    4,8,0.01f
            ));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.BTG3_INJECTOR.get(), 1),
                    4,8,0.01f
            ));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.ETGC_INJECTOR.get(), 1),
                    4,8,0.01f
            ));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.L1_INJECTOR.get(), 1),
                    4,8,0.01f
            ));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.MELDONIN_INJECTOR.get(), 1),
                    4,8,0.01f
            ));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.MORPHINE_INJECTOR.get(), 1),
                    4,8,0.01f
            ));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.MULE_INJECTOR.get(), 1),
                    4,8,0.01f
            ));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.OBDOLBOS_INJECTOR.get(), 1),
                    4,8,0.01f
            ));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.OBDOLBOS2_INJECTOR.get(), 1),
                    4,8,0.01f
            ));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.P22_INJECTOR.get(), 1),
                    4,8,0.01f
            ));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.PERFOTORAN_INJECTOR.get(), 1),
                    4,8,0.01f
            ));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.PNB_INJECTOR.get(), 1),
                    4,8,0.01f
            ));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.PROPITAL_INJECTOR.get(), 1),
                    4,8,0.01f
            ));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.SJ1_INJECTOR.get(), 1),
                    4,8,0.01f
            ));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.SJ6_INJECTOR.get(), 1),
                    4,8,0.01f
            ));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.SJ9_INJECTOR.get(), 1),
                    4,8,0.01f
            ));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.SJ12_INJECTOR.get(), 1),
                    4,8,0.01f
            ));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.SJ15_INJECTOR.get(), 1),
                    4,8,0.01f
            ));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.TRIMADOL_INJECTOR.get(), 1),
                    4,8,0.01f
            ));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.ZAGUSTIN_INJECTOR.get(), 1),
                    4,8,0.01f
            ));

        }
    }
}
