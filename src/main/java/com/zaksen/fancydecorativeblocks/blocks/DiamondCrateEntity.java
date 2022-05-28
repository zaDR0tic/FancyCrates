package com.zaksen.fancydecorativeblocks.blocks;

import com.zaksen.fancydecorativeblocks.FancyBlockEntities;
import com.zaksen.fancydecorativeblocks.screen.DiamondCrateMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class DiamondCrateEntity extends BaseStorageEntityBlock{
    public DiamondCrateEntity(BlockPos BPos, BlockState BState) {
        super(FancyBlockEntities.DIAMOND_CRATE.get(), BPos, BState, 55, "Diamond Crate");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int ContainerId, Inventory Inv, Player P) {
        return new DiamondCrateMenu(ContainerId, Inv, this);
    }
}
