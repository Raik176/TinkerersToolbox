package org.rhm.tinkerers_toolbox.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rhm.tinkerers_toolbox.ModMain;
import org.rhm.tinkerers_toolbox.block.ModBlocks;
import org.rhm.tinkerers_toolbox.gui.TinkerersBenchScreen;
import org.rhm.tinkerers_toolbox.recipe.TinkerRecipe;

public class TinkerCategory implements IRecipeCategory<RecipeEntry<TinkerRecipe>> {
    private final IGuiHelper helper;

    public TinkerCategory(IGuiHelper helper) {
        this.helper = helper;
    }

    @Override
    public @NotNull RecipeType<RecipeEntry<TinkerRecipe>> getRecipeType() {
        return ModJEIPlugin.RECIPE_TYPE;
    }

    @Override
    public @NotNull Text getTitle() {
        return Text.translatable(ModMain.MOD_ID + ".recipe_type");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return helper.createDrawableItemStack(ModBlocks.TINKERERS_BENCH.asItem().getDefaultStack());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeEntry<TinkerRecipe> recipe, IFocusGroup focuses) {

    }

    @Override
    public void draw(RecipeEntry<TinkerRecipe> recipe, IRecipeSlotsView recipeSlotsView, DrawContext guiGraphics, double mouseX, double mouseY) {
        guiGraphics.drawTexture(TinkerersBenchScreen.TEXTURE, 0, 0, 0, 0, getWidth(), getHeight());
    }
}
