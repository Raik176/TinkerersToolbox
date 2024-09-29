package org.rhm.tinkerers_toolbox;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import org.rhm.tinkerers_toolbox.gui.ModScreens;
import org.rhm.tinkerers_toolbox.gui.TinkerersBenchScreen;

public class ModMainClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreens.TINKERERS_BENCH, TinkerersBenchScreen::new);
    }
}