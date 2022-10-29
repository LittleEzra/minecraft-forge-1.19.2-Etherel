package com.littleezra.ethereal.block.custom;

import com.littleezra.ethereal.sound.ModSounds;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EtherealSpikeBlock extends Block {

    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty NATURAL = BooleanProperty.create("natural");
    public static final DamageSource ETHEREAL_SPIKE_DAMAGE = new DamageSource("ethereal_spike_damage");

    public static final VoxelShape ACTIVATED_SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D);
    public static final VoxelShape DEACTIVATED_SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 2.0D, 10.0D);

    public EtherealSpikeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(ACTIVATED, false)
                .setValue(POWERED,   false)
                .setValue(NATURAL,   false)
        );
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return state.getValue(ACTIVATED) ? ACTIVATED_SHAPE : DEACTIVATED_SHAPE;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState p_49928_, BlockGetter p_49929_, BlockPos p_49930_) {
        return true;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos blockPos) {
        return canSupportCenter(levelReader, blockPos.below(), Direction.UP);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED, POWERED, NATURAL);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos blockPos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && state.getValue(ACTIVATED))
        {
            livingEntity.hurt(ETHEREAL_SPIKE_DAMAGE, 2f);
            livingEntity.knockback(blockPos.getX(),blockPos.getY(),blockPos.getZ());
        }

        if (!state.getValue(ACTIVATED) && entity instanceof LivingEntity)
        {
            if (!entity.isShiftKeyDown()){
                activate(state, level, blockPos);
            }
        }
    }

    // Used to spawn the crit particles when extending
    private static void spawnParticles(Level level, BlockPos blockPos)
    {
        if (level instanceof ServerLevel serverLevel)
        {
            serverLevel.sendParticles(ParticleTypes.CRIT,
                    blockPos.getX() + .5d,
                    blockPos.getZ() + .5d,
                    blockPos.getY() + .5d,
                    10,
                    0d,
                    0.25d,
                    0d,
                    0.25d);
        } else
        {
            level.addParticle(ParticleTypes.CRIT,
                    blockPos.getX() + .5d,
                    blockPos.getY() + .5d,
                    blockPos.getZ() + .5d,
                    0d,
                    0.25d,
                    0d);
        }
    }
    private static void activate(BlockState state, Level level, BlockPos blockPos){
        level.setBlock(blockPos, state.setValue(ACTIVATED, true), 3);
        level.playSound(null, blockPos, ModSounds.ETHEREAL_SPIKE_ACTIVATE.get(), SoundSource.BLOCKS, 1F, 1F);
        spawnParticles(level, blockPos);
    }
    private static void reset(BlockState state, Level level, BlockPos blockPos)
    {
        level.setBlock(blockPos, state.setValue(ACTIVATED, false), 3);
        level.playSound(null, blockPos, ModSounds.ETHEREAL_SPIKE_RESET.get(), SoundSource.BLOCKS, 1F, 1F);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        if (!level.isClientSide && hand == InteractionHand.MAIN_HAND && !state.getValue(NATURAL)){
            if (state.getValue(ACTIVATED) && !state.getValue(POWERED)){
                reset(state, level, blockPos);
            }
        }

        return super.use(state, level, blockPos, player, hand, hitResult);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos blockPos, boolean p_57552_)
    {
        if (level.getBlockState(pos.below()).isAir()){
            level.destroyBlock(pos, true);
        }

        if (!level.isClientSide) {
            boolean flag = level.hasNeighborSignal(pos);
            if (flag != state.getValue(POWERED)) {
                if (state.getValue(ACTIVATED) != flag) {
                    state = state.setValue(ACTIVATED, Boolean.valueOf(flag));
                    level.playSound(null, pos, (flag ? ModSounds.ETHEREAL_SPIKE_ACTIVATE.get() : ModSounds.ETHEREAL_SPIKE_RESET.get()), SoundSource.BLOCKS, 1F, 1F);
                    spawnParticles(level, blockPos);
                }

                level.setBlock(pos, state.setValue(POWERED, Boolean.valueOf(flag)), 2);
            }
        }
    }
}
