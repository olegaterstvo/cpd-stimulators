package net.chronos.cpd_stimulators.compat;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import net.chronos.cpd_stimulators.CPDStimulators;
import net.chronos.cpd_stimulators.block.ModBlocks;
import net.chronos.cpd_stimulators.recipe.CentrifugeRecipe;
import net.chronos.cpd_stimulators.recipe.ModRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

@EmiEntrypoint
public class EmiPluginCPD implements EmiPlugin {
    public static final ResourceLocation CENTRIFUGE_SPRITE_SHEET = ResourceLocation.fromNamespaceAndPath(CPDStimulators.MOD_ID, "textures/gui/centrifuge.png");
    public static final EmiStack EMI_CENTRIFUGE = EmiStack.of(ModBlocks.CENTRIFUGE.asItem());
    public static final EmiRecipeCategory CPD_CATEGORY
            = new EmiRecipeCategory(ResourceLocation.fromNamespaceAndPath(CPDStimulators.MOD_ID, "centrifuge"), EMI_CENTRIFUGE, new EmiTexture(CENTRIFUGE_SPRITE_SHEET, 0, 0, 16, 16));

    @Override
    public void register(EmiRegistry registry) {

        // Tell EMI to add a tab for your category
        registry.addCategory(CPD_CATEGORY);

        // Add all the workstations your category uses
        registry.addWorkstation(CPD_CATEGORY, EMI_CENTRIFUGE);

        RecipeManager manager = registry.getRecipeManager();

        // Use vanilla's concept of your recipes and pass them to your EmiRecipe representation
        for (RecipeHolder<CentrifugeRecipe> recipe : manager.getAllRecipesFor(ModRecipes.CENTRIFUGE.get())) {
            registry.addRecipe(new EmiCentrifugeRecipe(recipe.value()));
        }
    }
}
