package net.ninjadev.bouncyballs.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.ninjadev.bouncyballs.BouncyBalls;
import net.ninjadev.bouncyballs.item.BouncyBallItem;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static final List<Item> ITEMS = new ArrayList<>();
    public static final ItemGroup BOUNCY_BALLS_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.RED_BOUNCY_BALL))
            .displayName(Text.translatable("itemGroup.bouncyballs"))
            .build();

    public static final Item RED_BOUNCY_BALL = register(BouncyBalls.id("red_bouncy_ball"), new BouncyBallItem(DyeColor.RED));
    public static final Item ORANGE_BOUNCY_BALL = register(BouncyBalls.id("orange_bouncy_ball"), new BouncyBallItem(DyeColor.ORANGE));
    public static final Item YELLOW_BOUNCY_BALL = register(BouncyBalls.id("yellow_bouncy_ball"), new BouncyBallItem(DyeColor.YELLOW));
    public static final Item LIME_BOUNCY_BALL = register(BouncyBalls.id("lime_bouncy_ball"), new BouncyBallItem(DyeColor.LIME));
    public static final Item GREEN_BOUNCY_BALL = register(BouncyBalls.id("green_bouncy_ball"), new BouncyBallItem(DyeColor.GREEN));
    public static final Item CYAN_BOUNCY_BALL = register(BouncyBalls.id("cyan_bouncy_ball"), new BouncyBallItem(DyeColor.CYAN));
    public static final Item LIGHT_BLUE_BOUNCY_BALL = register(BouncyBalls.id("light_blue_bouncy_ball"), new BouncyBallItem(DyeColor.LIGHT_BLUE));
    public static final Item BLUE_BOUNCY_BALL = register(BouncyBalls.id("blue_bouncy_ball"), new BouncyBallItem(DyeColor.BLUE));
    public static final Item PURPLE_BOUNCY_BALL = register(BouncyBalls.id("purple_bouncy_ball"), new BouncyBallItem(DyeColor.PURPLE));
    public static final Item MAGENTA_BOUNCY_BALL = register(BouncyBalls.id("magenta_bouncy_ball"), new BouncyBallItem(DyeColor.MAGENTA));
    public static final Item PINK_BOUNCY_BALL = register(BouncyBalls.id("pink_bouncy_ball"), new BouncyBallItem(DyeColor.PINK));
    public static final Item BROWN_BOUNCY_BALL = register(BouncyBalls.id("brown_bouncy_ball"), new BouncyBallItem(DyeColor.BROWN));
    public static final Item BLACK_BOUNCY_BALL = register(BouncyBalls.id("black_bouncy_ball"), new BouncyBallItem(DyeColor.BLACK));
    public static final Item GRAY_BOUNCY_BALL = register(BouncyBalls.id("gray_bouncy_ball"), new BouncyBallItem(DyeColor.GRAY));
    public static final Item LIGHT_GRAY_BOUNCY_BALL = register(BouncyBalls.id("light_gray_bouncy_ball"), new BouncyBallItem(DyeColor.LIGHT_GRAY));
    public static final Item WHITE_BOUNCY_BALL = register(BouncyBalls.id("white_bouncy_ball"), new BouncyBallItem(DyeColor.WHITE));
    public static final Item RAINBOW_BOUNCY_BALL = register(BouncyBalls.id("rainbow_bouncy_ball"), new BouncyBallItem(DyeColor.WHITE));

    public static void init() {
        Registry.register(Registries.ITEM_GROUP, BouncyBalls.id("item_group"), BOUNCY_BALLS_GROUP);
        RegistryKey<ItemGroup> registryKey = RegistryKey.of(Registries.ITEM_GROUP.getKey(), BouncyBalls.id("item_group"));
        ItemGroupEvents.modifyEntriesEvent(registryKey).register(ModItems::addItemsToGroup);
    }

    private static void addItemsToGroup(FabricItemGroupEntries itemGroup) {
        ITEMS.forEach(itemGroup::add);
    }

    private static <T extends Item> T register(Identifier id, T item) {
        T registered = Registry.register(Registries.ITEM, id, item);
        ITEMS.add(registered);
        return registered;
    }
}
