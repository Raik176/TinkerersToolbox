package org.rhm.tinkerers_toolbox.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.rhm.tinkerers_toolbox.gui.TinkerersBenchScreenHandler;

public class TinkerersBenchBlock extends Block {
    public TinkerersBenchBlock() {
        super(AbstractBlock.Settings.create()
            .sounds(BlockSoundGroup.METAL)
            .mapColor(MapColor.DEEPSLATE_GRAY)
            .resistance(2)
            .hardness(3)
            .solid()
            .nonOpaque()
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
                (syncId, inventory, p) -> new TinkerersBenchScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos)),
                getName()
            );
            player.openHandledScreen(factory);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.union( // kill me please
            VoxelShapes.cuboid(0, 0.625, 0, 1, 0.9375, 1), // Top part
            VoxelShapes.cuboid(0, 0, 0, 0.125, 0.625, 0.125), // Leg 1, top left
            VoxelShapes.cuboid(0.875, 0, 0, 1, 0.625, 0.125), // Leg 2, top right
            VoxelShapes.cuboid(0, 0, 0.875, 0.125, 0.625, 1), // Leg 3, bottom left
            VoxelShapes.cuboid(0.875, 0, 0, 1, 0.625, 0.125) // Leg 4, bottom right
        );
    }
}
