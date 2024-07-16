package net.chronos.cpd_stimulators.item;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.event.DeferredMobEffect;
import net.chronos.cpd_stimulators.item.custom.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CPDStimulators.MOD_ID);
    private static final int maxStackSize = 4;

    public static final DeferredItem<Item> PROPITAL_INJECTOR = registerItem("propital", () -> new Propital(new Item.Properties().stacksTo(maxStackSize)));
    public static final DeferredItem<Item> ETGC_INJECTOR = registerItem("etgc", () -> new ETGc(new Item.Properties().stacksTo(maxStackSize)));
    public static final DeferredItem<Item> PNB_INJECTOR = registerItem("pnb", () -> new PNB(new Item.Properties().stacksTo(maxStackSize)));
    public static final DeferredItem<Item> MELDONIN_INJECTOR = registerItem("meldonin", () -> new Meldonin(new Item.Properties().stacksTo(maxStackSize)));
    public static final DeferredItem<Item> ADRENALINE_INJECTOR = registerItem("adrenaline", () -> new Adrenaline(new Item.Properties().stacksTo(maxStackSize)));
    public static final DeferredItem<Item> AHF1M_INJECTOR = registerItem("ahf1m", () -> new AHF1M(new Item.Properties().stacksTo(maxStackSize)));
    public static final DeferredItem<Item> BTG2A2_INJECTOR = registerItem("btg2a2", () -> new BTG2A2(new Item.Properties().stacksTo(maxStackSize)));
    public static final DeferredItem<Item> BTG3_INJECTOR = registerItem("btg3", () -> new BTG3(new Item.Properties().stacksTo(maxStackSize)));
    public static final DeferredItem<Item> L1_INJECTOR = registerItem("l1", () -> new L1(new Item.Properties().stacksTo(maxStackSize)));
    public static final DeferredItem<Item> MULE_INJECTOR = registerItem("mule", () -> new Mule(new Item.Properties().stacksTo(maxStackSize)));
    public static final DeferredItem<Item> OBDOLBOS_INJECTOR = registerItem("obdolbos", () -> new Obdolbos(new Item.Properties().stacksTo(maxStackSize)));
    public static final DeferredItem<Item> OBDOLBOS2_INJECTOR = registerItem("obdolbos2", () -> new Obdolbos2(new Item.Properties().stacksTo(maxStackSize)));
    public static final DeferredItem<Item> P22_INJECTOR = registerItem("p22", () -> new P22(new Item.Properties().stacksTo(maxStackSize)));
    public static final DeferredItem<Item> PERFOTORAN_INJECTOR = registerItem("perfotoran", () -> new Perfotoran(new Item.Properties().stacksTo(maxStackSize)));
    public static final DeferredItem<Item> SJ1_INJECTOR = registerItem("sj1", () -> new SJ1(new Item.Properties().stacksTo(maxStackSize)));
    public static final DeferredItem<Item> SJ6_INJECTOR = registerItem("sj6", () -> new SJ6(new Item.Properties().stacksTo(maxStackSize)));
    public static final DeferredItem<Item> TRIMADOL_INJECTOR = registerItem("trimadol", () -> new Trimadol(new Item.Properties().stacksTo(maxStackSize)));
    public static final DeferredItem<Item> ZAGUSTIN_INJECTOR = registerItem("zagustin", () -> new Zagustin(new Item.Properties().stacksTo(maxStackSize)));
    public static final DeferredItem<Item> SJ15_INJECTOR = registerItem("sj15", () -> new SJ15(new Item.Properties().stacksTo(maxStackSize)));

    public static void register(IEventBus eventBus){
        Optional<Holder.Reference<MobEffect>> mobEffect = BuiltInRegistries.MOB_EFFECT.getHolder(ResourceLocation.parse("toughasnails:thirst"));
        if (mobEffect.isPresent()) {
            registerItem("sj9", () -> new SJ9(new Item.Properties().stacksTo(maxStackSize)));
            registerItem("sj12", () -> new SJ12(new Item.Properties().stacksTo(maxStackSize)));
        }

        ITEMS.register(eventBus);
    }
    private static <T extends Item> DeferredItem<Item> registerItem(String name, Supplier<T> item){
        return ITEMS.register(name, item);
    }

    public static void appendApplicableEffectToTooltip(List<Component> tooltipComponents, List<Pair<Triple<String, Integer, Integer>, Integer>> tives, boolean isNegatives) {
        AtomicInteger index = new AtomicInteger();

        if (!isNegatives) tooltipComponents.add(Component.literal(""));

        tives.forEach((s) -> {
            Optional<Holder.Reference<MobEffect>> mobEffect = BuiltInRegistries.MOB_EFFECT.getHolder(ResourceLocation.parse(s.getLeft().getLeft()));

            mobEffect.ifPresent(mobEffectReference -> {
                int duration = s.getLeft().getMiddle();
                int amplifier = s.getLeft().getRight();
                int delay = s.getRight();

                int durationMinutes = duration / 60;
                int durationSeconds = duration % 60;

                int delayMinutes = delay / 60;
                int delaySeconds = delay % 60;

                String duration_ = "";
                duration_ += durationMinutes > 0 ? String.valueOf(durationMinutes) + Component.translatable("misc.cpd_stimulators.m").getString() : "";
                duration_ += durationSeconds > 0 ? String.valueOf(durationSeconds) + Component.translatable("misc.cpd_stimulators.s").getString() : "";

                String delay_ = "";
                delay_ += delayMinutes > 0 ? String.valueOf(delayMinutes) + Component.translatable("misc.cpd_stimulators.m").getString() : "";
                delay_ += delaySeconds > 0 ? String.valueOf(delaySeconds) + Component.translatable("misc.cpd_stimulators.s").getString() : "";

                String[] registeredName = mobEffectReference.getRegisteredName().split(":");
                String romanAmplifier = amplifier == 0 ? "" : amplifier == 1 ? "II" : amplifier == 2 ? "III" : amplifier == 3 ? "IV" : ""; // freaky

                if (index.get() == 0 || !tives.get(index.get() - 1).getLeft().getMiddle().equals(s.getLeft().getMiddle()))
                    tooltipComponents.add(Component.literal("§o§7" + Component.translatable("misc.cpd_stimulators.delay", delay_).getString() + Component.translatable("misc.cpd_stimulators.duration", duration_).getString()));
                tooltipComponents.add(Component.literal("   " + (isNegatives ? "§c" : "§b") + Component.translatable("effect." + registeredName[0] + "." + registeredName[1]).getString() + " " + romanAmplifier));

                index.addAndGet(1);
            });
        });

        if (!isNegatives) tooltipComponents.add(Component.literal(""));
    }

    public static void addEffects(Player player, List<Pair<Triple<String, Integer, Integer>, Integer>> effects) {
        effects.forEach((s) -> {
//            String key = s.getLeft().getLeft();
//            int duration = s.getLeft().getMiddle() * 20;
//            int amplifier = s.getLeft().getRight();
//
//            Optional<Holder.Reference<MobEffect>> mobEffect = BuiltInRegistries.MOB_EFFECT.getHolder(ResourceLocation.parse(key));
//            mobEffect.ifPresent(mobEffectReference -> {
//                player.addEffect(new MobEffectInstance(mobEffectReference, duration, amplifier, false, false, false));
//            });
            DeferredMobEffect.add(player, s.getRight() * 20 + "@" + s.getLeft().getLeft() + "@" + s.getLeft().getMiddle() * 20 + "@" + s.getLeft().getRight());
        });
    }

    public static void addSideEffects(Player player, List<Pair<Triple<String, Integer, Integer>, Integer>> effects) {
        effects.forEach((s) -> {
            DeferredMobEffect.add(player, s.getRight() * 20 + "@" + s.getLeft().getLeft() + "@" + s.getLeft().getMiddle() * 20 + "@" + s.getLeft().getRight());
        });
    }

    public static void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag, List<Pair<Triple<String, Integer, Integer>, Integer>> positives, List<Pair<Triple<String, Integer, Integer>, Integer>> negatives) {
        if (!Screen.hasShiftDown()) {
            // tooltipComponents.add(Component.literal(""));
            tooltipComponents.add(Component.translatable("misc.cpd_stimulators.press_shift"));
            return;
        }

        appendApplicableEffectToTooltip(tooltipComponents, positives, false);
        appendApplicableEffectToTooltip(tooltipComponents, negatives, true);
    }
}
