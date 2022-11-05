package com.littleezra.ethereal.item.custom;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.littleezra.ethereal.block.ModBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Optional;
import java.util.function.Supplier;

public class VerdantSapItem extends Item {

    public static final Supplier<BiMap<Block, Block>> RICH_VERSION = Suppliers.memoize(() -> {
        return ImmutableBiMap.<Block, Block>builder()
                .put(Blocks.SPRUCE_LOG, ModBlocks.RICH_LOG.get())
                .put(Blocks.STRIPPED_SPRUCE_LOG, ModBlocks.STRIPPED_RICH_LOG.get())
                .put(Blocks.SPRUCE_WOOD, ModBlocks.RICH_WOOD.get())
                .put(Blocks.STRIPPED_SPRUCE_WOOD, ModBlocks.STRIPPED_RICH_WOOD.get())
                .build();
    });
    public static final Supplier<BiMap<Block, Block>> SPRUCE_VERSION = Suppliers.memoize(() -> {
        return RICH_VERSION.get().inverse();
    });

    public VerdantSapItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);

        return getRich(blockstate).map((state) -> {
            Player player = context.getPlayer();
            ItemStack itemstack = context.getItemInHand();
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
            }

            itemstack.shrink(1);
            level.setBlock(blockpos, state, 11);
            level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, state));
            level.levelEvent(player, 3003, blockpos, 0);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }).orElse(InteractionResult.PASS);
    }

    public static Optional<BlockState> getRich(BlockState state) {
        return Optional.ofNullable(RICH_VERSION.get().get(state.getBlock())).map((block) -> {
            return block.withPropertiesOf(state);
        });
    }
}
