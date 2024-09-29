package org.rhm.tinkerers_toolbox.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.rhm.tinkerers_toolbox.ModMain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Unique
    private static final Identifier OVERLAY_TEXTURE = Identifier.of(ModMain.MOD_ID, "textures/item/overlay.png");

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;IILnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;I)V", at = @At("RETURN"))
    private void renderOverlayOnItem(ItemStack stack, ModelTransformationMode transformationType, int light, int overlay, MatrixStack matrices, VertexConsumerProvider vertexConsumers, World world, int seed, CallbackInfo ci) {
        if (!stack.isEmpty()) {
            System.out.println("lel");
            MinecraftClient client = MinecraftClient.getInstance();

            // Bind the overlay texture
            client.getTextureManager().bindTexture(OVERLAY_TEXTURE);

            // Set up the matrix stack for correct positioning
            matrices.push();
            matrices.translate(0, 0, 200);  // Ensure overlay is rendered on top

            // Get the buffer for rendering (default layer)
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(OVERLAY_TEXTURE));

            // Render a quad (overlay) on top of the item (this is 2D, so you can adjust the size accordingly)
            MatrixStack.Entry entry = matrices.peek();
            float size = 1.0F; // Size of the overlay (adjust as needed)
            vertexConsumer.vertex(entry.getPositionMatrix(), -size / 2, -size / 2, 0).color(255, 255, 255, 255);
            vertexConsumer.vertex(entry.getPositionMatrix(), size / 2, -size / 2, 0).color(255, 255, 255, 255);
            vertexConsumer.vertex(entry.getPositionMatrix(), size / 2, size / 2, 0).color(255, 255, 255, 255);
            vertexConsumer.vertex(entry.getPositionMatrix(), -size / 2, size / 2, 0).color(255, 255, 255, 255);

            matrices.pop();
        }
    }
}
