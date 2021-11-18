package com.hugomage.doyoubelieve.entities.ai;

import com.hugomage.doyoubelieve.entities.MartianEntity;
import com.hugomage.doyoubelieve.entities.RayGunLaserEntity;
import com.hugomage.doyoubelieve.registry.DYBEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class MartianShootGoal extends Goal {
    protected MartianEntity entity;
    private int timer;
    private final int COOLDOWN = 40;
    private int cooldownTimer;

    public MartianShootGoal(MartianEntity entity){
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        return this.entity.getTarget() != null && this.entity.distanceToSqr(this.entity.getTarget()) < 10.0f;
    }

    @Override
    public void tick() {
        super.tick();
        if(this.entity.getAttackState() == 0 || this.entity.getAttackState() == 1) {
            if (this.cooldownTimer < COOLDOWN) {
                this.cooldownTimer++;
            } else {
                if (this.timer < 20) {
                    this.timer++;
                    this.entity.getNavigation().stop();
                    this.entity.setAttackState(1);
                    if (this.timer == 13) {
                        this.tryHurtTarget(this.entity, this.entity.distanceTo(this.entity.getTarget()));
                    }
                } else {
                    this.timer = 0;
                    this.cooldownTimer = 0;
                    this.entity.setAttackState(0);
                }
            }
        }
        else{
            this.stop();
        }
    }

    protected void tryHurtTarget(MartianEntity entity, double distanceTo){
        BlockPos blockpos = this.entity.blockPosition();
        RayGunLaserEntity laser = DYBEntities.RAYGUNLASER.get().create(this.entity.level);
        laser.moveTo(blockpos.getX(), blockpos.getY(), blockpos.getZ());
        Vector3d vec = this.entity.getViewVector(1.0f);
        this.entity.level.addFreshEntity(laser);
        laser.setDeltaMovement(vec.x * 10, vec.y * 10, vec.z * 10);
    }

    protected double getAttackReachSqr(LivingEntity entity) {
        return (double)(entity.getBbWidth() * 1.2F * entity.getBbWidth() * 1.2F + entity.getBbWidth());
    }
}
