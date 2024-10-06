package org.rhm.tinkerers_toolbox.integrations.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.rhm.tinkerers_toolbox.ModMain;
import org.rhm.tinkerers_toolbox.gui.TinkerersBenchScreen;
import org.rhm.tinkerers_toolbox.recipe.ModRecipeTypes;
import org.rhm.tinkerers_toolbox.recipe.TinkerRecipe;

@JeiPlugin
public class ModJEIPlugin implements IModPlugin {
    public static final RecipeType<RecipeEntry<TinkerRecipe>> RECIPE_TYPE = RecipeType.createFromVanilla(ModRecipeTypes.TINKER_RECIPE_TYPE);

    @Override
    public @NotNull Identifier getPluginUid() {
        return Identifier.of(ModMain.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(new TinkerCategory(helper));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) { // why did I make the arrow so complex...
        registration.addRecipeClickArea(TinkerersBenchScreen.class, 89, 42, 20, 3, RECIPE_TYPE);
        registration.addRecipeClickArea(TinkerersBenchScreen.class, 95, 45, 3, 16, RECIPE_TYPE);
    }
}
