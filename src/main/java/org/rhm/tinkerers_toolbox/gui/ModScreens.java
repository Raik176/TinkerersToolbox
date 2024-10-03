package org.rhm.tinkerers_toolbox.gui;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.rhm.tinkerers_toolbox.ModMain;

public class ModScreens {
    public static <T extends ScreenHandlerType<?>> T register(T type, String name) {
        return Registry.register(Registries.SCREEN_HANDLER, Identifier.of(ModMain.MOD_ID, name), type);
    }

    public static void initialize() {
    }

    public static final ScreenHandlerType<TinkerersBenchScreenHandler> TINKERERS_BENCH = register(
            new ScreenHandlerType<>(TinkerersBenchScreenHandler::new, FeatureFlags.VANILLA_FEATURES),
            "tinkerers_bench"
    );


}
