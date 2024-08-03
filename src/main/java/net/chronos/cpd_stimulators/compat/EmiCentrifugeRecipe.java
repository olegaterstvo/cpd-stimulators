package net.chronos.cpd_stimulators.compat;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.recipe.CentrifugeRecipe;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EmiCentrifugeRecipe implements EmiRecipe {
    private final ResourceLocation id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public EmiCentrifugeRecipe(CentrifugeRecipe recipe) {
        this.input = List.of(
                EmiIngredient.of(recipe.getInputItem1()),
                EmiIngredient.of(recipe.getInputItem2()),
                EmiIngredient.of(recipe.getInputItem3())
        );
        this.output = List.of(EmiStack.of(recipe.getResult()));
        this.id = ResourceLocation.fromNamespaceAndPath(CPDStimulators.MOD_ID, recipe.getResult().getItem().getDescriptionId());
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EmiPluginCPD.CPD_CATEGORY;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return input;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return output;
    }

    @Override
    public int getDisplayWidth() {
        return 100;
    }

    @Override
    public int getDisplayHeight() {
        return 18;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        // Add an arrow texture to indicate processing
        widgets.addTexture(EmiTexture.SHAPELESS, 60, 2);

        // Adds an input slot on the left
        widgets.addSlot(input.get(0), 0, 0);
        widgets.addSlot(input.get(1), 20, 0);
        widgets.addSlot(input.get(2), 40, 0);

        // Adds an output slot on the right
        // Note that output slots need to call `recipeContext` to inform EMI about their recipe context
        // This includes being able to resolve recipe trees, favorite stacks with recipe context, and more
        widgets.addSlot(output.get(0), 80, 0).recipeContext(this);
    }
}
