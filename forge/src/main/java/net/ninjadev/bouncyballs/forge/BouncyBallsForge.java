package net.ninjadev.bouncyballs.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.ninjadev.bouncyballs.BouncyBalls;
import net.ninjadev.bouncyballs.client.BouncyBallsClient;

@Mod(BouncyBalls.MOD_ID)
public class BouncyBallsForge {
    public BouncyBallsForge() {
        EventBuses.registerModEventBus(BouncyBalls.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        BouncyBalls.init();

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> Client::onClientSetup);
    }

    public static class Client {
        public static void onClientSetup() {
            BouncyBallsClient.init();
        }
    }
}