package dev.devce.rocketnautics.content.blocks.world;

import com.mojang.serialization.MapCodec;
import com.simibubi.create.content.logistics.crate.CrateBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;

public class MossBlock extends DirectionalBlock {
    public static final MapCodec<MossBlock> CODEC = simpleCodec(MossBlock::new);
    private static final EnumMap<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);

    static {
        SHAPES.put(Direction.UP, box(2, 0, 2, 14, 4, 14));
        SHAPES.put(Direction.DOWN, box(2, 12, 2, 14, 16, 14));
        SHAPES.put(Direction.NORTH, box(2, 2, 12, 14, 14, 16));
        SHAPES.put(Direction.SOUTH, box(2, 2, 0, 14, 14, 4));
        SHAPES.put(Direction.EAST, box(0, 2, 2, 4, 14, 14));
        SHAPES.put(Direction.WEST, box(12, 2, 2, 16, 14, 14));
    }

    public MossBlock(Properties p_52591_) {
        super(p_52591_);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.UP));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getClickedFace();
        return this.defaultBlockState().setValue(FACING, facing);
    }

    @Override
    protected @NotNull MapCodec<? extends DirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPES.get(p_60555_.getValue(FACING));
    }

    @Override
    protected boolean canSurvive(BlockState p_60525_, LevelReader p_60526_, BlockPos p_60527_) {
        return canSupportCenter(p_60526_, p_60527_.relative(p_60525_.getValue(FACING).getOpposite()), p_60525_.getValue(FACING));
    }

    @Override
    protected BlockState updateShape(BlockState p_58143_, Direction p_58144_, BlockState p_58145_, LevelAccessor p_58146_, BlockPos p_58147_, BlockPos p_58148_) {
        return p_58144_.getOpposite() == p_58143_.getValue(FACING) && !p_58143_.canSurvive(p_58146_, p_58147_) ? Blocks.AIR.defaultBlockState() : p_58143_;
    }
}
