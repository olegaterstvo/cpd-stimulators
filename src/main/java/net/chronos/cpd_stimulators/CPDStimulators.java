package net.chronos.cpd_stimulators;

import net.chronos.cpd_stimulators.effect.ModEffects;
import net.chronos.cpd_stimulators.item.ModItemGroup;
import net.chronos.cpd_stimulators.item.ModItems;
import net.chronos.cpd_stimulators.sound.ModSounds;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(CPDStimulators.MOD_ID)
public class CPDStimulators {
    public static final String MOD_ID = "cpd_stimulators";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CPDStimulators(IEventBus eventBus, ModContainer modContainer){
        ModItemGroup.register(eventBus);
        ModItems.register(eventBus);
        ModEffects.register(eventBus);
        ModSounds.register(eventBus);
    }
}