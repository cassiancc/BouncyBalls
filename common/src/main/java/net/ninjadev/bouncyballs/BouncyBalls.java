package net.ninjadev.bouncyballs;

import com.google.common.base.Suppliers;
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.util.Identifier;
import net.ninjadev.bouncyballs.init.ModEntities;
import net.ninjadev.bouncyballs.init.ModItems;

import java.util.function.Supplier;

public class BouncyBalls
{

	public static final String MC_VERSION = "1.20.4";
	public static final String MOD_VERSION = "1.0.1";
	public static final String MOD_ID = "bouncyballs";
	public static final Supplier<RegistrarManager> REGISTRY_MANAGER = Suppliers.memoize(() -> RegistrarManager.get(BouncyBalls.MOD_ID));


	public static void init() {
		ModItems.init();
		ModEntities.init();
	}

	public static Identifier id(String id) {
		return new Identifier(MOD_ID, id);
	}
}
