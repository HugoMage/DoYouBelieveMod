package com.hugomage.doyoubelieve.common.entities;

import com.hugomage.doyoubelieve.common.entities.ai.MartianShootGoal;
import com.hugomage.doyoubelieve.registry.DYBItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class MartianEntity extends MonsterEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private static final DataParameter<Integer> ATTACK_STATE = EntityDataManager.defineId(MartianEntity.class, DataSerializers.INT);

    public MartianEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_STATE, 0);
    }

    public void setAttackState(int state){
        this.entityData.set(ATTACK_STATE, state);
    }

    public int getAttackState(){
        return this.entityData.get(ATTACK_STATE);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(this.getAttackState() == 1){
            event.getController().setAnimation(new AnimationBuilder()
                    .addAnimation("animation.Martian.shoot", false));
            return PlayState.CONTINUE;
        }
        if(event.isMoving()){
            if(this.getItemInHand(Hand.MAIN_HAND) != null){
                event.getController().setAnimation(new AnimationBuilder()
                        .addAnimation("animation.Martian.walk_item", true));
                return PlayState.CONTINUE;
            }
            event.getController().setAnimation(new AnimationBuilder()
                    .addAnimation("animation.Martian.walk", true));
            return PlayState.CONTINUE;
        }
        else {
            if(this.getItemInHand(Hand.MAIN_HAND) != null){
                event.getController().setAnimation(new AnimationBuilder()
                        .addAnimation("animation.Martian.idle_item", true));
                return PlayState.CONTINUE;
            }
            event.getController().setAnimation(new AnimationBuilder()
                    .addAnimation("animation.Martian.idle", true));
            return PlayState.CONTINUE;
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MartianShootGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));

    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes(){
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.30D)
                .add(Attributes.ATTACK_DAMAGE, 7D)
                .add(Attributes.ATTACK_SPEED, 6D)
                .add(Attributes.ATTACK_KNOCKBACK, 1D);
    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<MartianEntity>(this, "controller", 8, this::predicate));
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        this.setItemInHand(Hand.MAIN_HAND, new ItemStack(DYBItems.RAYGUN.get(), 1));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
