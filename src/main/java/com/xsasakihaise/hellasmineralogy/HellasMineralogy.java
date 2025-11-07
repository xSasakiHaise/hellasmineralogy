package com.xsasakihaise.hellasmineralogy;

import com.xsasakihaise.hellascontrol.api.CoreCheck;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Primary mod entrypoint for HellasMineralogy.
 */
@Mod(HellasMineralogy.MOD_ID)
public class HellasMineralogy {
    public static final String MOD_ID = "hellasmineralogy";
    private static final Logger LOGGER = LogManager.getLogger();

    public HellasMineralogy() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        CoreCheck.verifyCoreLoaded();
        if (FMLEnvironment.dist == Dist.DEDICATED_SERVER) {
            CoreCheck.verifyEntitled("mineralogy");
        }

        if (!ModList.get().isLoaded("hellascontrol")) {
            LOGGER.warn("HellasControl not detected; skipping HellasMineralogy initialization.");
            return;
        }

        LOGGER.info("HellasMineralogy common setup initialized.");
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("HellasMineralogy client setup initialized.");
    }
}
