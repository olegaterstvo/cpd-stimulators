package net.chronos.cpd_stimulators.block.entity;

import net.chronos.cpd_stimulators.item.ModItems;
import net.chronos.cpd_stimulators.screen.CentrifugeMenu;
import net.chronos.cpd_stimulators.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

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
//        capCache = BlockCapabilityCache.create(Capabilities.ItemHandler.BLOCK, (ServerLevel) getLevel(), getBlockPos(), Direction.NORTH);
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

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        ItemStack result = getRecipe(this.itemHandler.getStackInSlot(INPUT_SLOT_LEFT), this.itemHandler.getStackInSlot(INPUT_SLOT_CENTER), this.itemHandler.getStackInSlot(INPUT_SLOT_RIGHT));
        if (result != null && canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem())){
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

    //    private Optional<CentrifugeRecipe> getCurrentRecipe() {
//        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
//        for(int i = 0; i < itemHandler.getSlots(); i++){
//            inventory.setItem(i, itemHandler.getStackInSlot(i));
//        }
//        return this.level.getRecipeManager().getRecipeFor(CentrifugeRecipe.Type.INSTANCE, CentrifugeRecipe.Type.INSTANCE, level );
//
//    }
    private void craftItem(ItemStack result) {
//        ItemStack result = new ItemStack(ModItems.ADRENALINE_INJECTOR.get(), 1);
        this.itemHandler.extractItem(INPUT_SLOT_LEFT, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_CENTER, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_RIGHT, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT,
                new ItemStack(result.getItem(), this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }
//    private boolean hasRecipe() {
////        Optional<CentrifugeRecipe> recipe = getCurrentRecipe();
//        boolean hasCraftingItem = this.itemHandler.getStackInSlot(INPUT_SLOT_LEFT).getItem() == Items.SUGAR;
//        ItemStack result = new ItemStack(ModItems.ADRENALINE_INJECTOR.get());
//
//
//        return hasCraftingItem && canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
//    }

    private ItemStack getRecipe(ItemStack slot1, ItemStack slot2, ItemStack slot3){
        // TODO СДЕЛАТЬ НОРМАЛЬНО aka разобраться с сериалайзерами рот их ебал
        if(getVariants(slot1, slot2, slot3, Items.SUGAR, Items.COCOA_BEANS, Items.AIR)){
            return new ItemStack(ModItems.ADRENALINE_INJECTOR.get(), 1);

        } else if (getVariants(slot1, slot2, slot3, Items.REDSTONE, Items.AIR, Items.AIR)) {
            return new ItemStack(ModItems.AHF1M_INJECTOR.get(), 1);

        } else if (getVariants(slot1, slot2, slot3, Items.CARROT, Items.AIR, Items.AIR)) {
            return new ItemStack(ModItems.BTG2A2_INJECTOR.get(), 1);

        } else if (getVariants(slot1, slot2, slot3, Items.CARROT, Items.SUGAR, Items.AIR)) {
            return new ItemStack(ModItems.BTG3_INJECTOR.get(), 1);

        } else if (getVariants(slot1, slot2, slot3, Items.GOLDEN_APPLE, Items.GLOW_BERRIES, Items.SUGAR)) {
            return new ItemStack(ModItems.ETGC_INJECTOR.get(), 1);

        } else if (getVariants(slot1, slot2, slot3, Items.SUGAR, Items.SUGAR, Items.AIR)) {
            return new ItemStack(ModItems.L1_INJECTOR.get(), 1);

        } else if (getVariants(slot1, slot2, slot3, Items.SUGAR, Items.COCOA_BEANS, Items.BLAZE_POWDER)) {
            return new ItemStack(ModItems.MELDONIN_INJECTOR.get(), 1);

        } else if (getVariants(slot1, slot2, slot3, Items.POPPY, Items.AIR, Items.AIR)) {
            return new ItemStack(ModItems.MORPHINE_INJECTOR.get(), 1);

        } else if (getVariants(slot1, slot2, slot3, Items.RED_MUSHROOM, Items.BROWN_MUSHROOM, Items.BLAZE_POWDER)) {
            return new ItemStack(ModItems.MULE_INJECTOR.get(), 1);

        } else if (getVariants(slot1, slot2, slot3, Items.CHORUS_FRUIT, Items.GLOW_BERRIES, Items.RED_MUSHROOM)) {
            return new ItemStack(ModItems.OBDOLBOS_INJECTOR.get(), 1);

        } else if (getVariants(slot1, slot2, slot3, Items.CHORUS_FRUIT, Items.BLAZE_ROD, Items.COCOA_BEANS)) {
            return new ItemStack(ModItems.OBDOLBOS2_INJECTOR.get(), 1);

        } else if (getVariants(slot1, slot2, slot3, Items.POPPY, Items.BEETROOT, Items.SUGAR)) {
            return new ItemStack(ModItems.P22_INJECTOR.get(), 1);

        } else if (getVariants(slot1, slot2, slot3, Items.BROWN_MUSHROOM, Items.SWEET_BERRIES, Items.AIR)) {
            return new ItemStack(ModItems.PERFOTORAN_INJECTOR.get(), 1);

        } else if (getVariants(slot1, slot2, slot3, Items.SUGAR_CANE, Items.SWEET_BERRIES, Items.AIR)) {
            return new ItemStack(ModItems.PNB_INJECTOR.get(), 1);

        } else if (getVariants(slot1, slot2, slot3, Items.BROWN_MUSHROOM, Items.COCOA_BEANS, Items.SUGAR)) {
            return new ItemStack(ModItems.PROPITAL_INJECTOR.get(), 1);

        } else if (getVariants(slot1, slot2, slot3, Items.COCOA_BEANS, Items.SUGAR, Items.SUGAR)) {
            return new ItemStack(ModItems.SJ1_INJECTOR.get(), 1);

        } else if (getVariants(slot1, slot2, slot3, Items.COOKED_BEEF, Items.AIR, Items.AIR)) {
            return new ItemStack(ModItems.SJ6_INJECTOR.get(), 1);

        } else if (getVariants(slot1, slot2, slot3, Items.INK_SAC, Items.AIR, Items.AIR)) {
            return new ItemStack(ModItems.SJ9_INJECTOR.get(), 1);

        } else if (getVariants(slot1, slot2, slot3, Items.INK_SAC, Items.COD, Items.CARROT)) {
            return new ItemStack(ModItems.SJ12_INJECTOR.get(), 1);

        } else if (getVariants(slot1, slot2, slot3, Items.CHORUS_FRUIT, Items.ENDER_EYE, Items.CRIMSON_FUNGUS)) {
            return new ItemStack(ModItems.SJ15_INJECTOR.get(), 1);

        } else if (getVariants(slot1, slot2, slot3, Items.GOLDEN_APPLE, Items.SPIDER_EYE, Items.BRICK)) {
            return new ItemStack(ModItems.TRIMADOL_INJECTOR.get(), 1);

        } else if (getVariants(slot1, slot2, slot3, Items.FERMENTED_SPIDER_EYE, Items.AIR, Items.AIR)) {
            return new ItemStack(ModItems.ZAGUSTIN_INJECTOR.get(), 1);

        }

        return null;
    }
    private boolean getVariants(ItemStack slot1, ItemStack slot2, ItemStack slot3, Item ing1, Item ing2, Item ing3){
        return     (slot1.getItem() == ing1 && slot2.getItem() == ing2 && slot3.getItem() == ing3)
                || (slot1.getItem() == ing1 && slot2.getItem() == ing3 && slot3.getItem() == ing2)
                || (slot1.getItem() == ing2 && slot2.getItem() == ing1 && slot3.getItem() == ing3)
                || (slot1.getItem() == ing2 && slot2.getItem() == ing3 && slot3.getItem() == ing1)
                || (slot1.getItem() == ing3 && slot2.getItem() == ing1 && slot3.getItem() == ing2)
                || (slot1.getItem() == ing3 && slot2.getItem() == ing2 && slot3.getItem() == ing1);
    }

}
