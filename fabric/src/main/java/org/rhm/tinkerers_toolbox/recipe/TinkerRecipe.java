package org.rhm.tinkerers_toolbox.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public class TinkerRecipe implements Recipe<RecipeInput> {
    @Override
    public boolean matches(RecipeInput input, World world) {
        return false;
    }

    @Override
    public ItemStack craft(RecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return null;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return null;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public RecipeType<TinkerRecipe> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<TinkerRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "tinker_recipe";
        private Type() {
        }
    }
}
