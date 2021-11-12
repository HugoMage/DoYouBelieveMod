package com.hugomage.doyoubelieve.entities;

import com.hugomage.doyoubelieve.registry.DYBSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.IFlinging;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class BigfootEntity extends AnimalEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private static final DataParameter<Integer> EAT_COUNTER = EntityDataManager.defineId(BigfootEntity.class, DataSerializers.INT);
    private static final Predicate<ItemEntity> TEMPTATION_ITEMS = (p_213575_0_) -> {
        Item item = p_213575_0_.getItem().getItem();
        return (item == Blocks.SWEET_BERRY_BUSH.asItem()) && p_213575_0_.isAlive() && !p_213575_0_.hasPickUpDelay();
    };
    private int attackAnimationRemainingTicks;
    private int ticksSinceEaten;

    public BigfootEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
        this.setCanPickUpLoot(true);
        this.setPathfindingMalus(PathNodeType.DANGER_OTHER, 0.0F);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(event.isMoving()){
            event.getController().setAnimation(new AnimationBuilder()
                    .addAnimation("animation.BigFoot.walk", true));
            return PlayState.CONTINUE;
        }
        else {
            event.getController().setAnimation(new AnimationBuilder()
                    .addAnimation("animation.basalt_capsling.idle", true));
            return PlayState.CONTINUE;
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(10, new BigfootEntity.EatBerriesGoal(1.2F, 12, 2));
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PlayerEntity.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(7, new BigfootEntity.FindItemsGoal());
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes(){
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.30D)
                .add(Attributes.ATTACK_DAMAGE, 10D)
                .add(Attributes.ATTACK_SPEED, 6D)
                .add(Attributes.ATTACK_KNOCKBACK, 1D);
    }

    public boolean doHurtTarget(Entity p_70652_1_) {
        if (!(p_70652_1_ instanceof LivingEntity)) {
            return false;
        } else {
            this.attackAnimationRemainingTicks = 10;
            this.level.broadcastEntityEvent(this, (byte)4);
            this.playSound(DYBSounds.BIGFOOT_ATTACK.get(), 1.0F, this.getVoicePitch());
            return IFlinging.hurtAndThrowTarget(this, (LivingEntity)p_70652_1_);
        }
    }


    public void aiStep() {
        if (!this.level.isClientSide && this.isAlive() && this.isEffectiveAi()) {
            ++this.ticksSinceEaten;
            ItemStack itemstack = this.getItemBySlot(EquipmentSlotType.MAINHAND);
        }
        if (this.attackAnimationRemainingTicks > 0) {
            --this.attackAnimationRemainingTicks;
        }

        super.aiStep();
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(EAT_COUNTER, 0);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte p_70103_1_) {
        if (p_70103_1_ == 4) {
            this.attackAnimationRemainingTicks = 10;
            this.playSound(DYBSounds.BIGFOOT_ATTACK.get(), 0.5F, this.getVoicePitch());
        } else {
            super.handleEntityEvent(p_70103_1_);
        }

    }

    protected int getExperienceReward(PlayerEntity p_70693_1_) {
        if (this.isBaby()) {
            this.xpReward = (int)((float)this.xpReward * 6.5F);
        }

        return super.getExperienceReward(p_70693_1_);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound ()
    {
        return DYBSounds.BIGFOOT_AMBIENT.get();
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return DYBSounds.BIGFOOT_DEATH.get();
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return DYBSounds.BIGFOOT_HURT.get();
    }


    @Override
    protected void playStepSound( BlockPos pos, BlockState blockIn ) {
        if ( !blockIn.getMaterial().isLiquid()) {
            this.playSound( SoundEvents.IRON_GOLEM_STEP, this.getSoundVolume() * 0.3F, this.getSoundVolume() );
        }
    }

    private boolean canMove() {
        return !this.isSleeping();
    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<BigfootEntity>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return null;
    }

    class FindItemsGoal extends Goal {
        public FindItemsGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }
        public boolean canUse() {
            if (!BigfootEntity.this.getItemBySlot(EquipmentSlotType.MAINHAND).isEmpty()) {
                return false;
            } else if (BigfootEntity.this.getTarget() == null && BigfootEntity.this.getLastHurtByMob() == null) {
                if (!BigfootEntity.this.canMove()) {
                    return false;
                } else if (BigfootEntity.this.getRandom().nextInt(10) != 0) {
                    return false;
                } else {
                    List<ItemEntity> list = BigfootEntity.this.level.getEntitiesOfClass(ItemEntity.class, BigfootEntity.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), BigfootEntity.TEMPTATION_ITEMS);
                    return !list.isEmpty() && BigfootEntity.this.getItemBySlot(EquipmentSlotType.MAINHAND).isEmpty();
                }
            } else {
                return false;
            }
        }

        public void tick() {
            List<ItemEntity> list = BigfootEntity.this.level.getEntitiesOfClass(ItemEntity.class, BigfootEntity.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), BigfootEntity.TEMPTATION_ITEMS);
            ItemStack itemstack = BigfootEntity.this.getItemBySlot(EquipmentSlotType.MAINHAND);
            if (itemstack.isEmpty() && !list.isEmpty()) {
                BigfootEntity.this.getNavigation().moveTo(list.get(0), (double)1.2F);
            }

        }

        public void start() {
            List<ItemEntity> list = BigfootEntity.this.level.getEntitiesOfClass(ItemEntity.class, BigfootEntity.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), BigfootEntity.TEMPTATION_ITEMS);
            if (!list.isEmpty()) {
                BigfootEntity.this.getNavigation().moveTo(list.get(0), (double)1.2F);
            }

        }
    }

    public class EatBerriesGoal extends MoveToBlockGoal {
        protected int ticksWaited;

        public EatBerriesGoal(double p_i50737_2_, int p_i50737_4_, int p_i50737_5_) {
            super(BigfootEntity.this, p_i50737_2_, p_i50737_4_, p_i50737_5_);
        }

        public double acceptedDistance() {
            return 2.0D;
        }

        public boolean shouldRecalculatePath() {
            return this.tryTicks % 100 == 0;
        }

        protected boolean isValidTarget(IWorldReader p_179488_1_, BlockPos p_179488_2_) {
            BlockState blockstate = p_179488_1_.getBlockState(p_179488_2_);
            return blockstate.is(Blocks.SWEET_BERRY_BUSH) && blockstate.getValue(SweetBerryBushBlock.AGE) >= 2;
        }

        public void tick() {
            if (this.isReachedTarget()) {
                if (this.ticksWaited >= 40) {
                    this.onReachedTarget();
                } else {
                    ++this.ticksWaited;
                }
            } else if (!this.isReachedTarget() && BigfootEntity.this.random.nextFloat() < 0.05F) {
                BigfootEntity.this.playSound(SoundEvents.FOX_SNIFF, 1.0F, 1.0F);
            }

            super.tick();
        }

        protected void onReachedTarget() {
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(BigfootEntity.this.level, BigfootEntity.this)) {
                BlockState blockstate = BigfootEntity.this.level.getBlockState(this.blockPos);
                if (blockstate.is(Blocks.SWEET_BERRY_BUSH)) {
                    int i = blockstate.getValue(SweetBerryBushBlock.AGE);
                    blockstate.setValue(SweetBerryBushBlock.AGE, Integer.valueOf(1));
                    int j = 1 + BigfootEntity.this.level.random.nextInt(2) + (i == 3 ? 1 : 0);
                    ItemStack itemstack = BigfootEntity.this.getItemBySlot(EquipmentSlotType.MAINHAND);
                    if (itemstack.isEmpty()) {
                        BigfootEntity.this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.SWEET_BERRIES));
                        --j;
                    }

                    if (j > 0) {
                        Block.popResource(BigfootEntity.this.level, this.blockPos, new ItemStack(Items.SWEET_BERRIES, j));
                    }

                    BigfootEntity.this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 1.0F, 1.0F);
                    BigfootEntity.this.level.setBlock(this.blockPos, blockstate.setValue(SweetBerryBushBlock.AGE, Integer.valueOf(1)), 2);
                }
            }
        }

        public boolean canUse() {
            return !BigfootEntity.this.isSleeping() && super.canUse();
        }

        public void start() {
            this.ticksWaited = 0;
            super.start();
        }
    }
}
