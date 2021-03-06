package com.zaksen.fancycrates.blocks;

import com.zaksen.fancycrates.FancyBlockEntities;
import com.zaksen.fancycrates.screen.WoodCrateMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class WoodCrateEntity extends BaseStorageEntityBlock{
    public WoodCrateEntity(BlockPos BPos, BlockState BState) {
        super(FancyBlockEntities.WOOD_CRATE.get(), BPos, BState, 9, "Wood Crate");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int ContainerId, Inventory Inv, Player P) {
        return new WoodCrateMenu(ContainerId, Inv, this);
    }
}
