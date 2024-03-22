package net.ninjadev.bouncyballs.fabric;

import net.ninjadev.bouncyballs.BouncyBalls;
import net.fabricmc.api.ModInitializer;

public class BouncyBallsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BouncyBalls.init();
    }
}