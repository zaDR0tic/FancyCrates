package com.zaksen.fancycrates;

import com.zaksen.fancycrates.blocks.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FancyBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, FancyCrates.MOD_ID);

    public static final RegistryObject<BlockEntityType<WoodCrateEntity>> WOOD_CRATE = BLOCK_ENTITIES.register("wood_crate", () -> BlockEntityType.Builder.of(WoodCrateEntity::new, FancyBlocks.WOOD_CRATE.get()).build(null));
    public static final RegistryObject<BlockEntityType<IronCrateEntity>> IRON_CRATE = BLOCK_ENTITIES.register("iron_crate", () -> BlockEntityType.Builder.of(IronCrateEntity::new, FancyBlocks.IRON_CRATE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CopperCrateEntity>> COPPER_CRATE = BLOCK_ENTITIES.register("copper_crate", () -> BlockEntityType.Builder.of(CopperCrateEntity::new, FancyBlocks.COPPER_CRATE.get()).build(null));
    public static final RegistryObject<BlockEntityType<GoldCrateEntity>> GOLD_CRATE = BLOCK_ENTITIES.register("gold_crate", () -> BlockEntityType.Builder.of(GoldCrateEntity::new, FancyBlocks.GOLD_CRATE.get()).build(null));
    public static final RegistryObject<BlockEntityType<DiamondCrateEntity>> DIAMOND_CRATE = BLOCK_ENTITIES.register("diamond_crate", () -> BlockEntityType.Builder.of(DiamondCrateEntity::new, FancyBlocks.DIAMOND_CRATE.get()).build(null));
    public static final RegistryObject<BlockEntityType<NetheriteCrateEntity>> NETHERITE_CRATE = BLOCK_ENTITIES.register("netherite_crate", () -> BlockEntityType.Builder.of(NetheriteCrateEntity::new, FancyBlocks.NETHERITE_CRATE.get()).build(null));
    public static void register() {
        BLOCK_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
