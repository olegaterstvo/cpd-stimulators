package net.chronos.cpd_stimulators.config;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.effect.custom.Overload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = CPDStimulators.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModCommonConfigs {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.ConfigValue<Float> OVERLOAD_MAX_WEIGHT;
    public static final ModConfigSpec.ConfigValue<Float> OVERLOAD_STOP_SPRINTING_THRESHOLD;
    public static final ModConfigSpec.ConfigValue<Integer> CONFUSED_CHANCE_OF_ACQUISITION;
    public static final ModConfigSpec.ConfigValue<Integer> CONFUSED_DURATION_MIN;
    public static final ModConfigSpec.ConfigValue<Integer> CONFUSED_DURATION_MAX;

    static{
        BUILDER.push("Configs for CPD: Stimulators");


        // TODO: add descriptions
        OVERLOAD_MAX_WEIGHT = BUILDER.comment("description. Default=27648.0").define("overload_max_weight", 27648f);
        OVERLOAD_STOP_SPRINTING_THRESHOLD = BUILDER.comment("description. Default=13824.0").define("overload_stop_sprinting_threshold", 13824f);

        CONFUSED_CHANCE_OF_ACQUISITION = BUILDER.comment("description%. Default=25").define("confused_chance_of_acquisition", 25);
        CONFUSED_DURATION_MIN = BUILDER.comment("description in ticks (1s=20ticks). Default=60").define("confused_duration_min", 60);
        CONFUSED_DURATION_MAX = BUILDER.comment("description in ticks (1s=20ticks). Default=140").define("confused_duration_max", 140);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    // https://forge.gemwire.uk/wiki/Stages_of_Modloading
    @SubscribeEvent
    public static void onCommonSetup (FMLCommonSetupEvent event){
        CPDStimulators.LOGGER.info("COMMON SETUP");

        // Configs are not available during mod initialisation, thus overriding values on common setup
        Overload.MAX_WEIGHT = OVERLOAD_MAX_WEIGHT.get();
        Overload.STOP_SPRINTING_THRESHOLD = OVERLOAD_STOP_SPRINTING_THRESHOLD.get();
    }
}

