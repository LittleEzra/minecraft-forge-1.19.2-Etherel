package com.littleezra.ethereal.advancements.critereon;

import com.google.gson.JsonObject;
import com.littleezra.ethereal.Ethereal;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class CnithereaBounceTrigger extends SimpleCriterionTrigger<CnithereaBounceTrigger.TriggerInstance> {

    static final ResourceLocation ID = new ResourceLocation(Ethereal.MODID, "cnitherea_bounce");

    public ResourceLocation getId() {
        return ID;
    }

    public CnithereaBounceTrigger.TriggerInstance createInstance(JsonObject p_222621_, EntityPredicate.Composite p_222622_, DeserializationContext p_222623_) {
        return new CnithereaBounceTrigger.TriggerInstance(this.getId(), p_222622_);
    }

    public void trigger(ServerPlayer serverPlayer) {
        this.trigger(serverPlayer, (instance) -> {
            return true;
        });
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        private Player player;

        public TriggerInstance(ResourceLocation location, EntityPredicate.Composite predicate) {
            super(location, predicate);
        }
    }
}
