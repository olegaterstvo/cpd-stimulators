package net.chronos.cpd_stimulators;

import net.chronos.cpd_stimulators.block.ModBlocks;
import net.chronos.cpd_stimulators.block.entity.ModBlockEntities;
import net.chronos.cpd_stimulators.config.ModServerConfigs;
import net.chronos.cpd_stimulators.effect.ModEffects;
import net.chronos.cpd_stimulators.entity.ModEntities;
import net.chronos.cpd_stimulators.item.ModItemGroup;
import net.chronos.cpd_stimulators.item.ModItems;
import net.chronos.cpd_stimulators.recipe.ModRecipes;
import net.chronos.cpd_stimulators.screen.ModMenuTypes;
import net.chronos.cpd_stimulators.sound.ModSounds;
import net.chronos.cpd_stimulators.villager.ModVillagers;
import net.neoforged.fml.config.ModConfig;
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
        modContainer.registerConfig(ModConfig.Type.SERVER, ModServerConfigs.SPEC);

        ModItemGroup.register(eventBus);
        ModItems.register(eventBus);
        ModEffects.register(eventBus);
        ModSounds.register(eventBus);
        ModVillagers.register(eventBus);
        ModEntities.register(eventBus);
        ModBlocks.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModMenuTypes.register(eventBus);
        ModRecipes.register(eventBus);
    }
}