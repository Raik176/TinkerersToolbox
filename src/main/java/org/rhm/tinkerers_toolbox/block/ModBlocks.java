package org.rhm.tinkerers_toolbox.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.rhm.tinkerers_toolbox.ModMain;
import org.rhm.tinkerers_toolbox.item.ModItems;

public class ModBlocks {
    public static final Block TINKERERS_BENCH = register(
        new TinkerersBenchBlock(),
        "tinkerers_bench",
        true
    );

    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        Identifier id = Identifier.of(ModMain.MOD_ID, name);

        if (shouldRegisterItem) ModItems.register(new BlockItem(block, new Item.Settings()), name);

        return Registry.register(Registries.BLOCK, id, block);
    }

    public static void initialize() {

    }
}
