package net.chronos.cpd_stimulators.event;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.config.ModServerConfigs;
import net.chronos.cpd_stimulators.effect.ModEffects;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EventBusSubscriber(modid = CPDStimulators.MOD_ID)
public class PlayerEventHandler {
    private enum Weights {
        DebugStickItem(1000f, false),
        EndCrystalItem(100f, false),
        MinecartItem(20f, false),
        BedItem(20f, false),
        MaceItem(15f, true),
        ArmorItem(15f, false),
        AnimalArmorItem(15f, false),
        BlockItem(12f, false),
        ShieldItem(10f, false),
        AxeItem(10f, false),
        SwordItem(9f, false),
        PickaxeItem(8f, false),
        ShovelItem(7f, false),
        TridentItem(7f, true),
        HoeItem(6f, false),
        ProjectileWeaponItem(6f, false),
        CrossbowItem(4f, false),
        BowItem(2f, false),
        MapItem(.05f, false),
        EmptyMapItem(.05f, false),
        InstrumentItem(6f, false),
        LeadItem(.05f, false),
        ArmorStandItem(4f, false),
        FireworkRocketItem(.15625f, false),
        SmithingTemplateItem(.5f, false),
        SaddleItem(5f, false),
        BundleItem(.1f, false),
        ChorusFruitItem(.2f, false),
        BannerPatternItem(.05f, false),
        ArrowItem(.05f, false),
        SpectralArrowItem(.05f, false),
        TippedArrowItem(.05f, false),
        SuspiciousStewItem(.5f, false),
        FlintAndSteelItem(.1f, false),
        BoneMealItem(.02f, false),
        FireChargeItem(.1f, false),
        ShearsItem(.25f, false),
        HoneycombItem(.1f, false),
        SnowballItem(.05f, false),
        BrushItem(.25f, false),
        FishingRodItem(1f, false),
        FoodOnAStickItem(1.1f, false),
        NameTagItem(.01f, false),
        CompassItem(.05f, false),
        DyeItem(.1f, false),
        InkSacItem(.1f, false),
        GlowInkSacItem(.1f, false),
        SpawnEggItem(.15f, false),
        DeferredSpawnEggItem(.15f, false),
        EggItem(.1f, false),
        ElytraItem(1.5f, false),
        BoatItem(2f, false),
        FireworkStarItem(.1f, false),
        SpyglassItem(.5f, false),
        EnderEyeItem(.25f, false),
        EnderpearlItem(-.1f, false),
        DiscFragmentItem(.25f, false),
        HangingEntityItem(.5f, false),
        ItemFrameItem(.25f, false),
        ItemNameBlockItem(0f, false),
        StandingAndWallBlockItem(0f, false),
        PlaceOnWaterBlockItem(.02f, false),
        GameMasterBlockItem(0f, false),
        SolidBucketItem(0f, false),
        ScaffoldingBlockItem(1f, false),
        DoubleHighBlockItem(.2f, false),
        BannerItem(1f, false),
        SignItem(.5f, false),
        HangingSignItem(.5f, false),
        PlayerHeadItem(5f, false),
        BucketItem(.5f, false),
        MilkBucketItem(3.5f, false),
        MobBucketItem(3.5f, false),
        EnchantedBookItem(2f, false),
        WrittenBookItem(.4f, false),
        KnowledgeBookItem(.4f, false),
        WritableBookItem(.4f, false),
        BookItem(.4f, false),
        ExperienceBottleItem(.33f, false),
        HoneyBottleItem(.33f, false),
        PotionItem(.33f, false),
        SplashPotionItem(.33f, false),
        LingeringPotionItem(.33f, false),
        OminousBottleItem(.33f, false),
        BottleItem(.1f, false),
        Item(.05f, false),
        WindChargeItem(0f, false),
        AirItem(0f, false);

        private static final Map<String, Weights> ENUM_MAP = Stream.of(Weights.values())
                .collect(Collectors.toMap(Enum::name, Function.identity()));

        public static float getWeight(final String name) {
            return ENUM_MAP.getOrDefault(name, Item).weight;
        }

        public static boolean getBindable(final String name) {
            return ENUM_MAP.getOrDefault(name, Item).bindable;
        }

        private final boolean bindable;
        private final float weight;

        Weights(float weight, boolean bindable) {
            this.weight = weight;
            this.bindable = bindable;
        }
    }

    @SubscribeEvent
    public static void onInventoryUpdate(PlayerTickEvent.Post event) {
        if (!event.getEntity().isLocalPlayer()) return;

        int c = 0; for (ItemStack item : event.getEntity().getInventory().items) c += item.getCount();

        if (event.getEntity().getPersistentData().getLong("size") == c) return;
        event.getEntity().getPersistentData().putLong("size", c);

        float sum = 0; for (ItemStack item : event.getEntity().getInventory().items) {
            String simpleName = item.getItem().getClass().getSimpleName();
            for (int i = 0; i < item.getCount(); i++) sum += Weights.getWeight(simpleName);

            if (Weights.getBindable(simpleName)) {
                if (item.has(DataComponents.CUSTOM_DATA)) {
                    String ownerId = Objects.requireNonNull(item.get(DataComponents.CUSTOM_DATA)).copyTag().getString("ownerId");
                    if (!event.getEntity().getStringUUID().equals(ownerId)) sum += 1000000f;
                } else {
                    CompoundTag nbt = new CompoundTag();
                    nbt.putString("owner", event.getEntity().getDisplayName().getString());
                    nbt.putString("ownerId", event.getEntity().getStringUUID());
                    CustomData data = CustomData.of(nbt);
                    item.set(DataComponents.CUSTOM_DATA, data);
                }
            }
        }

        event.getEntity().removeEffect(ModEffects.INSUPERABILITY);
        event.getEntity().removeEffect(ModEffects.OVERLOAD);

        if (sum < 2000f) return;

        if (sum >= 1000000f) {
            event.getEntity().addEffect(new MobEffectInstance(ModEffects.INSUPERABILITY, -1));
        } else {
            event.getEntity().getPersistentData().putFloat("overload_amplifier", sum);
            event.getEntity().addEffect(new MobEffectInstance(ModEffects.OVERLOAD, -1, 0, false, false));
            if (sum > ModServerConfigs.OVERLOAD_STOP_SPRINTING_THRESHOLD.get().floatValue() && event.getEntity().isSprinting()) event.getEntity().setSprinting(false);
        }
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (event.getEntity() == null) return;
        if (!event.getEntity().isLocalPlayer()) return;

        String weight;
        String coloredWeight;

        DecimalFormat format = new DecimalFormat(); format.setDecimalSeparatorAlwaysShown(false);
        Float w = Weights.getWeight(event.getItemStack().getItem().getClass().getSimpleName()) * event.getItemStack().getCount();

        weight = "§7⭐ §8" + Component.translatable("misc.cpd_stimulators.weight").getString() + ": §7" + String.valueOf(format.format(w)) + " §8" + Component.translatable("misc.cpd_stimulators.cu").getString();
        coloredWeight = (w < 600f ? (w < 400f ? (w < 100f ? (w < 5f ? "§7" : "§2") : "§e") : "§6") : "§4") + "⭐ " + Component.translatable("misc.cpd_stimulators.weight").getString() + ": " + String.valueOf(format.format(w)) + " " + Component.translatable("misc.cpd_stimulators.cu").getString();

        if (event.getItemStack().has(DataComponents.CUSTOM_DATA)) {
            String ownerId = Objects.requireNonNull(event.getItemStack().get(DataComponents.CUSTOM_DATA)).copyTag().getString("ownerId");
            if (!event.getEntity().getStringUUID().equals(ownerId)) weight = "§7⭐ " + Component.translatable("misc.cpd_stimulators.weight").getString() + ": ∞";

            event.getToolTip().add(Component.literal("§7❤ §8" + Component.translatable("misc.cpd_stimulators.owner").getString() + ": §e" + Objects.requireNonNull(event.getItemStack().get(DataComponents.CUSTOM_DATA)).copyTag().getString("owner")));
        }

        event.getToolTip().add(Component.literal(""));
        event.getToolTip().addLast(Component.literal(weight));
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        if (!event.getEntity().level().isClientSide()) return;
        int[] pos = event.getEntity().getPersistentData().getIntArray("airdrop_pos");
        if (pos.length == 0) return;
        if (Math.random() > 0.5f) return;
        event.getEntity().level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                pos[0] + 0.5f,
                pos[1] + 0.5f,
                pos[2] + 0.5f,
                0.03, 0.07, 0.02);
    }
}
