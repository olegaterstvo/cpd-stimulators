package net.chronos.cpd_stimulators.item.custom;

import net.chronos.cpd_stimulators.item.ModItems;
import net.chronos.cpd_stimulators.sound.ModSounds;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Obdolbos extends Item {
    private static final List<Pair<Triple<String, Integer, Integer>, Integer>> positives = new ArrayList<>();
    private static final List<Pair<Triple<String, Integer, Integer>, Integer>> negatives = new ArrayList<>();

    public Obdolbos(Properties properties) {
        super(properties);
    }

    private void addEffects(Player player) {
        if (player.isLocalPlayer()) return;
        Random rnd = new Random();

        // 25% of happening for each effect
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of("minecraft:speed",                     600, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of("minecraft:strength",                  600, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of("minecraft:glowing",                   600, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of("minecraft:night_vision",              600, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of("minecraft:resistance",                600, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of("minecraft:hero_of_the_village",       600, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of("minecraft:regeneration",              600, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of("minecraft:saturation",                600, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of("minecraft:haste",                     600, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of("minecraft:dolphins_grace",            600, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of("minecraft:water_breathing",           600, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of("minecraft:slow_falling",              600, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of("minecraft:jump_boost",                600, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of("minecraft:invisibility",              600, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of("cpd_stimulators:stress_resistance",   600, 0), 0));

        ModItems.addEffects(player, positives);
    }

    private void addSideEffects(Player player) {
        if (player.isLocalPlayer()) return;
        Random rnd = new Random();

        // 25% of happening for each effect          pair(triple(effect, duration (in seconds), amplifier), delay (in seconds))
        if (rnd.nextInt(4) == 0) negatives.add(Pair.of(Triple.of("minecraft:hunger",                600, 1), 1));
        if (rnd.nextInt(4) == 0) negatives.add(Pair.of(Triple.of("minecraft:nausea",                600, 0), 1));
        if (rnd.nextInt(4) == 0) negatives.add(Pair.of(Triple.of("minecraft:poison",                600, 0), 1));
        if (rnd.nextInt(4) == 0) negatives.add(Pair.of(Triple.of("cpd_stimulators:vulnerability",   600, 0), 1));
        if (rnd.nextInt(4) == 0) negatives.add(Pair.of(Triple.of("cpd_stimulators:exhaustion",      600, 0), 1));
        if (rnd.nextInt(4) == 0) negatives.add(Pair.of(Triple.of("minecraft:darkness",              600, 0), 1));
        if (rnd.nextInt(4) == 0) negatives.add(Pair.of(Triple.of("minecraft:unluck",                600, 0), 1));

        ModItems.addSideEffects(player, negatives);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        if (!Screen.hasShiftDown()) {
            tooltipComponents.add(Component.literal(""));
            tooltipComponents.add(Component.translatable("misc.cpd_stimulators.press_shift"));
            return;
        }

        tooltipComponents.add(Component.literal(""));
        tooltipComponents.add(Component.literal("§o§7" + Component.translatable("misc.cpd_stimulators.duration", 600).getString()));
        tooltipComponents.add(Component.literal("   §b§k" + Component.literal("effect.minecraft:health_boost").getString()));
        tooltipComponents.add(Component.literal("   §b§k" + Component.literal("effect.minecraft:luck").getString()));

        tooltipComponents.add(Component.literal(""));
        tooltipComponents.add(Component.literal("§o§7" + Component.translatable("misc.cpd_stimulators.delay", 1).getString() + Component.translatable("misc.cpd_stimulators.duration", 600).getString()));
        tooltipComponents.add(Component.literal("   §c§k" + Component.literal("effect.minecraft:hunger").getString()));
        tooltipComponents.add(Component.literal("   §c§k" + Component.literal("effect.cpd_stimulators.exhaustion").getString()));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(hand == InteractionHand.MAIN_HAND) {
            player.playSound(ModSounds.APPLY_INJECTOR.get(), 1f,1f);
            player.startUsingItem(hand);
        }
        return super.use(level, player, hand);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        Player player = (Player) livingEntity;
        addEffects(player);
        addSideEffects(player);
        player.getCooldowns().addCooldown(this, 600);
        player.getMainHandItem().shrink(1);

        return super.finishUsingItem(stack, level, livingEntity);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BRUSH;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity p_344979_) {
        return 10;
    }
}
