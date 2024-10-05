package org.rhm.tinkerers_toolbox;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rhm.tinkerers_toolbox.block.ModBlocks;
import org.rhm.tinkerers_toolbox.block.entity.ModBlockEntities;
import org.rhm.tinkerers_toolbox.components.ModComponents;
import org.rhm.tinkerers_toolbox.gui.ModScreens;
import org.rhm.tinkerers_toolbox.item.ModItems;
import org.rhm.tinkerers_toolbox.recipe.ModRecipeTypes;

public class ModMain implements ModInitializer {
    public static final String MOD_ID = "tinkerers_toolbox";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final RegistryKey<ItemGroup> ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(MOD_ID, "item_group"));
    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
        .icon(() -> new ItemStack(ModBlocks.TINKERERS_BENCH))
        .displayName(Text.translatable(MOD_ID + ".itemGroup"))
        .build();

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP_KEY, ITEM_GROUP);

        ModBlocks.initialize();
        ModBlockEntities.initialize();
        ModScreens.initialize();
        ModComponents.initialize();
        ModItems.initialize();
        ModRecipeTypes.initialize();
    }
}
