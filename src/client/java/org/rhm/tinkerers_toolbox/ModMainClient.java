package org.rhm.tinkerers_toolbox;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import org.rhm.tinkerers_toolbox.block.ModBlocks;
import org.rhm.tinkerers_toolbox.gui.ModScreens;
import org.rhm.tinkerers_toolbox.gui.TinkerersBenchScreen;

public class ModMainClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreens.TINKERERS_BENCH, TinkerersBenchScreen::new);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TINKERERS_BENCH, RenderLayer.getTranslucent());
    }
}