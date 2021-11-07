package com.hugomage.doyoubelieve.entities;

import com.hugomage.doyoubelieve.registries.SoundRegistry;
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
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class BigfootEntity extends AnimalEntity {
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
            this.playSound(SoundRegistry.BIGFOOT_ATTACK.get(), 1.0F, this.getVoicePitch());
            return IFlinging.hurtAndThrowTarget(this, (LivingEntity)p_70652_1_);
        }
    }

    public boolean isBerry(ItemStack p_70877_1_) {
        return p_70877_1_.getItem() == Blocks.BAMBOO.asItem();
    }

    // TODO - delete? it's unused.
    private void handleEating() {
        if (!this.isEating() &&  !this.getItemBySlot(EquipmentSlotType.MAINHAND).isEmpty() && this.random.nextInt(80) == 1) {
            this.eat(true);
        } else if (this.getItemBySlot(EquipmentSlotType.MAINHAND).isEmpty()) {
            this.eat(false);
        }

        if (this.isEating()) {
            this.addEatingParticles();
            if (!this.level.isClientSide && this.getEatCounter() > 80 && this.random.nextInt(20) == 1) {
                if (this.getEatCounter() > 100 && this.isBerry(this.getItemBySlot(EquipmentSlotType.MAINHAND))) {
                    if (!this.level.isClientSide) {
                        this.setItemSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
                    }

                }

                this.eat(false);
                return;
            }

            this.setEatCounter(this.getEatCounter() + 1);
        }

    }

    private void addEatingParticles() {
        if (this.getEatCounter() % 5 == 0) {
            this.playSound(SoundEvents.PANDA_EAT, 0.5F + 0.5F * (float)this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);

            for(int i = 0; i < 6; ++i) {
                Vector3d vector3d = new Vector3d(((double)this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, ((double)this.random.nextFloat() - 0.5D) * 0.1D);
                vector3d = vector3d.xRot(-this.xRot * ((float)Math.PI / 180F));
                vector3d = vector3d.yRot(-this.yRot * ((float)Math.PI / 180F));
                double d0 = (double)(-this.random.nextFloat()) * 0.6D - 0.3D;
                Vector3d vector3d1 = new Vector3d(((double)this.random.nextFloat() - 0.5D) * 0.8D, d0, 1.0D + ((double)this.random.nextFloat() - 0.5D) * 0.4D);
                vector3d1 = vector3d1.yRot(-this.yBodyRot * ((float)Math.PI / 180F));
                vector3d1 = vector3d1.add(this.getX(), this.getEyeY() + 1.0D, this.getZ());
                this.level.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItemBySlot(EquipmentSlotType.MAINHAND)), vector3d1.x, vector3d1.y, vector3d1.z, vector3d.x, vector3d.y + 0.05D, vector3d.z);
            }
        }
    }

    private boolean canEat(ItemStack p_213464_1_) {
        return p_213464_1_.getItem().isEdible() && this.getTarget() == null && this.onGround;
    }

    public void aiStep() {
        if (!this.level.isClientSide && this.isAlive() && this.isEffectiveAi()) {
            ++this.ticksSinceEaten;
            ItemStack itemstack = this.getItemBySlot(EquipmentSlotType.MAINHAND);
            if (this.canEat(itemstack)) {
                if (this.ticksSinceEaten > 600) {
                    ItemStack itemstack1 = itemstack.finishUsingItem(this.level, this);
                    if (!itemstack1.isEmpty()) {
                        this.setItemSlot(EquipmentSlotType.MAINHAND, itemstack1);
                    }

                    this.ticksSinceEaten = 0;
                } else if (this.ticksSinceEaten > 560 && this.random.nextFloat() < 0.1F) {
                    this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
                    this.level.broadcastEntityEvent(this, (byte)45);
                }
            }
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
            this.playSound(SoundRegistry.BIGFOOT_ATTACK.get(), 0.5F, this.getVoicePitch());
        } else {
            super.handleEntityEvent(p_70103_1_);
        }

    }

    public int getEatAnimation() {
        return this.ticksSinceEaten;
    }

    @OnlyIn(Dist.CLIENT)
    public int getAttackAnimationRemainingTicks() {
        return this.attackAnimationRemainingTicks;
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
        return SoundRegistry.BIGFOOT_AMBIENT.get();
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundRegistry.BIGFOOT_DEATH.get();
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundRegistry.BIGFOOT_HURT.get();
    }

    public SoundEvent getEatingSound(ItemStack p_213353_1_) {
        return SoundEvents.PANDA_EAT;
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

    public boolean isEating() {
        return this.entityData.get(EAT_COUNTER) > 0;
    }

    public void eat(boolean p_213534_1_) {
        this.entityData.set(EAT_COUNTER, p_213534_1_ ? 1 : 0);
    }

    private int getEatCounter() {
        return this.entityData.get(EAT_COUNTER);
    }

    private void setEatCounter(int p_213571_1_) {
        this.entityData.set(EAT_COUNTER, p_213571_1_);
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
