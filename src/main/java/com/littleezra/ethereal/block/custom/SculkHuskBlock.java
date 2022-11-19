package com.littleezra.ethereal.block.custom;

import com.littleezra.ethereal.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
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
    }
}
