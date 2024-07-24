package net.chronos.cpd_stimulators.recipe;

import net.chronos.cpd_stimulators.CPDStimulators;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, CPDStimulators.MOD_ID);

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, CPDStimulators.MOD_ID);



    public static final Supplier<RecipeSerializer<CentrifugeRecipe>> CENTRIFUGE_SERIALIZER =
            RECIPE_SERIALIZERS.register("right_click_block", CentrifugeSerializer::new);


    public static final Supplier<RecipeType<CentrifugeRecipe>> CENTRIFUGE =
            RECIPE_TYPES.register(
                    "centrifuge",
                    // We need the qualifying generic here due to generics being generics.
                    () -> RecipeType.<CentrifugeRecipe>simple(ResourceLocation.fromNamespaceAndPath(CPDStimulators.MOD_ID, "right_click_block"))
            );
}
