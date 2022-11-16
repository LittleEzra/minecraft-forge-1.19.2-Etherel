package com.littleezra.ethereal.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.littleezra.ethereal.entity.custom.Bushhog;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

import java.util.Map;

public class BushhogHide extends Behavior<Bushhog> {
    public BushhogHide(int min, int max) {
        super(ImmutableMap.of(), min, max);
    }

    protected boolean canStillUse(ServerLevel level, Bushhog bushhog, long p_22845_) {
        return bushhog.canHide();
    }

    @Override
    protected void start(ServerLevel level, Bushhog bushhog, long l) {
        bushhog.setIsHiding(true);

        super.start(level, bushhog, l);
    }

    @Override
    protected void stop(ServerLevel level, Bushhog bushhog, long l) {
        bushhog.setIsHiding(false);

        super.stop(level, bushhog, l);
    }
}
