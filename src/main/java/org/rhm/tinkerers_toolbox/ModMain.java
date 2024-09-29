package org.rhm.tinkerers_toolbox;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rhm.tinkerers_toolbox.block.ModBlocks;
import org.rhm.tinkerers_toolbox.block.entity.ModBlockEntities;
import org.rhm.tinkerers_toolbox.gui.ModScreens;

public class ModMain implements ModInitializer {
    public static final String MOD_ID = "tinkerers_toolbox";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModBlocks.initialize();
        ModBlockEntities.initialize();
        ModScreens.initialize();
    }
}
