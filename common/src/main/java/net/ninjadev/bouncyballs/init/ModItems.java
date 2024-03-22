package net.ninjadev.bouncyballs.init;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.ninjadev.bouncyballs.BouncyBalls;
import net.ninjadev.bouncyballs.item.BouncyBallItem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModItems {

    public static final DeferredRegister<ItemGroup> TAB_REGISTRY = DeferredRegister.create(BouncyBalls.MOD_ID, RegistryKeys.ITEM_GROUP);
    public static RegistrySupplier<ItemGroup> BOUNCY_BALLS_GROUP = TAB_REGISTRY.register(BouncyBalls.id("bouncy_balls"),
            () -> CreativeTabRegistry.create(Text.translatable("itemGroup.bouncyballs"), () -> new ItemStack(ModItems.RED_BOUNCY_BALL.get())));

    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(BouncyBalls.MOD_ID, RegistryKeys.ITEM);
    public static final List<RegistrySupplier<? extends Item>> ITEMS = new ArrayList<>();

    public static final RegistrySupplier<Item> RED_BOUNCY_BALL = register(BouncyBalls.id("red_bouncy_ball"), () -> new BouncyBallItem(DyeColor.RED));
    public static final RegistrySupplier<Item> ORANGE_BOUNCY_BALL = register(BouncyBalls.id("orange_bouncy_ball"), () -> new BouncyBallItem(DyeColor.ORANGE));
    public static final RegistrySupplier<Item> YELLOW_BOUNCY_BALL = register(BouncyBalls.id("yellow_bouncy_ball"), () -> new BouncyBallItem(DyeColor.YELLOW));
    public static final RegistrySupplier<Item> LIME_BOUNCY_BALL = register(BouncyBalls.id("lime_bouncy_ball"), () -> new BouncyBallItem(DyeColor.LIME));
    public static final RegistrySupplier<Item> GREEN_BOUNCY_BALL = register(BouncyBalls.id("green_bouncy_ball"), () -> new BouncyBallItem(DyeColor.GREEN));
    public static final RegistrySupplier<Item> CYAN_BOUNCY_BALL = register(BouncyBalls.id("cyan_bouncy_ball"), () -> new BouncyBallItem(DyeColor.CYAN));
    public static final RegistrySupplier<Item> LIGHT_BLUE_BOUNCY_BALL = register(BouncyBalls.id("light_blue_bouncy_ball"), () -> new BouncyBallItem(DyeColor.LIGHT_BLUE));
    public static final RegistrySupplier<Item> BLUE_BOUNCY_BALL = register(BouncyBalls.id("blue_bouncy_ball"), () -> new BouncyBallItem(DyeColor.BLUE));
    public static final RegistrySupplier<Item> PURPLE_BOUNCY_BALL = register(BouncyBalls.id("purple_bouncy_ball"), () -> new BouncyBallItem(DyeColor.PURPLE));
    public static final RegistrySupplier<Item> MAGENTA_BOUNCY_BALL = register(BouncyBalls.id("magenta_bouncy_ball"), () -> new BouncyBallItem(DyeColor.MAGENTA));
    public static final RegistrySupplier<Item> PINK_BOUNCY_BALL = register(BouncyBalls.id("pink_bouncy_ball"), () -> new BouncyBallItem(DyeColor.PINK));
    public static final RegistrySupplier<Item> BROWN_BOUNCY_BALL = register(BouncyBalls.id("brown_bouncy_ball"), () -> new BouncyBallItem(DyeColor.BROWN));
    public static final RegistrySupplier<Item> BLACK_BOUNCY_BALL = register(BouncyBalls.id("black_bouncy_ball"), () -> new BouncyBallItem(DyeColor.BLACK));
    public static final RegistrySupplier<Item> GRAY_BOUNCY_BALL = register(BouncyBalls.id("gray_bouncy_ball"), () -> new BouncyBallItem(DyeColor.GRAY));
    public static final RegistrySupplier<Item> LIGHT_GRAY_BOUNCY_BALL = register(BouncyBalls.id("light_gray_bouncy_ball"), () -> new BouncyBallItem(DyeColor.LIGHT_GRAY));
    public static final RegistrySupplier<Item> WHITE_BOUNCY_BALL = register(BouncyBalls.id("white_bouncy_ball"), () -> new BouncyBallItem(DyeColor.WHITE));
    public static final RegistrySupplier<Item> RAINBOW_BOUNCY_BALL = register(BouncyBalls.id("rainbow_bouncy_ball"), () -> new BouncyBallItem(DyeColor.WHITE));

    public static void init() {
        TAB_REGISTRY.register();
        REGISTRY.register();
    }

    private static <T extends Item> RegistrySupplier<T> register(Identifier id, Supplier<T> itemSupplier) {
        RegistrySupplier<T> registered = REGISTRY.register(id, itemSupplier);
        ITEMS.add(registered);
        return registered;
    }
}
