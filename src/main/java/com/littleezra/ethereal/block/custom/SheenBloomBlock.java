package com.littleezra.ethereal.block.custom;

import com.littleezra.ethereal.block.ModBlocks;
import com.littleezra.ethereal.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SheenBloomBlock extends BushBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_5;
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(6.0D, 11.0D, 6.0D, 10.0D, 16.0D, 10.0D),
            Block.box(6.0D, 6.0D, 6.0D, 10.0D, 16.0D, 10.0D),
            Block.box(5.0D, 5.0D, 5.0D, 11.0D, 16.0D, 11.0D),
            Block.box(3.0D, 3.0D, 3.0D, 12.0D, 16.0D, 12.0D),
            Block.box(4.0D, 3.0D, 4.0D, 12.0D, 16.0D, 12.0D),
            Block.box(5.0D, 2.0D, 5.0D, 11.0D, 16.0D, 11.0D)};

    public SheenBloomBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[state.getValue(AGE)];
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {

        if (state.getValue(AGE) > 2) {
            if (player.getItemInHand(hand).is(Items.SHEARS)) {
                popResource(level, pos, new ItemStack(ModItems.SHIMMER_FLOWER.get(), 1));
                level.setBlock(pos, state.setValue(AGE, Integer.valueOf(0)), 2);

                return InteractionResult.sidedSuccess(level.isClientSide());
            } else if (state.getValue(AGE) == 5) {
                popResource(level, pos, new ItemStack(ModItems.SHIMMER_FRUIT.get(), 1));
                level.setBlock(pos, state.setValue(AGE, Integer.valueOf(0)), 2);

                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }

        return InteractionResult.PASS;
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos pos) {
        return state.is(ModBlocks.RICH_LEAVES.get());
    }

    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos pos) {
        return levelReader.getBlockState(pos.above()).is(ModBlocks.RICH_LEAVES.get());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    public boolean isMaxAge(BlockState state) {
        return state.getValue(AGE) >= 5;
    }

    public boolean isRandomlyTicking(BlockState state) {
        return !this.isMaxAge(state);
    }

    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (level.getRawBrightness(pos, 0) >= 9) {
            int i = state.getValue(AGE);
            if (i < 5) {
                float f = getGrowthSpeed(this, level, pos);
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt((int)(25.0F / f) + 1) == 0)) {
                    level.setBlock(pos, state.setValue(AGE, i + 1), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(level, pos, state);
                }
            }
        }
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return getLevelForAge(state.getValue(AGE));
    }

    public int getLevelForAge(int age){
        int level = 0;
        switch (age) {
            default -> {
            }
            case 2 -> level = 2;
            case 3 -> level = 4;
            case 4 -> level = 6;
            case 5 -> level = 9;
        }
        return level;
    }

    protected static float getGrowthSpeed(Block block, BlockGetter getter, BlockPos pos) {
        float f = 1.0F;
        BlockPos blockpos = pos.above();

        for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                BlockState blockstate = getter.getBlockState(blockpos.offset(i, 0, j));
                if (blockstate.getBlockHolder().containsTag(BlockTags.LEAVES)) {
                    f1 = 1.0F;
                    if (blockstate.is(ModBlocks.RICH_LEAVES.get())) {
                        f1 = 3.0F;
                    }
                }

                if (i != 0 || j != 0) {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        BlockPos north = pos.north();
        BlockPos south = pos.south();
        BlockPos west = pos.west();
        BlockPos east = pos.east();
        boolean flag = getter.getBlockState(west).is(block) || getter.getBlockState(east).is(block);
        boolean flag1 = getter.getBlockState(north).is(block) || getter.getBlockState(south).is(block);
        if (flag && flag1) {
            f /= 2.0F;
        } else {
            boolean flag2 = getter.getBlockState(west.north()).is(block) || getter.getBlockState(east.north()).is(block) || getter.getBlockState(east.south()).is(block) || getter.getBlockState(west.south()).is(block);
            if (flag2) {
                f /= 2.0F;
            }
        }

        return f;
    }
}
