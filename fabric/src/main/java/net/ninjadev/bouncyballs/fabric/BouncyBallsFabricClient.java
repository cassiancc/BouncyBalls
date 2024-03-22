package net.ninjadev.bouncyballs.fabric;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.PacketByteBuf;
import net.ninjadev.bouncyballs.BouncyBalls;
import net.ninjadev.bouncyballs.client.BouncyBallsClient;

import java.util.concurrent.CompletableFuture;

public class BouncyBallsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        ClientLoginNetworking.registerGlobalReceiver(BouncyBalls.id("version_check"), (client, handler, buf, listenerAdder) -> {
            PacketByteBuf out = new PacketByteBuf(Unpooled.buffer());
            StringBuilder sb = new StringBuilder();
            FabricLoader.getInstance().getModContainer(BouncyBalls.MOD_ID).ifPresent(modContainer -> {
                sb.append(modContainer.getMetadata().getVersion());
                sb.append(":");
                sb.append(parseClientVersion(client.getGameVersion()));
            });

            String clientVersion = sb.toString();
            out.writeString(clientVersion);
            return CompletableFuture.completedFuture(out);
        });

        BouncyBallsClient.init();
    }

    private static String parseClientVersion(String gameVersion) {
        return gameVersion.split("-")[3];
    }
}
