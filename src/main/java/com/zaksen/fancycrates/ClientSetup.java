package com.zaksen.fancycrates;

import com.zaksen.fancycrates.screen.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
@Mod.EventBusSubscriber(modid = FancyCrates.MOD_ID)
public class ClientSetup {
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent Event)
    {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                MenuScreens.register(FancyMenuTypes.WOOD_CRATE_MENU.get(), WoodCrateScreen::new);
                MenuScreens.register(FancyMenuTypes.IRON_CRATE_MENU.get(), IronCrateScreen::new);
                MenuScreens.register(FancyMenuTypes.COPPER_CRATE_MENU.get(), CopperCrateScreen::new);
                MenuScreens.register(FancyMenuTypes.GOLD_CRATE_MENU.get(), GoldCrateScreen::new);
                MenuScreens.register(FancyMenuTypes.DIAMOND_CRATE_MENU.get(), DiamondCrateScreen::new);
                MenuScreens.register(FancyMenuTypes.NETHERITE_CRATE_MENU.get(), NetheriteCrateScreen::new);
            }
        };
        Event.enqueueWork(runnable);
    }
}
