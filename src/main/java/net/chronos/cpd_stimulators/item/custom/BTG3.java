package net.chronos.cpd_stimulators.item.custom;

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

public class BTG3 extends Item {
    public BTG3(Properties properties) {
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
        player.addEffect(new MobEffectInstance(MobEffects.LUCK, 4800, 2));
        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 4800, 0));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 4800, 0));
        player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 4800, 0));
    }
    private void addSideEffects(Player player) {
        ModPlayerEvent.queueWork(2400, () -> {
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER,2400,0));
        });
        ModPlayerEvent.queueWork(4400, () -> {
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION,400,0));
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
