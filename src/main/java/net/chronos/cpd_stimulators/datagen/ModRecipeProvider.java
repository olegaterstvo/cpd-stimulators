package net.chronos.cpd_stimulators.datagen;

import net.chronos.cpd_stimulators.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
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

    }
}
