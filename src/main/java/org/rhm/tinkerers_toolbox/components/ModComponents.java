package org.rhm.tinkerers_toolbox.components;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.rhm.tinkerers_toolbox.ModMain;

public class ModComponents {
    public static final ComponentType<String> SHAFT_COMPONENT = register(
            "shaft",
            ComponentType.<String>builder().codec(Codec.STRING).build()
    );
    public static final ComponentType<String> BINDING_COMPONENT = register(
            "binding",
            ComponentType.<String>builder().codec(Codec.STRING).build()
    );
    public static final ComponentType<String> HEAD_COMPONENT = register(
            "head",
            ComponentType.<String>builder().codec(Codec.STRING).build()
    );

    public static <T extends ComponentType<?>> T register(String name, T component) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(ModMain.MOD_ID, name), component);
    }

    public static void initialize() {
    }
}
