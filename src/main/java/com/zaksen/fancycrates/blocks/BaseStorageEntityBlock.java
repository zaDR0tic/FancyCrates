package com.zaksen.fancycrates.blocks;

import com.zaksen.fancycrates.FancyCrates;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class BaseStorageEntityBlock extends BlockEntity implements MenuProvider {

    private String Name;
    private int Size;

    private final ItemStackHandler ItemHandler;

    private LazyOptional<IItemHandler> LazyItemHandler = LazyOptional.empty();

    protected BaseStorageEntityBlock(BlockEntityType<?> BlockEntityType, BlockPos BPos, BlockState BState, int Size, String Name) {
        super(BlockEntityType, BPos, BState);
        this.Name = Name;
        this.Size = Size;
        this.ItemHandler = new ItemStackHandler(Size)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                setChanged();
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("gui." + FancyCrates.MOD_ID + "." + Name.toLowerCase(Locale.ROOT).replaceAll(" ", ""));
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int ContainerId, Inventory Inv, Player P) {
        return null;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return LazyItemHandler.cast();
        }
        return super.getCapability(cap);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        LazyItemHandler = LazyOptional.of(() -> ItemHandler);
    }

    @Override
    protected void saveAdditional(CompoundTag Tag) {
        Tag.put("inventory", ItemHandler.serializeNBT());
        super.saveAdditional(Tag);
    }

    @Override
    public void load(CompoundTag Tag) {
        super.load(Tag);
        ItemHandler.deserializeNBT(Tag.getCompound("inventory"));
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        LazyItemHandler.invalidate();
    }

    public void drops()
    {
        SimpleContainer Inventory = new SimpleContainer(ItemHandler.getSlots());
        for(int i = 0; i < ItemHandler.getSlots(); i ++)
        {
            Inventory.setItem(i, ItemHandler.getStackInSlot(i));
        }


        Containers.dropContents(this.level, this.worldPosition, Inventory);
    }

    public static void tick(Level Level, BlockPos BPos, BlockState BState, BaseStorageEntityBlock BlockEntity)
    {
        TickFunction(BlockEntity);
    }

    private static void TickFunction(BaseStorageEntityBlock BlockEntity)
    {

    }
}