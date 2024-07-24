package net.chronos.cpd_stimulators.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class CentrifugeSerializer implements RecipeSerializer<CentrifugeRecipe> {
    public static final MapCodec<CentrifugeRecipe> CODEC = RecordCodecBuilder.mapCodec(inst ->
            inst.group(
                    Ingredient.CODEC.fieldOf("input_1").forGetter(CentrifugeRecipe::getInputItem1),
                    Ingredient.CODEC.fieldOf("input_2").forGetter(CentrifugeRecipe::getInputItem2),
                    Ingredient.CODEC.fieldOf("input_3").forGetter(CentrifugeRecipe::getInputItem3),
                    ItemStack.CODEC.fieldOf("result").forGetter(CentrifugeRecipe::getResult)
            ).apply(inst, CentrifugeRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, CentrifugeRecipe> STREAM_CODEC =
            StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, CentrifugeRecipe::getInputItem1,
                Ingredient.CONTENTS_STREAM_CODEC, CentrifugeRecipe::getInputItem2,
                Ingredient.CONTENTS_STREAM_CODEC, CentrifugeRecipe::getInputItem3,
                ItemStack.STREAM_CODEC, CentrifugeRecipe::getResult,
                CentrifugeRecipe::new
            );

    @Override
    public MapCodec<CentrifugeRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, CentrifugeRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}































//import com.mojang.serialization.MapCodec;
//import net.chronos.cpd_stimulators.CPDStimulators;
//import net.minecraft.network.RegistryFriendlyByteBuf;
//import net.minecraft.network.codec.StreamCodec;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.crafting.RecipeSerializer;
//
//
//import java.lang.reflect.Array;
//import java.util.Arrays;
//
//public class CentrifugeSerializer implements RecipeSerializer<CentrifugeRecipe> {
//    public static final CentrifugeSerializer INSTANCE = new CentrifugeSerializer();
//
////    public static final MapCodec<CentrifugeRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
////            ItemStack.CODEC.fieldOf("result").forGetter(CentrifugeRecipe::getResult),
////            Ingredient.CODEC.listOf().listOf().fieldOf("ingredients")
////                    .xmap(lists -> lists.stream()
////                                    .map(ingredients -> ingredients.toArray(Ingredient[]::new))
////                                    .toArray(Ingredient[][]::new),
////                            ingredients -> Arrays.stream(ingredients)
////                                    .map(ingredients1 -> Arrays.stream(ingredients1).toList())
////                                    .toList())
////                    .forGetter(CentrifugeRecipe::getInputItem))
////                    .apply(inst, (itemStack, ingredients) -> new CentrifugeRecipe(ingredients, itemStack)));
//
//////    public static final StreamCodec<RegistryFriendlyByteBuf, CentrifugeRecipe> REGISTRY_STREAM_CODEC =
//////            StreamCodec.composite(
//////                    // Note that ByteBuf stream codecs can be used here
//////                    ByteBufCodecs.registry(Registries.ITEM), CentrifugeRecipe::getIngredients,
//////                    ByteBufCodecs.registry(Registries.ITEM), CentrifugeRecipe::getResult,
//////                    CentrifugeRecipe::new
//////            );
//////    public static final StreamCodec<RegistryFriendlyByteBuf, CentrifugeRecipe> STREAM_CODEC =
//////            StreamCodec.composite(
//////                    ByteBufCodecs.registry(Registries.ITEM),
//////                    CentrifugeRecipe::getIngredients,
//////                    Ingredient.CONTENTS_STREAM_CODEC,
//////                    CentrifugeRecipe::getResult,
//////                    CentrifugeRecipe::new
//////            );
//////
//////    public static final StreamCodec<RegistryFriendlyByteBuf, CentrifugeRecipe> STREAM_CODEC =
//////            StreamCodec.composite(
//////                    ByteBufCodecs.idMapper(Block.BLOCK_STATE_REGISTRY),
//////                    CentrifugeRecipe::getInputState,
//////                    Ingredient.CONTENTS_STREAM_CODEC, CentrifugeRecipe::getInputItem,
//////                    ItemStack.STREAM_CODEC,
//////                    CentrifugeRecipe::getResult,
//////                    CentrifugeRecipe::new
//////            );
//
//
//
//
//    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(CPDStimulators.MOD_ID, "centrifuge");
//
//
//    @Override
//    public MapCodec<CentrifugeRecipe> codec() {
////        return CODEC;
//        return null;
//    }
//
//    @Override
//    public StreamCodec<RegistryFriendlyByteBuf, CentrifugeRecipe> streamCodec() {
//        return null;
//    }
//}
