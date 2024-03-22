package net.ninjadev.bouncyballs.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.ItemPickupAnimationS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.ninjadev.bouncyballs.init.ModEntities;
import net.ninjadev.bouncyballs.init.ModItems;
import net.ninjadev.bouncyballs.init.ModTags;

public class BouncyBallEntity extends ThrownItemEntity {

    private static final TrackedData<Float> BOUNCE = DataTracker.registerData(BouncyBallEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Integer> LIFE = DataTracker.registerData(BouncyBallEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<ItemStack> STACK = DataTracker.registerData(BouncyBallEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

    public BouncyBallEntity(World world, LivingEntity owner) {
        super(ModEntities.BOUNCY_BALL.get(), owner, world);
    }

    public BouncyBallEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.RED_BOUNCY_BALL.get();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            this.handleLife();
        }
        if (this.getStack().getItem() == ModItems.RAINBOW_BOUNCY_BALL) {
            NbtCompound nbt = this.getStack().getOrCreateNbt();
            nbt.putInt("Life", this.getLife());
        }
    }

    private void handleLife() {
        this.setLife(this.getLife() + 1);
        if (this.getLife() >= 6000) {
            this.discard();
        }
        if (this.isInLava()) {
            World world = this.getWorld();
            world.playSound(this, this.getBlockPos(),
                    SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.NEUTRAL,
                    0.5f, 1 + world.getRandom().nextFloat() * 0.4F + 0.8F
            );
            this.discard();
        }
    }

    public void handleBubbles(ParticleEffect particleEffect, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        Vec3d velocity = this.getVelocity();
        if (!velocity.equals(Vec3d.ZERO)) {
            this.getWorld().addParticle(particleEffect, x, y, z, velocityX, velocityY, velocityZ);
        }
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        super.onPlayerCollision(player);

        if (!(this.getWorld() instanceof ServerWorld world)) return;
        if (!(player instanceof ServerPlayerEntity serverPlayer)) return;

        if (this.getLife() < 10) return;
        PlayerInventory inventory = player.getInventory();
        if (this.getStack().isEmpty()) return;
        ItemStack copy = this.getStack().copy();
        if (copy.hasNbt()) {
            copy.getOrCreateNbt().remove("Life");
        }
        if (inventory.insertStack(copy)) {
            world.getChunkManager().sendToOtherNearbyPlayers(this, new ItemPickupAnimationS2CPacket(this.getId(), serverPlayer.getId(), 1));
            this.discard();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof PlayerEntity || entity instanceof BouncyBallEntity) return;
        this.setVelocity(Vec3d.ZERO);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (this.isInFluid() && blockHitResult.getSide() == Direction.UP) {
            this.setVelocity(Vec3d.ZERO);
            return;
        }

        this.bounce(blockHitResult);
    }

    private void bounce(BlockHitResult blockHitResult) {
        BlockState state = this.getWorld().getBlockState(blockHitResult.getBlockPos());
        Vec3d velocity = this.getVelocity();
        Vec3i hitVector = blockHitResult.getSide().getVector();
        Vec3d normal = new Vec3d(hitVector.getX(), hitVector.getY(), hitVector.getZ());

        double dotProduct = velocity.dotProduct(normal);

        Vec3d reflectionVector = new Vec3d(velocity.x - 2 * dotProduct * normal.x, velocity.y - 2 * dotProduct * normal.y, velocity.z - 2 * dotProduct * normal.z);
        reflectionVector = reflectionVector.multiply(this.getAndDecrementBounce(this.getCoefficient(state)));
        this.setVelocity(reflectionVector);
        if (!this.getWorld().isClient && !this.isInFluid()) {
            ServerWorld world = (ServerWorld) this.getWorld();
            world.playSound(this, this.getBlockPos(),
                    SoundEvents.ENTITY_SLIME_SQUISH_SMALL, SoundCategory.NEUTRAL,
                    this.getBounce() * .7f, this.getBounce() * 1.5f
            );
        }
    }

    private void setBounce(float bounce) {
        this.dataTracker.set(BOUNCE, bounce);
    }

    private float getBounce() {
        return this.dataTracker.get(BOUNCE);
    }

    private float getAndDecrementBounce(float dampening) {
        this.dataTracker.set(BOUNCE, this.getBounce() * dampening);
        return this.getBounce();
    }

    private float getCoefficient(BlockState state) {
        boolean dampening = state.isIn(ModTags.DAMPENS_BOUNCE);
        boolean increasing = state.isIn(ModTags.INCREASES_BOUNCE);
        if (dampening) return 0.075f;
        if (increasing) return .95f;
        return 0.85f;
    }

    public int getLife() {
        return this.dataTracker.get(LIFE);
    }

    public void setLife(int life) {
        this.dataTracker.set(LIFE, life);
    }

    @Override
    public ItemStack getStack() {
        return this.dataTracker.get(STACK);
    }

    public void setStack(ItemStack stack) {
        this.dataTracker.set(STACK, stack);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BOUNCE, 1f);
        this.dataTracker.startTracking(LIFE, 0);
        this.dataTracker.startTracking(STACK, ItemStack.EMPTY);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putFloat("Bounce", this.getBounce());
        nbt.putInt("Life", this.getLife());
        nbt.put("Stack", this.getStack().writeNbt(new NbtCompound()));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setBounce(nbt.getFloat("Bounce"));
        this.setLife(nbt.getInt("Life"));
        this.setStack(ItemStack.fromNbt(nbt.getCompound("Stack")));
    }
}
