package net.chronos.cpd_stimulators.datagen;

import net.chronos.cpd_stimulators.recipe.CentrifugeRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class CentrifugeRecipeBuilder implements RecipeBuilder {
    private final Ingredient inputItem1;
    private final Ingredient inputItem2;
    private final Ingredient inputItem3;
    private final ItemStack output;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;

    public CentrifugeRecipeBuilder(Ingredient inputItem1, Ingredient inputItem2, Ingredient inputItem3, ItemStack output) {
        this.inputItem1 = inputItem1;
        this.inputItem2 = inputItem2;
        this.inputItem3 = inputItem3;
        this.output = output;
    }
    @Override
    public RecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
        this.criteria.put(s, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String s) {
        this.group = group;
        return this;
    }

    @Override
    public Item getResult() {
        return this.output.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        Advancement.Builder advancement = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation))
                .rewards(AdvancementRewards.Builder.recipe(resourceLocation))
                .requirements(AdvancementRequirements.Strategy.OR);

        this.criteria.forEach(advancement::addCriterion);

        CentrifugeRecipe recipe = new CentrifugeRecipe(this.inputItem1, this.inputItem2, this.inputItem3, this.output);

        recipeOutput.accept(resourceLocation, recipe, advancement.build(resourceLocation.withPrefix("recipes/")));
    }
}
