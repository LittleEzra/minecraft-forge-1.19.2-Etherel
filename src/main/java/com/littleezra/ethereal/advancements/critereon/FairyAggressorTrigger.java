package com.littleezra.ethereal.advancements.critereon;

import com.google.gson.JsonObject;
import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.entity.custom.IFairy;
import com.littleezra.ethereal.player.PlayerFairyAggressor;
import com.littleezra.ethereal.player.PlayerFairyAggressorProvider;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.function.Predicate;

public class FairyAggressorTrigger extends SimpleCriterionTrigger<FairyAggressorTrigger.TriggerInstance> {

    static final ResourceLocation ID = new ResourceLocation(Ethereal.MODID, "is_fairy_aggressor");
    //static final Predicate<TriggerInstance> IS_FAIRY_AGGRESSOR = (instance) -> {
    //    return
    //};

    public ResourceLocation getId() {
        return ID;
    }

    public FairyAggressorTrigger.TriggerInstance createInstance(JsonObject p_222621_, EntityPredicate.Composite p_222622_, DeserializationContext p_222623_) {
        return new FairyAggressorTrigger.TriggerInstance(this.getId(), p_222622_);
    }

    public void trigger(ServerPlayer serverPlayer) {
        this.trigger(serverPlayer, (instance) -> {
            return IFairy.playerIsMildAggressor(serverPlayer);
        });
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        private Player player;

        public TriggerInstance(ResourceLocation location, EntityPredicate.Composite predicate) {
            super(location, predicate);
        }
    }
}
