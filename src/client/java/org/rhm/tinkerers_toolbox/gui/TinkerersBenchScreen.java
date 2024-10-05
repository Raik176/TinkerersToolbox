package org.rhm.tinkerers_toolbox.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.rhm.tinkerers_toolbox.ModMain;

public class TinkerersBenchScreen extends HandledScreen<TinkerersBenchScreenHandler> {
    public static final String TOOL_PLACEHOLDER_KEY = ModMain.MOD_ID + ".gui.tool_placeholder";
    public static final String MINERAL_PLACEHOLDER_KEY = ModMain.MOD_ID + ".gui.mineral_placeholder";
    public static final String PATTERN_PLACEHOLDER_KEY = ModMain.MOD_ID + ".gui.pattern_placeholder";

    private static final Identifier TEXTURE = Identifier.of(ModMain.MOD_ID, "textures/gui/tinkerers_bench_gui.png");
    private static final Identifier RECYCLE_TEXTURE = Identifier.of(ModMain.MOD_ID, "container/tinkerers_bench/empty_slot_recycle");
    private static final Identifier PATTERN_TEXTURE = Identifier.of(ModMain.MOD_ID, "container/tinkerers_bench/empty_slot_pattern");
    private static final Identifier[] TOOL_CAROUSEL = new Identifier[]{
            Identifier.of(ModMain.MOD_ID, "container/tinkerers_bench/empty_slot_sword"),
            Identifier.of(ModMain.MOD_ID, "container/tinkerers_bench/empty_slot_pickaxe"),
            Identifier.of(ModMain.MOD_ID, "container/tinkerers_bench/empty_slot_axe"),
            Identifier.of(ModMain.MOD_ID, "container/tinkerers_bench/empty_slot_shovel"),
            Identifier.of(ModMain.MOD_ID, "container/tinkerers_bench/empty_slot_hoe"),
    };
    private static final Identifier[] MINERAL_CAROUSEL = new Identifier[]{
            Identifier.of(ModMain.MOD_ID, "container/tinkerers_bench/empty_slot_diamond"),
            Identifier.of(ModMain.MOD_ID, "container/tinkerers_bench/empty_slot_emerald"),
            Identifier.of(ModMain.MOD_ID, "container/tinkerers_bench/empty_slot_ingot"),
            Identifier.of(ModMain.MOD_ID, "container/tinkerers_bench/empty_slot_quartz"),
            Identifier.of(ModMain.MOD_ID, "container/tinkerers_bench/empty_slot_redstone_dust"),
    };
    private int tool_carousel_index = 0;
    private float tool_carousel_timer = 0;
    private int mineral_carousel_index = 0;
    private float mineral_carousel_timer = 0;

    public TinkerersBenchScreen(TinkerersBenchScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(TEXTURE, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        Slot toolSlot = handler.getToolSlot();
        Slot mineralSlot = handler.getMineralSlot();
        Slot recycleSlot = handler.getRecycleSlot();
        Slot patternSlot = handler.getPatternSlot();
        if (!toolSlot.hasStack()) {
            context.drawGuiTexture(TOOL_CAROUSEL[tool_carousel_index], x + toolSlot.x, y + toolSlot.y, 16, 16);

            tool_carousel_timer += delta;
            if (tool_carousel_timer >= 20.25) {
                tool_carousel_timer = 0;
                tool_carousel_index++;
                if (tool_carousel_index > TOOL_CAROUSEL.length - 1) tool_carousel_index = 0;
            }
        }

        if (!mineralSlot.hasStack()) {
            context.drawGuiTexture(MINERAL_CAROUSEL[mineral_carousel_index], x + mineralSlot.x, y + mineralSlot.y, 16, 16);

            mineral_carousel_timer += delta;
            if (mineral_carousel_timer >= 20.25) {
                mineral_carousel_timer = 0;
                mineral_carousel_index++;
                if (mineral_carousel_index > MINERAL_CAROUSEL.length - 1) mineral_carousel_index = 0;
            }
        }

        if (!recycleSlot.hasStack()) {
            context.drawGuiTexture(RECYCLE_TEXTURE, x + recycleSlot.x, y + recycleSlot.y, 16, 16);
        }

        if (!patternSlot.hasStack()) {
            context.drawGuiTexture(PATTERN_TEXTURE, x + patternSlot.x, y + patternSlot.y, 16, 16);
        }
    }

    @Override
    protected void drawMouseoverTooltip(DrawContext context, int x, int y) {
        super.drawMouseoverTooltip(context, x, y);
        if (this.focusedSlot != null && !this.focusedSlot.hasStack()) {
            if (this.focusedSlot == handler.getToolSlot()) {
                context.drawTooltip(this.textRenderer, Text.translatable(TOOL_PLACEHOLDER_KEY), x, y);
            } else if (this.focusedSlot == handler.getMineralSlot()) {
                context.drawTooltip(this.textRenderer, Text.translatable(MINERAL_PLACEHOLDER_KEY), x, y);
            } else if (this.focusedSlot == handler.getPatternSlot()) {
                context.drawTooltip(this.textRenderer, Text.translatable(PATTERN_PLACEHOLDER_KEY), x, y);
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
