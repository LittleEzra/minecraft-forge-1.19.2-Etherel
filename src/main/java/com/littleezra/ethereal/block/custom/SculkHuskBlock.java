package com.littleezra.ethereal.block.custom;

import com.littleezra.ethereal.entity.ModEntityTypes;
import com.littleezra.ethereal.entity.custom.Snatcher;
import com.littleezra.ethereal.entity.custom.TotemGolem;
import com.littleezra.ethereal.item.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.phys.BlockHitResult;

public class SculkHuskBlock extends Block {
    public SculkHuskBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (player.getItemInHand(hand).is(ModItems.SCULK_HEART.get()))
        {
            spawnGolem(state, level, pos, player);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.use(state, level, pos, player, hand, hitResult);
    }

    private void spawnGolem(BlockState state, Level level, BlockPos pos, Player player){
        player.sendSystemMessage(Component.literal("Sculk Heart used by " + player.getName().getString()));

        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
        level.levelEvent(2001, pos, Block.getId(state));

        Snatcher snatcher = ModEntityTypes.SNATCHER.get().create(level);
        snatcher.moveTo((double)pos.getX() + 0.5D, (double)pos.getY() + 0.05D, (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
        level.addFreshEntity(snatcher);

        for(ServerPlayer serverplayer : level.getEntitiesOfClass(ServerPlayer.class, snatcher.getBoundingBox().inflate(5.0D))) {
            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayer, snatcher);
        }

        level.blockUpdated(pos, Blocks.AIR);
    }
}
