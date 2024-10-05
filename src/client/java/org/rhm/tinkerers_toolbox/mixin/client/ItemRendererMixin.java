package org.rhm.tinkerers_toolbox.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.rhm.tinkerers_toolbox.ModificationType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Unique
    private static final float Z_OFFSET = 0.032f;

    // TODO: this method doesn't work with the enchantment glint. maybe find a way to add the overlay onto the texture before its rendered?
    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At("RETURN"))
    private void renderOverlayOnItem(ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci) {
        if (stack.isEmpty()) return;
        if (model.hasDepth()) return; // for now, 3d models simply don't work.
        for (ModificationType value : ModificationType.values()) { // yummy lags
            if (!stack.getComponents().contains(value.getComponent())) continue;

            Identifier id = Identifier.tryParse(Objects.requireNonNull(stack.get(value.getComponent())));
            if (id == null || !Registries.ITEM.containsId(id)) {
                stack.remove(value.getComponent()); // remove invalid shaft
                continue;
            }

            renderOverlay(matrices, model, renderMode, leftHanded, value, vertexConsumers, light, overlay);
        }
    }

    // most normal method with the least amount of parameters
    @Unique
    private void renderOverlay(MatrixStack matrices, BakedModel model, ModelTransformationMode renderMode, boolean leftHanded, ModificationType type, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        // front
        matrices.push();

        // FIXME: doesn't work for 3d models but eh, most tools are 2d anyway
        model.getTransformation().getTransformation(renderMode).apply(leftHanded, matrices);
        if (renderMode == ModelTransformationMode.GUI) {
            matrices.translate(type.getOffsetGui().x, type.getOffsetGui().y, 1);
        } else {
            matrices.translate(type.getOffset().x, type.getOffset().y, Z_OFFSET);
        }

        MinecraftClient.getInstance().getTextureManager().bindTexture(type.getTexture());
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getText(type.getTexture()));
        MatrixStack.Entry matrixEntry = matrices.peek();
        renderOverlayQuad(matrixEntry, vertexConsumer, light, overlay, false);
        matrices.pop();

        // back
        matrices.push();

        // FIXME: doesn't work for 3d models but eh, most tools are 2d anyway
        model.getTransformation().getTransformation(renderMode).apply(leftHanded, matrices);
        if (renderMode == ModelTransformationMode.GUI) {
            matrices.translate(0, 0, 1);
        } else {
            matrices.translate(0, 0, -Z_OFFSET);
        }

        MinecraftClient.getInstance().getTextureManager().bindTexture(type.getTexture());
        vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getText(type.getTexture()));
        matrixEntry = matrices.peek();
        renderOverlayQuad(matrixEntry, vertexConsumer, light, overlay, true);
        matrices.pop();
    }

    @Unique
    private void renderOverlayQuad(MatrixStack.Entry matrixEntry, VertexConsumer vertexConsumer, int light, int overlay, boolean flipped) {
        float[] y = flipped ? new float[]{0.5f, 0.5f, -0.5f, -0.5f} : new float[]{-0.5f, -0.5f, 0.5f, 0.5f};
        int[] v = flipped ? new int[]{0, 0, 1, 1} : new int[]{1, 1, 0, 0};

        vertexConsumer.vertex(matrixEntry.getPositionMatrix(), -0.5f, y[0], 0)
            .color(255, 255, 255, 255)
            .texture(0, v[0]) // bottom left
            .light(light)
            .overlay(overlay);

        vertexConsumer.vertex(matrixEntry.getPositionMatrix(), 0.5f, y[1], 0)
            .color(255, 255, 255, 255)
            .texture(1, v[1]) // bottom right
            .light(light)
            .overlay(overlay);

        vertexConsumer.vertex(matrixEntry.getPositionMatrix(), 0.5f, y[2], 0)
            .color(255, 255, 255, 255)
            .texture(1, v[2]) // top right
            .light(light)
            .overlay(overlay);

        vertexConsumer.vertex(matrixEntry.getPositionMatrix(), -0.5f, y[3], 0)
            .color(255, 255, 255, 255)
            .texture(0, v[3]) // top left
            .light(light)
            .overlay(overlay);
    }
}
