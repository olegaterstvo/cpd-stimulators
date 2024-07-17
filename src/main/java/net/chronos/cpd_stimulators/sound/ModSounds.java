package net.chronos.cpd_stimulators.sound;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, CPDStimulators.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> APPLY_INJECTOR = SOUND_EVENTS.register(
            "apply_injector", // must match the resource location on the next line
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(CPDStimulators.MOD_ID, "apply_injector"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> SANITAR_DEATH = SOUND_EVENTS.register(
            "sanitar_death_1", // must match the resource location on the next line
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(CPDStimulators.MOD_ID, "sanitar_death"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> SANITAR_HURT = SOUND_EVENTS.register(
            "sanitar_hurt_1", // must match the resource location on the next line
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(CPDStimulators.MOD_ID, "sanitar_hurt"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> SANITAR_MUTTER = SOUND_EVENTS.register(
            "sanitar_mutter_1", // must match the resource location on the next line
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(CPDStimulators.MOD_ID, "sanitar_mutter"))
    );

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
