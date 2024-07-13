package net.chronos.cpd_stimulators.item.custom;

import net.chronos.cpd_stimulators.effect.ModEffects;
import net.chronos.cpd_stimulators.event.ModPlayerEvent;
import net.chronos.cpd_stimulators.item.ModItems;
import net.chronos.cpd_stimulators.sound.ModSounds;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
    private static final List<Pair<Triple<Holder<MobEffect>, Integer, Integer>, Integer>> positives = new ArrayList<>();
    private static final List<Pair<Triple<Holder<MobEffect>, Integer, Integer>, Integer>> negatives = new ArrayList<>();

    public Obdolbos(Properties properties) {
        super(properties);
    }

    private void addEffects(Player player) {
        Random rnd = new Random();

        // 25% of happening for each effect
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of(MobEffects.MOVEMENT_SPEED, 12000, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of(MobEffects.DAMAGE_BOOST, 12000, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of(MobEffects.GLOWING, 12000, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of(MobEffects.NIGHT_VISION, 12000, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of(MobEffects.DAMAGE_RESISTANCE, 12000, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of(MobEffects.HERO_OF_THE_VILLAGE, 12000, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of(MobEffects.REGENERATION, 12000, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of(MobEffects.SATURATION, 12000, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of(MobEffects.DIG_SPEED, 12000, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of(MobEffects.DOLPHINS_GRACE, 12000, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of(MobEffects.WATER_BREATHING, 12000, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of(MobEffects.SLOW_FALLING, 12000, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of(MobEffects.JUMP, 12000, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of(MobEffects.INVISIBILITY, 12000, 0), 0));
        if (rnd.nextInt(4) == 0) positives.add(Pair.of(Triple.of(ModEffects.STRESS_RESISTANCE, 12000, 0), 0));

        ModItems.addEffects(player, positives);
    }

    private void addSideEffects(Player player) {
        Random rnd = new Random();

        // 25% of happening for each effect
        if (rnd.nextInt(4) == 0) negatives.add(Pair.of(Triple.of(MobEffects.HUNGER, 12000, 1), 1));
        if (rnd.nextInt(4) == 0) negatives.add(Pair.of(Triple.of(MobEffects.CONFUSION, 12000, 0), 1));
        if (rnd.nextInt(4) == 0) negatives.add(Pair.of(Triple.of(MobEffects.POISON, 12000, 0), 1));
        if (rnd.nextInt(4) == 0) negatives.add(Pair.of(Triple.of(ModEffects.VULNERABILITY, 12000, 0), 1));
        if (rnd.nextInt(4) == 0) negatives.add(Pair.of(Triple.of(ModEffects.EXHAUSTION, 12000, 0), 1));
        if (rnd.nextInt(4) == 0) negatives.add(Pair.of(Triple.of(MobEffects.DARKNESS, 12000, 0), 1));
        if (rnd.nextInt(4) == 0) negatives.add(Pair.of(Triple.of(MobEffects.UNLUCK, 12000, 0), 1));

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
