package net.chronos.cpd_stimulators.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ModServerConfigs {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.ConfigValue<Integer> OVERLOAD_MAX_WEIGHT;
    public static final ModConfigSpec.ConfigValue<Integer> OVERLOAD_STOP_SPRINTING_THRESHOLD;
    public static final ModConfigSpec.ConfigValue<Integer> CONFUSED_CHANCE_OF_ACQUISITION;
    public static final ModConfigSpec.ConfigValue<Integer> CONFUSED_DURATION_MIN;
    public static final ModConfigSpec.ConfigValue<Integer> CONFUSED_DURATION_MAX;
    public static final ModConfigSpec.ConfigValue<Boolean> AIRDROP_ANNOUNCEMENT;
    public static final ModConfigSpec.ConfigValue<Integer> AIRDROP_ANNOUNCEMENT_X_MINUTES_BEFORE;
    public static final ModConfigSpec.ConfigValue<Integer> AIRDROP_EVENT_EVERY_X_MINUTES;
    public static final ModConfigSpec.ConfigValue<Integer> AIRDROP_WITHIN_RADIUS_OF_POLYGON;
    public static final ModConfigSpec.ConfigValue<Integer> AIRDROP_WITHIN_RADIUS_OF_POINT;

    static{
        BUILDER.push("Configs for CPD: Stimulators");


        // TODO: add descriptions
        OVERLOAD_MAX_WEIGHT = BUILDER
                .comment(" Max weight your character can carry. Default=27648")
                .define("overload_max_weight", 27648);
        OVERLOAD_STOP_SPRINTING_THRESHOLD = BUILDER.
                comment(" Max weight your character can sprint with. Default=13824")
                .define("overload_stop_sprinting_threshold", 13824);

        CONFUSED_CHANCE_OF_ACQUISITION = BUILDER
                .comment(" %Chance of getting confused after taking explosion damage. Default=25")
                .define("confused_chance_of_acquisition", 25);
        CONFUSED_DURATION_MIN = BUILDER
                .comment(" Minimum possible duration of the effect in ticks (1s=20ticks). Default=60")
                .define("confused_duration_min", 60);
        CONFUSED_DURATION_MAX = BUILDER
                .comment(" Maximum possible duration of the effect in ticks (1s=20ticks). Default=140")
                .define("confused_duration_max", 140);

        AIRDROP_ANNOUNCEMENT = BUILDER
                .comment(" Should the airdrop be announced in chat")
                .define("airdrop_announcement", true);
        AIRDROP_ANNOUNCEMENT_X_MINUTES_BEFORE = BUILDER
                .comment(" Time between announcement and drop in minutes")
                .define("airdrop_announcement_x_minutes_before", 5);
        AIRDROP_EVENT_EVERY_X_MINUTES = BUILDER
                .comment(" Time between airdrops")
                .define("airdrop_event_every_x_minutes", 60);
        AIRDROP_WITHIN_RADIUS_OF_POLYGON = BUILDER
                .comment(" description")
                .define("airdrop_within_radius_of_polygon", 500);
        AIRDROP_WITHIN_RADIUS_OF_POINT = BUILDER
                .comment(" description")
                .define("airdrop_within_radius_of_point", 50);

//        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}

