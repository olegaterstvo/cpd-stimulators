package net.chronos.cpd_stimulators.event;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Optional;

@EventBusSubscriber(modid = CPDStimulators.MOD_ID)
public class DeferredMobEffect {
    public static void add(Player player, String params) {
        if (player.isLocalPlayer()) return;

        for (int i = 0; i < 50; i++) {
            if (player.getPersistentData().contains("deferred_" + i)) continue;
            player.getPersistentData().putString("deferred_" + i, params);
//            CPDStimulators.LOGGER.info(params);
            break;
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity().isLocalPlayer()) return;

        for (int i = 0; i < event.getEntity().getPersistentData().size(); i++) {
            if (!event.getEntity().getPersistentData().contains("deferred_" + i)) continue;
            String[] effectData = event.getEntity().getPersistentData().getString("deferred_" + i).split("@");

            int tick = Integer.parseInt(effectData[0]);
//            CPDStimulators.LOGGER.info(i + ": (" + tick + ") " + event.getEntity().getPersistentData().getString("deferred_" + i) + "");
            String effect = effectData[1];
            int duration = Integer.parseInt(effectData[2]);
            int amplifier = Integer.parseInt(effectData[3]);

            event.getEntity().getPersistentData().putString("deferred_" + i, tick - 1 + "@" + effect + "@" + duration + "@" + amplifier);

            if (tick == 0) {
                event.getEntity().getPersistentData().remove("deferred_" + i);
                Optional<Holder.Reference<MobEffect>> mobEffect = BuiltInRegistries.MOB_EFFECT.getHolder(ResourceLocation.parse(effect));
                mobEffect.ifPresent(mobEffectReference -> event.getEntity().addEffect(new MobEffectInstance(mobEffectReference, duration, amplifier, false, false, false)));
            }
        }
    }
}
