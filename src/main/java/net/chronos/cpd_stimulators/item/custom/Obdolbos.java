package net.chronos.cpd_stimulators.item.custom;

import net.chronos.cpd_stimulators.effect.ModEffects;
import net.chronos.cpd_stimulators.event.ModPlayerEvent;
import net.chronos.cpd_stimulators.sound.ModSounds;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Obdolbos extends Item {
    public Obdolbos(Properties properties) {
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
        Random rnd = new Random();
        // 25% of happening for each effect
        if (rnd.nextInt(4) == 0) player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 12000, 0));
        if (rnd.nextInt(4) == 0) player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 12000, 0));
        if (rnd.nextInt(4) == 0) player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 12000, 0));
        if (rnd.nextInt(4) == 0) player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 12000, 0));
        if (rnd.nextInt(4) == 0) player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 12000, 0));
        if (rnd.nextInt(4) == 0) player.addEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 12000, 0));
        if (rnd.nextInt(4) == 0) player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 12000, 0));
        if (rnd.nextInt(4) == 0) player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 12000, 0));
        if (rnd.nextInt(4) == 0) player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 12000, 0));
        if (rnd.nextInt(4) == 0) player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 12000, 0));
        if (rnd.nextInt(4) == 0) player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 12000, 0));
        if (rnd.nextInt(4) == 0) player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 12000, 0));
        if (rnd.nextInt(4) == 0) player.addEffect(new MobEffectInstance(MobEffects.JUMP, 12000, 0));
        if (rnd.nextInt(4) == 0) player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 12000, 0));
        if (rnd.nextInt(4) == 0) player.addEffect(new MobEffectInstance(ModEffects.STRESS_RESISTANCE.getDelegate(), 12000, 0));



    }
    private void addSideEffects(Player player) {
        ModPlayerEvent.queueWork(20, () -> {
            Random rnd = new Random();
            if (rnd.nextInt(4) == 0) player.addEffect(new MobEffectInstance(MobEffects.HUNGER,12000,1));
            if (rnd.nextInt(4) == 0) player.addEffect(new MobEffectInstance(MobEffects.CONFUSION,12000,0));
            if (rnd.nextInt(4) == 0) player.addEffect(new MobEffectInstance(MobEffects.POISON,12000,0));
            if (rnd.nextInt(4) == 0) player.addEffect(new MobEffectInstance(ModEffects.VULNERABILITY.getDelegate(),12000,0));
            if (rnd.nextInt(4) == 0) player.addEffect(new MobEffectInstance(ModEffects.EXHAUSTION.getDelegate(),12000,0));
            if (rnd.nextInt(4) == 0) player.addEffect(new MobEffectInstance(MobEffects.DARKNESS,12000,0));
            if (rnd.nextInt(4) == 0) player.addEffect(new MobEffectInstance(MobEffects.UNLUCK,12000,0));
        });
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
