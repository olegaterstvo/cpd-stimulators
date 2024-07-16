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

public class SJ15 extends Item {
    private static final List<Pair<Triple<String, Integer, Integer>, Integer>> positives = new ArrayList<>();
    private static final List<Pair<Triple<String, Integer, Integer>, Integer>> negatives = new ArrayList<>();

    public SJ15(Properties properties) {
        super(properties);

        // pair(triple(effect, duration (in seconds), amplifier), delay (in seconds))
        positives.add(Pair.of(Triple.of("cpd_stimulators:increased_carrying_capacity",      900, 1), 1));
        positives.add(Pair.of(Triple.of("minecraft:saturation",                             900, 1), 1));
        positives.add(Pair.of(Triple.of("minecraft:strength",                               900, 1), 1));
        positives.add(Pair.of(Triple.of("cpd_stimulators:antidote",                         900, 0), 1));
        positives.add(Pair.of(Triple.of("minecraft:regeneration",                           900, 0), 1));
    }

    private void addEffects(Player player) { ModItems.addEffects(player, positives); }
    private void addSideEffects(Player player) {
        if (player.isLocalPlayer()) return;

        Random rnd = new Random();
        // 50% chance of instant death
        if (rnd.nextInt(2) == 0) {
            negatives.add(Pair.of(Triple.of("minecraft:instant_damage", 5, 10), 900));
        }

        ModItems.addSideEffects(player, negatives);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if (!Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("misc.cpd_stimulators.press_shift"));
            return;
        }
        ModItems.appendApplicableEffectToTooltip(tooltipComponents, positives, false);

        tooltipComponents.add(Component.literal("§o§7" + (Component.translatable("misc.cpd_stimulators.delay", "15m").getString().split(";")[0] + ":")));
        tooltipComponents.add(Component.translatable("misc.cpd_stimulators.chance_of_death", Component.literal("50%")));

//        ModItems.appendHoverText(stack, context, tooltipComponents, tooltipFlag, positives, negatives);
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
