package com.zaksen.fancydecorativeblocks.blocks;

import com.zaksen.fancydecorativeblocks.FancyBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class NetheriteCrate extends BaseEntityBlock {

    public NetheriteCrate() {
        super(Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL).destroyTime(10f).explosionResistance(20f));
    }

    @Override
    public InteractionResult use(BlockState BState, Level Level, BlockPos BPos, Player P, InteractionHand Hand, BlockHitResult Result) {
        if(!Level.isClientSide)
        {
            BlockEntity entity = Level.getBlockEntity(BPos);
            if(entity instanceof NetheriteCrateEntity)
            {
                NetworkHooks.openGui(((ServerPlayer)P), (NetheriteCrateEntity)entity, BPos);
            }
            else
            {
                throw new IllegalStateException("Container provider is missing!");
            }
        }
        return InteractionResult.sidedSuccess(Level.isClientSide());
    }

    @Override
    public void tick(BlockState BState, ServerLevel Level, BlockPos BPos, Random Rand) {
        super.tick(BState, Level, BPos, Rand);
    }

    @Override
    public void onRemove(BlockState BState, Level Level, BlockPos BPos, BlockState BState2, boolean bool) {
        if(BState.getBlock() != BState2.getBlock())
        {
            BlockEntity blockEntity = Level.getBlockEntity(BPos);
            if(blockEntity instanceof NetheriteCrateEntity)
            {
                ((NetheriteCrateEntity) blockEntity).drops();
            }
        }
        super.onRemove(BState, Level, BPos, BState2, bool);
    }

    @Override
    public RenderShape getRenderShape(BlockState BState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos BPos, BlockState BState) {
        return new NetheriteCrateEntity(BPos, BState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level Level, BlockState BState, BlockEntityType<T> BlockEntity) {
        return createTickerHelper(BlockEntity, FancyBlockEntities.NETHERITE_CRATE.get(), NetheriteCrateEntity::tick);
    }
}
