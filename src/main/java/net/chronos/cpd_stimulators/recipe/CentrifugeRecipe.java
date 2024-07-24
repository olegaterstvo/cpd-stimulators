package net.chronos.cpd_stimulators.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CentrifugeRecipe implements Recipe<CraftingInput> {
    private final Ingredient[] inputItems;
    private final ItemStack output;

    public CentrifugeRecipe(Ingredient[] inputItems, ItemStack output) {
        this.inputItems = inputItems;
        this.output = output;
//        inp = inputItems.getFirst();
//        out = output;
    }


    @Override
    public boolean matches(CraftingInput craftingInput, Level level) {
        return inputItems[0].test(craftingInput.getItem(0));
    }

    @Override
    public ItemStack assemble(CraftingInput craftingInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output.copy();
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.CENTRIFUGE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipes.CENTRIFUGE.get();
    }

    public ItemStack getResult() {
        return this.output;
    }
    public Ingredient[] getInputItem() {
        return this.inputItems;
    }



    public static class Type implements RecipeType<CentrifugeRecipe>{
        public static final Type INSTANCE = new Type();
        public static final String ID = "centrifuge";
    }



}

