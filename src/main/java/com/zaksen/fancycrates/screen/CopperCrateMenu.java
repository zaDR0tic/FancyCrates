package com.zaksen.fancycrates.screen;

import com.zaksen.fancycrates.FancyBlocks;
import com.zaksen.fancycrates.blocks.CopperCrateEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CopperCrateMenu extends AbstractContainerMenu {
    private final CopperCrateEntity blockEntity;
    private final Level level;

    protected CopperCrateMenu(int ContrainerId, Inventory Inv, FriendlyByteBuf extraData) {
        this(ContrainerId, Inv, Inv.player.level.getBlockEntity(extraData.readBlockPos()));
    }

    public CopperCrateMenu(int ContrainerId, Inventory Inv, BlockEntity entity) {
        super(FancyMenuTypes.COPPER_CRATE_MENU.get(), ContrainerId);
        checkContainerSize(Inv,41);
        blockEntity = ((CopperCrateEntity) entity);
        this.level = Inv.player.level;

        addPlayerInventory(Inv);
        addPlayerHotbar(Inv);

        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 8, 18));
            this.addSlot(new SlotItemHandler(iItemHandler, 1, 26, 18));
            this.addSlot(new SlotItemHandler(iItemHandler, 2, 44, 18));
            this.addSlot(new SlotItemHandler(iItemHandler, 3, 62, 18));
            this.addSlot(new SlotItemHandler(iItemHandler, 4, 80, 18));
            this.addSlot(new SlotItemHandler(iItemHandler, 5, 98, 18));
            this.addSlot(new SlotItemHandler(iItemHandler, 6, 116, 18));
            this.addSlot(new SlotItemHandler(iItemHandler, 7, 134, 18));
            this.addSlot(new SlotItemHandler(iItemHandler, 8, 152, 18));
            this.addSlot(new SlotItemHandler(iItemHandler, 9, 8, 36));
            this.addSlot(new SlotItemHandler(iItemHandler, 10, 26, 36));
            this.addSlot(new SlotItemHandler(iItemHandler, 11, 44, 36));
            this.addSlot(new SlotItemHandler(iItemHandler, 12, 62, 36));
            this.addSlot(new SlotItemHandler(iItemHandler, 13, 80, 36));
            this.addSlot(new SlotItemHandler(iItemHandler, 14, 98, 36));
            this.addSlot(new SlotItemHandler(iItemHandler, 15, 116, 36));
            this.addSlot(new SlotItemHandler(iItemHandler, 16, 134, 36));
            this.addSlot(new SlotItemHandler(iItemHandler, 17, 152, 36));
            this.addSlot(new SlotItemHandler(iItemHandler, 18, 8, 54));
            this.addSlot(new SlotItemHandler(iItemHandler, 19, 26, 54));
            this.addSlot(new SlotItemHandler(iItemHandler, 20, 44, 54));
            this.addSlot(new SlotItemHandler(iItemHandler, 21, 62, 54));
            this.addSlot(new SlotItemHandler(iItemHandler, 22, 80, 54));
            this.addSlot(new SlotItemHandler(iItemHandler, 23, 98, 54));
            this.addSlot(new SlotItemHandler(iItemHandler, 24, 116, 54));
            this.addSlot(new SlotItemHandler(iItemHandler, 25, 134, 54));
            this.addSlot(new SlotItemHandler(iItemHandler, 26, 152, 54));
        });
    }

    @Override
    public boolean stillValid(Player P) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), P, FancyBlocks.COPPER_CRATE.get());
    }

    private void addPlayerInventory(Inventory playerInv)
    {
        for(int i = 0; i < 3; ++i)
        {
            for(int l = 0; l < 9; ++l)
            {
                this.addSlot(new Slot(playerInv, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory)
    {
        for(int i = 0; i < 9; ++i)
        {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = 27;

    @Override
    public ItemStack quickMoveStack(Player p, int index)
    {
        Slot sourceSlot = slots.get(index);
        if(sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT)
        {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT, false))
            {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        }
        else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT)
        {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false))
            {
                return ItemStack.EMPTY;
            }
        }
        else
        {
            System.out.println("Invalid Slot Index: " + index);
            return ItemStack.EMPTY;
        }

        if(sourceStack.getCount() == 0)
        {
            sourceSlot.set(ItemStack.EMPTY);
        }
        else
        {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(p, sourceStack);
        return copyOfSourceStack;
    }
}
