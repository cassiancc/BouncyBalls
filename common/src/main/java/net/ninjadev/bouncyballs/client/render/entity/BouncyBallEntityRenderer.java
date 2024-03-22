package net.ninjadev.bouncyballs.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;

@Environment(value = EnvType.CLIENT)
public class BouncyBallEntityRenderer<T extends Entity> extends EntityRenderer<T> {

    private static final float MIN_DISTANCE = 12.25f;
    private final ItemRenderer itemRenderer;

    public BouncyBallEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (entity.age < 2 && this.dispatcher.camera.getFocusedEntity().squaredDistanceTo(entity) < MIN_DISTANCE) {
            return;
        }

        matrices.push();
        matrices.translate(0,0.05,0);
        matrices.multiply(this.dispatcher.getRotation());
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f));
        this.itemRenderer.renderItem(((FlyingItemEntity) entity).getStack(), ModelTransformationMode.GROUND, LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), entity.getId());
        matrices.pop();
    }

    @Override
    public Identifier getTexture(Entity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}

