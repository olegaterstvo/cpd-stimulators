package net.chronos.cpd_stimulators.effect;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.effect.custom.Exhaustion;
import net.chronos.cpd_stimulators.effect.custom.Vulnerability;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.world.effect.MobEffectCategory;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, CPDStimulators.MOD_ID);

    public static final DeferredHolder<MobEffect, MobEffect> EXHAUSTION = MOB_EFFECTS.register("exhaustion",
            () -> new Exhaustion(MobEffectCategory.HARMFUL, 0x111111));
    public static final DeferredHolder<MobEffect, MobEffect> VULNERABILITY = MOB_EFFECTS.register("vulnerability",
            () -> new Vulnerability(MobEffectCategory.HARMFUL, 0xff0000));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
