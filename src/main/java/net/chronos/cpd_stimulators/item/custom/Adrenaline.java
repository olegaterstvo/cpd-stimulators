package net.chronos.cpd_stimulators.item.custom;

import net.chronos.cpd_stimulators.effect.ModEffects;
import net.chronos.cpd_stimulators.event.ModPlayerEvent;
import net.chronos.cpd_stimulators.sound.ModSounds;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.List;

public class Adrenaline extends Item {
    public Adrenaline(Properties properties) {
        super(properties);
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

    private void addEffects(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 0));
        player.addEffect(new MobEffectInstance(ModEffects.STRESS_RESISTANCE.getDelegate(), 1200, 0));
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 300, 2));
    }
    private void addSideEffects(Player player) {
        ModPlayerEvent.queueWork(1000, () -> {
            player.addEffect(new MobEffectInstance(ModEffects.VULNERABILITY.getDelegate(), 1200, 0));
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0));
        });
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if (!Screen.hasShiftDown()){
            tooltipComponents.add(Component.translatable("misc.cpd_stimulators.press_shift"));
            return;
        }
        tooltipComponents.add(Component.literal("§o§7"+"60" + Component.translatable("misc.cpd_stimulators.duration").getString()));
        tooltipComponents.add(Component.literal("   §b"+Component.translatable("effect.minecraft.speed").getString()+" "));
        tooltipComponents.add(Component.literal("   §b"+Component.translatable("effect.minecraft.strength").getString()+" "));
        tooltipComponents.add(Component.literal("   §b"+Component.translatable("effect.cpd_stimulators.stress_resistance").getString()+" "));

        tooltipComponents.add(Component.literal("§o§7"+"15" + Component.translatable("misc.cpd_stimulators.duration").getString()));
        tooltipComponents.add(Component.literal("   §b"+Component.translatable("effect.minecraft.regeneration").getString()+" III"));

        tooltipComponents.add(Component.literal("§o§7"+"1" + Component.translatable("misc.cpd_stimulators.delay").getString()
                + "60" + Component.translatable("misc.cpd_stimulators.duration").getString()));
        tooltipComponents.add(Component.literal("   §c"+Component.translatable("effect.cpd_stimulators.vulnerability").getString()+" "));

        tooltipComponents.add(Component.literal("§o§7"+"50" + Component.translatable("misc.cpd_stimulators.delay").getString()
                + "30" + Component.translatable("misc.cpd_stimulators.duration").getString()));
        tooltipComponents.add(Component.literal("   §c"+Component.translatable("effect.minecraft.hunger").getString()+" "));
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
