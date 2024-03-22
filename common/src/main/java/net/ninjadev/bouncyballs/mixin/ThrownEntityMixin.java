package net.ninjadev.bouncyballs.mixin;

import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;
import net.ninjadev.bouncyballs.entity.BouncyBallEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ThrownEntity.class)
public class ThrownEntityMixin {
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"))
    public void stopBubbles(World instance, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        ThrownEntity entity = (ThrownEntity) (Object) this;
        if (entity instanceof BouncyBallEntity bouncyBall) {
            bouncyBall.handleBubbles(parameters, x, y, z, velocityX, velocityY, velocityZ);
        } else {
            instance.addParticle(parameters, x, y, z, velocityX, velocityY, velocityZ);
        }
    }
}
