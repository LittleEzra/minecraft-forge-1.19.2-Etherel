package com.littleezra.ethereal.entity.custom;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.player.PlayerFairyAggressor;
import com.littleezra.ethereal.player.PlayerFairyAggressorProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;

public interface IFairy {
    boolean closeToAggressor();

    static boolean playerIsAggressor(Player player)
    {
        boolean flag = false;

        LazyOptional<PlayerFairyAggressor> lazyOptional = player.getCapability(PlayerFairyAggressorProvider.PLAYER_FAIRY_AGGRESSOR);
        if (lazyOptional.isPresent()){
            PlayerFairyAggressor aggressor = lazyOptional.orElseGet(() ->{
                return null;
            });
            flag = aggressor.getHurtFairy() || aggressor.getHurtOak();
        }

        return flag;
    }
}