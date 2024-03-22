package net.ninjadev.bouncyballs.init;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.ninjadev.bouncyballs.BouncyBalls;

public class ModTags {
    public static final TagKey<Block> DAMPENS_BOUNCE = TagKey.of(RegistryKeys.BLOCK, BouncyBalls.id("dampens_bounce"));
    public static final TagKey<Block> INCREASES_BOUNCE = TagKey.of(RegistryKeys.BLOCK, BouncyBalls.id("increases_bounce"));
}
