package net.chronos.cpd_stimulators.datagen;

import net.chronos.cpd_stimulators.block.ModBlocks;
import net.chronos.cpd_stimulators.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CENTRIFUGE.get())
                .pattern(" B ")
                .pattern("BDB")
                .pattern("SCS")
                .define('B', Items.GLASS_BOTTLE)
                .define('D', Items.DIAMOND)
                .define('S', Items.SMOOTH_STONE_SLAB)
                .define('C', Items.STONECUTTER)
                .unlockedBy(getHasName(Items.STONECUTTER), has(Items.STONECUTTER))
                .save(recipeOutput);

        new CentrifugeRecipeBuilder(
                Ingredient.of(Items.SUGAR),
                Ingredient.of(Items.ROTTEN_FLESH),
                Ingredient.of(Items.AIR),
                new ItemStack(ModItems.ADRENALINE_INJECTOR.get())
        ).unlockedBy("has_centrifuge", has(ModBlocks.CENTRIFUGE.asItem())).save(recipeOutput);

        new CentrifugeRecipeBuilder(
                Ingredient.of(Items.GOLDEN_CARROT),
                Ingredient.of(Items.SUGAR),
                Ingredient.of(Items.AIR),
                new ItemStack(ModItems.AHF1M_INJECTOR.get())
        ).unlockedBy("has_centrifuge", has(ModBlocks.CENTRIFUGE.asItem())).save(recipeOutput);

        new CentrifugeRecipeBuilder(
                Ingredient.of(Items.DRIED_KELP),
                Ingredient.of(Items.SUGAR),
                Ingredient.of(Items.AIR),
                new ItemStack(ModItems.BTG2A2_INJECTOR.get())
        ).unlockedBy("has_centrifuge", has(ModBlocks.CENTRIFUGE.asItem())).save(recipeOutput);

        new CentrifugeRecipeBuilder(
                Ingredient.of(Items.LILY_PAD),
                Ingredient.of(Items.SUGAR),
                Ingredient.of(Items.COCOA_BEANS),
                new ItemStack(ModItems.BTG3_INJECTOR.get())
        ).unlockedBy("has_centrifuge", has(ModBlocks.CENTRIFUGE.asItem())).save(recipeOutput);

        new CentrifugeRecipeBuilder(
                Ingredient.of(Items.GOLDEN_APPLE),
                Ingredient.of(Items.GLOW_BERRIES),
                Ingredient.of(Items.CHORUS_FRUIT),
                new ItemStack(ModItems.ETGC_INJECTOR.get())
        ).unlockedBy("has_centrifuge", has(ModBlocks.CENTRIFUGE.asItem())).save(recipeOutput);

        new CentrifugeRecipeBuilder(
                Ingredient.of(Items.SUGAR),
                Ingredient.of(Items.ROTTEN_FLESH),
                Ingredient.of(Items.ROTTEN_FLESH),
                new ItemStack(ModItems.L1_INJECTOR.get())
        ).unlockedBy("has_centrifuge", has(ModBlocks.CENTRIFUGE.asItem())).save(recipeOutput);

        new CentrifugeRecipeBuilder(
                Ingredient.of(Items.COOKED_SALMON),
                Ingredient.of(Items.TURTLE_EGG),
                Ingredient.of(Items.NETHER_WART),
                new ItemStack(ModItems.MELDONIN_INJECTOR.get())
        ).unlockedBy("has_centrifuge", has(ModBlocks.CENTRIFUGE.asItem())).save(recipeOutput);

        new CentrifugeRecipeBuilder(
                Ingredient.of(Items.POPPY),
                Ingredient.of(Items.POPPY),
                Ingredient.of(Items.POPPY),
                new ItemStack(ModItems.MORPHINE_INJECTOR.get())
        ).unlockedBy("has_centrifuge", has(ModBlocks.CENTRIFUGE.asItem())).save(recipeOutput);

        new CentrifugeRecipeBuilder(
                Ingredient.of(Items.RED_MUSHROOM),
                Ingredient.of(Items.BROWN_MUSHROOM),
                Ingredient.of(Items.BLAZE_POWDER),
                new ItemStack(ModItems.MULE_INJECTOR.get())
        ).unlockedBy("has_centrifuge", has(ModBlocks.CENTRIFUGE.asItem())).save(recipeOutput);

        new CentrifugeRecipeBuilder(
                Ingredient.of(Items.GLOW_INK_SAC),
                Ingredient.of(Items.POISONOUS_POTATO),
                Ingredient.of(Items.CHORUS_FRUIT),
                new ItemStack(ModItems.OBDOLBOS_INJECTOR.get())
        ).unlockedBy("has_centrifuge", has(ModBlocks.CENTRIFUGE.asItem())).save(recipeOutput);

        new CentrifugeRecipeBuilder(
                Ingredient.of(Items.BLAZE_ROD),
                Ingredient.of(Items.COCOA_BEANS),
                Ingredient.of(Items.CHORUS_FRUIT),
                new ItemStack(ModItems.OBDOLBOS2_INJECTOR.get())
        ).unlockedBy("has_centrifuge", has(ModBlocks.CENTRIFUGE.asItem())).save(recipeOutput);

        new CentrifugeRecipeBuilder(
                Ingredient.of(Items.BEETROOT),
                Ingredient.of(Items.HONEY_BOTTLE),
                Ingredient.of(Items.COCOA_BEANS),
                new ItemStack(ModItems.P22_INJECTOR.get())
        ).unlockedBy("has_centrifuge", has(ModBlocks.CENTRIFUGE.asItem())).save(recipeOutput);

        new CentrifugeRecipeBuilder(
                Ingredient.of(Items.SWEET_BERRIES),
                Ingredient.of(Items.COCOA_BEANS),
                Ingredient.of(Items.COCOA_BEANS),
                new ItemStack(ModItems.PERFOTORAN_INJECTOR.get())
        ).unlockedBy("has_centrifuge", has(ModBlocks.CENTRIFUGE.asItem())).save(recipeOutput);

        new CentrifugeRecipeBuilder(
                Ingredient.of(Items.CACTUS),
                Ingredient.of(Items.COCOA_BEANS),
                Ingredient.of(Items.SUNFLOWER),
                new ItemStack(ModItems.PNB_INJECTOR.get())
        ).unlockedBy("has_centrifuge", has(ModBlocks.CENTRIFUGE.asItem())).save(recipeOutput);

        new CentrifugeRecipeBuilder(
                Ingredient.of(Items.TORCHFLOWER),
                Ingredient.of(Items.COCOA_BEANS),
                Ingredient.of(Items.PUMPKIN_PIE),
                new ItemStack(ModItems.PROPITAL_INJECTOR.get())
        ).unlockedBy("has_centrifuge", has(ModBlocks.CENTRIFUGE.asItem())).save(recipeOutput);

        new CentrifugeRecipeBuilder(
                Ingredient.of(Items.SEA_PICKLE),
                Ingredient.of(Items.MILK_BUCKET),
                Ingredient.of(Items.COCOA_BEANS),
                new ItemStack(ModItems.SJ1_INJECTOR.get())
        ).unlockedBy("has_centrifuge", has(ModBlocks.CENTRIFUGE.asItem())).save(recipeOutput);

        new CentrifugeRecipeBuilder(
                Ingredient.of(Items.BAKED_POTATO),
                Ingredient.of(Items.COOKED_MUTTON),
                Ingredient.of(Items.COOKED_CHICKEN),
                new ItemStack(ModItems.SJ6_INJECTOR.get())
        ).unlockedBy("has_centrifuge", has(ModBlocks.CENTRIFUGE.asItem())).save(recipeOutput);

        new CentrifugeRecipeBuilder(
                Ingredient.of(Items.SNOW_BLOCK),
                Ingredient.of(Items.WATER_BUCKET),
                Ingredient.of(Items.SNOW_BLOCK),
                new ItemStack(ModItems.SJ9_INJECTOR.get())
        ).unlockedBy("has_centrifuge", has(ModBlocks.CENTRIFUGE.asItem())).save(recipeOutput);

        new CentrifugeRecipeBuilder(
                Ingredient.of(Items.POWDER_SNOW_BUCKET),
                Ingredient.of(Items.NETHER_WART),
                Ingredient.of(Items.CARROT),
                new ItemStack(ModItems.SJ12_INJECTOR.get())
        ).unlockedBy("has_centrifuge", has(ModBlocks.CENTRIFUGE.asItem())).save(recipeOutput);

        new CentrifugeRecipeBuilder(
                Ingredient.of(Items.PUFFERFISH),
                Ingredient.of(Items.CHORUS_FRUIT),
                Ingredient.of(Items.RABBIT_FOOT),
                new ItemStack(ModItems.SJ15_INJECTOR.get())
        ).unlockedBy("has_centrifuge", has(ModBlocks.CENTRIFUGE.asItem())).save(recipeOutput);

        new CentrifugeRecipeBuilder(
                Ingredient.of(Items.BRICK),
                Ingredient.of(ModItems.MAYO.get()),
                Ingredient.of(Items.NETHER_WART),
                new ItemStack(ModItems.TRIMADOL_INJECTOR.get())
        ).unlockedBy("has_centrifuge", has(ModBlocks.CENTRIFUGE.asItem())).save(recipeOutput);

        new CentrifugeRecipeBuilder(
                Ingredient.of(Items.GOLDEN_APPLE),
                Ingredient.of(Items.GOLDEN_CARROT),
                Ingredient.of(Items.COCOA_BEANS),
                new ItemStack(ModItems.ZAGUSTIN_INJECTOR.get())
        ).unlockedBy("has_centrifuge", has(ModBlocks.CENTRIFUGE.asItem())).save(recipeOutput);

        new CentrifugeRecipeBuilder(
                Ingredient.of(Items.SUNFLOWER),
                Ingredient.of(Items.SUNFLOWER),
                Ingredient.of(Items.EGG),
                new ItemStack(ModItems.MAYO.get())
        ).unlockedBy("has_centrifuge", has(ModBlocks.CENTRIFUGE.asItem())).save(recipeOutput);


    }
}
