package com.hugomage.doyoubelieve.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class RayGunLaserEntity extends AbstractArrowEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    public LivingEntity shooter;
    public LivingEntity entity;

    public RayGunLaserEntity(EntityType<? extends RayGunLaserEntity> type, LivingEntity shooter, World worldIn) {
        super(type, shooter, worldIn);
        this.shooter = shooter;
        this.setNoGravity(true);
        this.setNoPhysics(true);
        this.setBaseDamage(3.7D);
    }

    public RayGunLaserEntity(EntityType<RayGunLaserEntity> type, World world) {
        super(type, world);
        this.setBaseDamage(3.7D);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
            return PlayState.CONTINUE;
    }

    @Override
    public void tick() {
        super.tick();
        if(this.isInWater() || this.inGround){
            this.kill();
        }
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<RayGunLaserEntity>(this, "controller", 2, this::predicate));
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        int arrowTimer = 50;
        this.entity = entity;
        this.entity.hurt(DamageSource.arrow(this, this.shooter), this.entity.getArmorValue() /2.0f * 0.09f);
    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
