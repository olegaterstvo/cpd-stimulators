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

public class AHF1M extends Item {
    private static final List<Pair<Triple<Holder<MobEffect>, Integer, Integer>, Integer>> positives = new ArrayList<>();
    private static final List<Pair<Triple<Holder<MobEffect>, Integer, Integer>, Integer>> negatives = new ArrayList<>();

    public AHF1M(Properties properties) {
        super(properties);

        positives.add(Pair.of(Triple.of(MobEffects.REGENERATION,    60, 0), 0));

        negatives.add(Pair.of(Triple.of(MobEffects.HUNGER,          60, 0), 60));
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
