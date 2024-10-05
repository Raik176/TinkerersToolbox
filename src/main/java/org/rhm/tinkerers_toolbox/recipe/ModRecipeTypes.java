package org.rhm.tinkerers_toolbox.recipe;

import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.rhm.tinkerers_toolbox.ModMain;

public class ModRecipeTypes {
    public static RecipeType<TinkerRecipe> TINKER_RECIPE_TYPE = register(TinkerRecipe.Type.INSTANCE, TinkerRecipe.Type.ID);

    public static <T extends RecipeType<?>> T register(T recipeType, String name) {
        return Registry.register(Registries.RECIPE_TYPE, Identifier.of(ModMain.MOD_ID, name), recipeType);
    }

    public static void initialize() {
    }
}
