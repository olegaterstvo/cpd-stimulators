package net.chronos.cpd_stimulators.block.entity;

import net.chronos.cpd_stimulators.recipe.CentrifugeRecipe;
import net.chronos.cpd_stimulators.recipe.ModRecipes;
import net.chronos.cpd_stimulators.screen.CentrifugeMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class CentrifugeBlockEntity extends BlockEntity implements MenuProvider {
    public static final int NUMBER_OF_SLOTS = 4;
    public final ItemStackHandler itemHandler = new ItemStackHandler(NUMBER_OF_SLOTS){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    private static final int INPUT_SLOT_LEFT = 0;
    private static final int INPUT_SLOT_CENTER = 1;
    private static final int INPUT_SLOT_RIGHT = 2;
    private static final int OUTPUT_SLOT = 3;

//    public BlockCapabilityCache<IItemHandler, @Nullable Direction> capCache;

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public CentrifugeBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.CENTRIFUGE_BLOCK_ENTITY.get(), pos, blockState);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> CentrifugeBlockEntity.this.progress;
                    case 1 -> CentrifugeBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> CentrifugeBlockEntity.this.progress = value;
                    case 1 -> CentrifugeBlockEntity.this.maxProgress = value;
                };
            }

            @Override
            public int getCount() {
                return NUMBER_OF_SLOTS;
            }
        };
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }


    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void invalidateCapabilities() {
        super.invalidateCapabilities();
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("block.cpd_stimulators.centrifuge");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new CentrifugeMenu(i, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory", itemHandler.serializeNBT(registries));
        tag.putInt("centrifuge.progress", progress);


        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("centrifuge.progress");
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    public ItemStack getRenderStack(){
//        if(itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty()){
//            return itemHandler.getStackInSlot(INPUT_SLOT_CENTER);
//        } else {
            return itemHandler.getStackInSlot(OUTPUT_SLOT);
//        }
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private void resetProgress() {
        progress = 0;
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        ItemStack result = getRecipe();
        if (result != ItemStack.EMPTY && canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem())){
            increaseCraftingProgress();
            setChanged(level, blockPos, blockState);
//            level.playSound(level.getEntity(0), blockPos, ModSounds.CENTRIFUGE.get(), SoundSource.BLOCKS, 1f, 1f);
            if(hasProgressFinished()){
                craftItem(result);
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void craftItem(ItemStack result) {
        this.itemHandler.extractItem(INPUT_SLOT_LEFT, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_CENTER, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_RIGHT, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT,
                new ItemStack(result.getItem(), this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }
    private ItemStack getRecipe() {
        ServerLevel serverLevel = (ServerLevel) getLevel();
        if (serverLevel == null) return ItemStack.EMPTY;

        RecipeManager recipes = serverLevel.getRecipeManager();
        CraftingInput input = CraftingInput.of(3,1, List.of(itemHandler.getStackInSlot(INPUT_SLOT_LEFT), itemHandler.getStackInSlot(INPUT_SLOT_CENTER), itemHandler.getStackInSlot(INPUT_SLOT_RIGHT)));

        Optional<RecipeHolder<CentrifugeRecipe>> optional = recipes.getRecipeFor(
                // The recipe type.
                ModRecipes.CENTRIFUGE.get(),
                input,
                level
        );
        ItemStack result = optional
                .map(RecipeHolder::value)
                .map(e -> e.assemble(input, level.registryAccess()))
                .orElse(ItemStack.EMPTY);
        return result;

    }


}
