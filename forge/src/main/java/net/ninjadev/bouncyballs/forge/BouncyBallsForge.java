package net.ninjadev.bouncyballs.forge;

import dev.architectury.platform.forge.EventBuses;
import net.ninjadev.bouncyballs.BouncyBalls;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BouncyBalls.MOD_ID)
public class BouncyBallsForge {
    public BouncyBallsForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(BouncyBalls.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        BouncyBalls.init();
    }
}