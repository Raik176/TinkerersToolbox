package org.rhm.tinkerers_toolbox.block.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.rhm.tinkerers_toolbox.ModMain;

public class ModBlockEntities {
    /*
    public static BlockEntityType<TinkerersBenchBlockEntity> TINKERERS_BENCH_ENTITY = register(
            BlockEntityType.Builder.create(TinkerersBenchBlockEntity::new,ModBlocks.TINKERERS_BENCH).build(),
            "tinkerers_bench"
    );
     */

    public static <T extends BlockEntityType<?>> T register(T blockEntityType, String name) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(ModMain.MOD_ID, name), blockEntityType);
    }

    public static void initialize() {
    }
}
