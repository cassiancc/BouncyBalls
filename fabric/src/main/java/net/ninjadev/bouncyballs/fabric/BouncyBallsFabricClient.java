package net.ninjadev.bouncyballs.fabric;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginNetworking;
import net.minecraft.network.PacketByteBuf;
import net.ninjadev.bouncyballs.BouncyBalls;
import net.ninjadev.bouncyballs.client.BouncyBallsClient;

import java.util.concurrent.CompletableFuture;

public class BouncyBallsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        ClientLoginNetworking.registerGlobalReceiver(BouncyBalls.id("version_check"), (client, handler, buf, listenerAdder) -> {
            PacketByteBuf out = new PacketByteBuf(Unpooled.buffer());
            out.writeString(BouncyBalls.MOD_VERSION + ":" + BouncyBalls.MC_VERSION);
            return CompletableFuture.completedFuture(out);
        });

        BouncyBallsClient.init();
    }
}
