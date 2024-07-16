package net.chronos.cpd_stimulators.item.custom;

import net.chronos.cpd_stimulators.item.ModItems;
import net.chronos.cpd_stimulators.sound.ModSounds;
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

public class P22 extends Item {
    private static final List<Pair<Triple<String, Integer, Integer>, Integer>> positives = new ArrayList<>();
    private static final List<Pair<Triple<String, Integer, Integer>, Integer>> negatives = new ArrayList<>();

    public P22(Properties properties) {
        super(properties);

        // pair(triple(effect, duration (in seconds), amplifier), delay (in seconds))
        positives.add(Pair.of(Triple.of("minecraft:regeneration",           60, 2), 1));
        positives.add(Pair.of(Triple.of("minecraft:health_boost",           60, 2), 1));
        positives.add(Pair.of(Triple.of("minecraft:resistance",             60, 2), 1));

        negatives.add(Pair.of(Triple.of("minecraft:hunger",                 60, 0), 65));
        negatives.add(Pair.of(Triple.of("minecraft:slowness",               60, 0), 65));
        negatives.add(Pair.of(Triple.of("minecraft:mining_fatigue",         60, 0), 65));
    }

    private void addEffects(Player player) { ModItems.addEffects(player, positives); }
    private void addSideEffects(Player player) { ModItems.addSideEffects(player, negatives); }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        ModItems.appendHoverText(stack, context, tooltipComponents, tooltipFlag, positives, negatives);
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
