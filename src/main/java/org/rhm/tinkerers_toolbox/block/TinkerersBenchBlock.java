package org.rhm.tinkerers_toolbox.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.rhm.tinkerers_toolbox.gui.TinkerersBenchScreenHandler;

public class TinkerersBenchBlock extends Block {
    public TinkerersBenchBlock() {
        super(AbstractBlock.Settings.create()
                .sounds(BlockSoundGroup.METAL)
                .mapColor(MapColor.DEEPSLATE_GRAY)
                .resistance(2)
                .solid()
        );
    }

    public TinkerersBenchBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends TinkerersBenchBlock> getCodec() {
        return createCodec(TinkerersBenchBlock::new);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory factory = new SimpleNamedScreenHandlerFactory(
                    (syncId, inventory, p) -> new TinkerersBenchScreenHandler(syncId, inventory, pos),
                    getName()
            );
            player.openHandledScreen(factory);
        }
        return ActionResult.SUCCESS;
    }
}
