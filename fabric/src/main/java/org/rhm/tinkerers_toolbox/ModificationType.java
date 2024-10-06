package org.rhm.tinkerers_toolbox;

import net.minecraft.component.ComponentType;
import net.minecraft.util.Identifier;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.rhm.tinkerers_toolbox.components.ModComponents;

public enum ModificationType {
    SHAFT(
        ModComponents.SHAFT_COMPONENT,
        Identifier.of(ModMain.MOD_ID, "textures/misc/shaft_overlay.png")
    ),
    BINDING(
        ModComponents.BINDING_COMPONENT,
        Identifier.of(ModMain.MOD_ID, "textures/misc/binding_overlay.png")
    ),
    HEAD(
        ModComponents.HEAD_COMPONENT,
        Identifier.of(ModMain.MOD_ID, "textures/misc/head_overlay.png")
    );

    private final ComponentType<String> component;
    private final Identifier texture;
    private final Vector2f offsetGui;
    private final Vector3f offset;

    ModificationType(ComponentType<String> component, Identifier texture) {
        this(component, texture, new Vector2f(0, 0), new Vector3f(0, 0, 0));
    }

    ModificationType(ComponentType<String> component, Identifier texture, Vector2f offsetGui, Vector3f offset) {
        this.component = component;
        this.texture = texture;
        this.offsetGui = offsetGui;
        this.offset = offset;
    }

    public Vector2f getOffsetGui() {
        return offsetGui;
    }

    public Vector3f getOffset() {
        return offset;
    }

    public ComponentType<String> getComponent() {
        return component;
    }

    public Identifier getTexture() {
        return texture;
    }
}
