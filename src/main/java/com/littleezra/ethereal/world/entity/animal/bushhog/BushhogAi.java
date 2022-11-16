package com.littleezra.ethereal.world.entity.animal.bushhog;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.littleezra.ethereal.entity.custom.Bushhog;
import com.littleezra.ethereal.entity.custom.Fairyfly;
import com.littleezra.ethereal.world.entity.ai.behavior.BushhogHide;
import com.littleezra.ethereal.world.entity.animal.fairyfly.FairyflyAi;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;

public class BushhogAi {


    public static Brain<?> makeBrain(Brain<Bushhog> brain) {
        initCoreActivity(brain);
        initIdleActivity(brain);
        initRetreatActivity(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void initCoreActivity(Brain<Bushhog> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(new Swim(0.8F), new LookAtTargetSink(45, 90), new MoveToTargetSink()));
    }

    //      public void addActivity(Activity p_21892_, int p_21893_, ImmutableList<? extends Behavior<? super E>> p_21894_) {
    //      public void addActivityWithConditions(Activity p_21904_, ImmutableList<? extends Pair<Integer, ? extends Behavior<? super E>>> p_21905_, Set<Pair<MemoryModuleType<?>, MemoryStatus>> p_21906_) {

    private static void initIdleActivity(Brain<Bushhog> brain) {

        brain.addActivityWithConditions(Activity.IDLE, ImmutableList.of(
                        Pair.of(4, new RunOne<Bushhog>(ImmutableList.of(Pair.of(new RandomStroll(1.0F), 2),
                        Pair.of(new SetWalkTargetFromLookTarget(1.0F, 3), 3),
                        Pair.of(new DoNothing(30, 60), 2),
                        Pair.of(new RunSometimes<>(new BushhogHide(30, 60), UniformInt.of(30, 60)), 3)
                        )))),
                ImmutableSet.of());
    }

    private static void initRetreatActivity(Brain<Bushhog> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.AVOID, 10, ImmutableList.of(SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 1.0F, 12, true), createIdleLookBehaviors(), createIdleMovementBehaviors(), new EraseMemoryIf<Bushhog>(BushhogAi::wantsToStopFleeing, MemoryModuleType.AVOID_TARGET)), MemoryModuleType.AVOID_TARGET);
    }

    private static RunOne<Bushhog> createIdleLookBehaviors() {
        return new RunOne<>(ImmutableList.of(Pair.of(new SetEntityLookTarget(EntityType.PLAYER, 8.0F), 1), Pair.of(new SetEntityLookTarget(8.0F), 1), Pair.of(new DoNothing(30, 60), 1)));
    }

    private static RunOne<Bushhog> createIdleMovementBehaviors() {
        return new RunOne<>(ImmutableList.of(Pair.of(new RandomStroll(1.0F), 2)));
    }

    public static void updateActivity(Bushhog fairyfly) {
        fairyfly.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.IDLE));
    }

    public static boolean wantsToStopFleeing(Bushhog bushhog) {
        Brain<Bushhog> brain = bushhog.getBrain();
        if (!brain.hasMemoryValue(MemoryModuleType.AVOID_TARGET)) {
            return true;
        } else {
            LivingEntity livingentity = brain.getMemory(MemoryModuleType.AVOID_TARGET).get();
            return bushhog.closeToAggressor();
        }
    }
}
