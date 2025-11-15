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
 * Primary Forge entry point for the Hellas Mineralogy sidemod.
 * <p>
 * The class does not register any custom content yet; instead, it ensures that the
 * HellasCore/Control infrastructure is present before any future mineralogy-specific
 * registration would happen. In practice this sidemod currently behaves as a runtime
 * validator that can be expanded with new mineralogy related features without
 * changing the initialization contract.
 * </p>
 */
@Mod(HellasMineralogy.MOD_ID)
public class HellasMineralogy {
    public static final String MOD_ID = "hellasmineralogy";
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Registers the setup listeners on construction so that Forge invokes the lifecycle
     * hooks at the appropriate stages during game startup.
     */
    public HellasMineralogy() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
    }

    /**
     * Performs the minimal server-side wiring for the mod.
     * <p>
     * The method ensures that the shared Hellas control framework is present so that future
     * mineralogy features can depend on its APIs. On dedicated servers an entitlement check is
     * performed to make sure the instance is licensed for this sidemod.
     * </p>
     *
     * @param event the Forge common setup lifecycle event
     */
    private void onCommonSetup(final FMLCommonSetupEvent event) {
        CoreCheck.verifyCoreLoaded();
        if (FMLEnvironment.dist == Dist.DEDICATED_SERVER) {
            // Dedicated servers are gated by an entitlement code provided by Hellas Control.
            CoreCheck.verifyEntitled("mineralogy");
        }

        if (!ModList.get().isLoaded("hellascontrol")) {
            LOGGER.warn("HellasControl not detected; skipping HellasMineralogy initialization.");
            return;
        }

        LOGGER.info("HellasMineralogy common setup initialized.");
    }

    /**
     * Client-side initialization hook, currently only used to log that the mod is active on
     * the client. Any future mineralogy specific renderers or screens would be registered here.
     *
     * @param event the Forge client setup lifecycle event
     */
    private void onClientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("HellasMineralogy client setup initialized.");
    }
}
