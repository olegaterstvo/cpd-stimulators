package net.chronos.cpd_stimulators.event;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@EventBusSubscriber(modid = CPDStimulators.MOD_ID)
public class ModPlayerEvent {
    private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();
    public static void queueWork(int tick, Runnable action) {
        workQueue.add(new AbstractMap.SimpleEntry<>(action, tick));
    }

    @SubscribeEvent
    public static void tick(PlayerTickEvent.Post event) {
        if (workQueue.isEmpty()) return;

        List<AbstractMap.SimpleEntry<Runnable, Integer>> actions = new ArrayList<>();
        workQueue.forEach(work -> {
            work.setValue(work.getValue() - 1);
            if (work.getValue() == 0)
                actions.add(work);
        });
        actions.forEach(e -> e.getKey().run());
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        workQueue.clear();
    }
    
    public static void addEffect(Player player, Holder<MobEffect> effect, int duration, int amplifier) {
        boolean ambient = false;
        boolean visible = false;
        boolean showIcon = false;

        player.addEffect(new MobEffectInstance(effect, duration, amplifier, ambient, visible, showIcon));
    }
}
