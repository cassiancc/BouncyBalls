package net.ninjadev.bouncyballs.fabric;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerLoginConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerLoginNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.ninjadev.bouncyballs.BouncyBalls;
import org.jetbrains.annotations.NotNull;

public class BouncyBallsFabric implements ModInitializer {
    @Override
    public void onInitialize() {

        ServerLoginConnectionEvents.QUERY_START.register((handler, server, sender, synchronizer) -> {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeString(getServerVersion(server));
            sender.sendPacket(BouncyBalls.id("version_check"), buf);
        });

        ServerLoginNetworking.registerGlobalReceiver(BouncyBalls.id("version_check"), (server, handler, understood, buf, synchronizer, responseSender) -> {
            if (!understood) {
                handler.disconnect(Text.literal("You are missing the BouncyBalls mod!"));
                return;
            }

            synchronizer.waitFor(server.submit(() -> {
                String clientVersion = buf.readString();
                String serverVersion = getServerVersion(server);
                if (!serverVersion.equalsIgnoreCase(clientVersion)) {
                    handler.disconnect(Text.literal("BouncyBalls version mismatch: Server=%s, Client=%s".formatted(serverVersion, clientVersion)));
                }
            }));
        });

        BouncyBalls.init();
    }

    @NotNull
    private static String getServerVersion(MinecraftServer server) {
        StringBuilder sb = new StringBuilder();
        FabricLoader.getInstance().getModContainer(BouncyBalls.MOD_ID).ifPresent(modContainer -> {
            sb.append(modContainer.getMetadata().getVersion());
            sb.append(":");
            sb.append(server.getVersion());
        });
        return sb.toString();
    }
}