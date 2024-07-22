package net.chronos.cpd_stimulators.effect;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.effect.custom.*;
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
    public static final DeferredHolder<MobEffect, MobEffect> CONFUSED = MOB_EFFECTS.register("confused",
            () -> new Confused(MobEffectCategory.HARMFUL, 0xff7257));
    public static final DeferredHolder<MobEffect, MobEffect> STRESS_RESISTANCE = MOB_EFFECTS.register("stress_resistance",
            () -> new StressResistance(MobEffectCategory.BENEFICIAL, 0x6374ce));
    public static final DeferredHolder<MobEffect, MobEffect> ANTIDOTE = MOB_EFFECTS.register("antidote",
            () -> new Antidote(MobEffectCategory.BENEFICIAL, 0x96f605));

    public static final DeferredHolder<MobEffect, MobEffect> INSUPERABILITY = MOB_EFFECTS.register("insuperability",
            () -> new Insuperability(MobEffectCategory.HARMFUL, 0x000000));
    public static final DeferredHolder<MobEffect, MobEffect> OVERLOAD = MOB_EFFECTS.register("overload",
            () -> new Overload(MobEffectCategory.HARMFUL, 0x6374CE)); // TODO: change color
    public static final DeferredHolder<MobEffect, MobEffect> PHOTOPSIA = MOB_EFFECTS.register("photopsia",
            () -> new Photopsia(MobEffectCategory.HARMFUL, 0x6374CE)); // TODO: change color
    public static final DeferredHolder<MobEffect, MobEffect> INCREASED_CARRYING_CAPACITY = MOB_EFFECTS.register("increased_carrying_capacity",
            () -> new Blank(MobEffectCategory.BENEFICIAL, 0x6374CE)); // TODO: change color

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
