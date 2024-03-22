package net.ninjadev.bouncyballs.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import net.ninjadev.bouncyballs.entity.BouncyBallEntity;
import net.ninjadev.bouncyballs.init.ModItems;

public class BouncyBallItem extends Item {

    public BouncyBallItem(DyeColor color) {
        super(new Settings().maxCount(16));
        this.color = color;
    }

    private DyeColor color;

    public DyeColor getColor() {
        return color;
    }

    public void setColor(DyeColor color) {
        this.color = color;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (stack.getItem() instanceof BouncyBallItem) {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(stack);
        }
        return TypedActionResult.fail(stack);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity living, int remainingUseTicks) {
        if (!(living instanceof PlayerEntity player)) return;
        float throwStrength = this.getThrowStrength(this.getMaxUseTime(stack) - remainingUseTicks);
        if (throwStrength < 0.25f) return;

        if (!world.isClient) {
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
            ItemStack ball = stack.copy();
            ball.setCount(1);
            BouncyBallEntity bouncyBallEntity = new BouncyBallEntity(world, player);
            bouncyBallEntity.setItem(ball);
            bouncyBallEntity.setStack(ball);
            bouncyBallEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, throwStrength * 1.5f, 0.25f);
            world.spawnEntity(bouncyBallEntity);
        }

        if (!player.getAbilities().creativeMode) {
            player.incrementStat(Stats.USED.getOrCreateStat(this));
            stack.decrement(1);
        }
    }

    private float getThrowStrength(int ticksUsed) {
        float seconds = ticksUsed / 20f;
        return Math.min(seconds, 1.0f);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    public void translateArm(MatrixStack matrices, Arm arm, ItemStack stack, float tickDelta) {
        boolean rightArm = arm == Arm.RIGHT;
        int sideOffset = rightArm ? 1 : -1;
        matrices.translate((float)sideOffset * -0.25f, 0.7f, 0.1f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-55.0f));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)sideOffset * 35.3f));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)sideOffset * -9.785f));
        float timeHeld = (float)stack.getMaxUseTime() - ((float) MinecraftClient.getInstance().player.getItemUseTimeLeft() - tickDelta + 1.0f);
        float raiseHeight = timeHeld / 10.0f;
        if (raiseHeight > 1.0f) {
            raiseHeight = 1.0f;
        }
        if (raiseHeight > 0.1f) {
            float g = MathHelper.sin((timeHeld - 0.1f) * 1.3f);
            float h = raiseHeight - 0.1f;
            float j = g * h;
            matrices.translate(j * 0.0f, j * 0.004f, j * 0.0f);
        }
        matrices.translate(0.0f, 0.0f, raiseHeight * 0.2f);
        matrices.scale(1.0f, 1.0f, 1.0f + raiseHeight * 0.2f);
        matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees((float)sideOffset * 45.0f));
    }
}
