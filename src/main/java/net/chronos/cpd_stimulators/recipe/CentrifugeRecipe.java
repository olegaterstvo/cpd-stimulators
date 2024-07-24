package net.chronos.cpd_stimulators.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CentrifugeRecipe implements Recipe<CraftingInput> {
    private final Ingredient inputItem1;
    private final Ingredient inputItem2;
    private final Ingredient inputItem3;
    private final ItemStack output;

    public CentrifugeRecipe(Ingredient inputItem1, Ingredient inputItem2, Ingredient inputItem3, ItemStack output) {
        this.inputItem1 = inputItem1;
        this.inputItem2 = inputItem2;
        this.inputItem3 = inputItem3;
        this.output = output;

    }

    public Ingredient getInputItem1() {
        return this.inputItem1;
    }

    public Ingredient getInputItem2() {
        return this.inputItem2;
    }

    public Ingredient getInputItem3() {
        return this.inputItem3;
    }
    public ItemStack getResult() {
        return this.output;
    }

    @Override
    public boolean matches(CraftingInput craftingInput, Level level) {
        List<ItemStack> items = craftingInput.items();
        if(items.size() < 3){
            for (int i=items.size(); i<3; i++){
                items.add(ItemStack.EMPTY);
            }
        }
        return  (inputItem1.test(craftingInput.getItem(0))  &&
                inputItem2.test(craftingInput.getItem(1))   &&
                inputItem3.test(craftingInput.getItem(2)))  ||
                (inputItem1.test(craftingInput.getItem(0))  &&
                inputItem2.test(craftingInput.getItem(2))   &&
                inputItem3.test(craftingInput.getItem(1)))  ||
                (inputItem1.test(craftingInput.getItem(1))  &&
                inputItem2.test(craftingInput.getItem(0))   &&
                inputItem3.test(craftingInput.getItem(2)))  ||
                (inputItem1.test(craftingInput.getItem(1))  &&
                inputItem2.test(craftingInput.getItem(2))   &&
                inputItem3.test(craftingInput.getItem(0)))  ||
                (inputItem1.test(craftingInput.getItem(2))  &&
                inputItem2.test(craftingInput.getItem(0))   &&
                inputItem3.test(craftingInput.getItem(1)))  ||
                (inputItem1.test(craftingInput.getItem(2))  &&
                inputItem2.test(craftingInput.getItem(1))   &&
                inputItem3.test(craftingInput.getItem(0)));
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


    public static class Type implements RecipeType<CentrifugeRecipe>{
        public static final Type INSTANCE = new Type();
        public static final String ID = "centrifuge";
    }



}

