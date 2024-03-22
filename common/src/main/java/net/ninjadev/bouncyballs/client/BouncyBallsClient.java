package net.ninjadev.bouncyballs.client;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.item.ItemConvertible;
import net.minecraft.nbt.NbtCompound;
import net.ninjadev.bouncyballs.client.render.entity.BouncyBallEntityRenderer;
import net.ninjadev.bouncyballs.init.ModEntities;
import net.ninjadev.bouncyballs.init.ModItems;
import net.ninjadev.bouncyballs.item.BouncyBallItem;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class BouncyBallsClient {

    public static void init() {
        EntityRendererRegistry.register(ModEntities.BOUNCY_BALL, BouncyBallEntityRenderer::new);

        for (RegistrySupplier<?> item : ModItems.ITEMS) {
            ColorHandlerRegistry.registerItemColors(getItemColorProvider(), () -> (ItemConvertible) item.get());
        }
    }

    @NotNull
    private static ItemColorProvider getItemColorProvider() {
        return (stack, tintIndex) -> {
            if (stack.getItem() instanceof BouncyBallItem ball) {
                if (ball == ModItems.RAINBOW_BOUNCY_BALL.get()) {
                    NbtCompound nbt = stack.getOrCreateNbt();
                    if (nbt.contains("Life")) {
                        int life = nbt.getInt("Life");
                        float transition = life / 120f;
                        return Color.getHSBColor(transition, 1F, 1F).getRGB();
                    } else {
                        float transition = (System.currentTimeMillis() % 10000) / 10000F;
                        return Color.getHSBColor(transition, 1F, 1F).getRGB();
                    }
                }
                float[] colorComponents = ball.getColor().getColorComponents();
                Color color = new Color(colorComponents[0], colorComponents[1], colorComponents[2]);
                return color.getRGB();
            }
            return 0;
        };
    }
}
