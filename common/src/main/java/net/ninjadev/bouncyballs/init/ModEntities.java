package net.ninjadev.bouncyballs.init;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.ninjadev.bouncyballs.BouncyBalls;
import net.ninjadev.bouncyballs.entity.BouncyBallEntity;

import java.util.ArrayList;
import java.util.List;

public class ModEntities {

    public static final List<EntityType<?>> ENTITIES = new ArrayList<>();

    public static final EntityType<BouncyBallEntity> BOUNCY_BALL = register(BouncyBalls.id("bouncy_ball"), BouncyBallEntity::new);

    public static void init() {

    }

    private static <E extends Entity> EntityType<E> register(Identifier id, EntityType.EntityFactory<E> entity) {
        EntityType<E> registered = Registry.register(Registries.ENTITY_TYPE, id, FabricEntityTypeBuilder.create(SpawnGroup.MISC, entity).dimensions(EntityDimensions.fixed(0.3f, 0.3f)).build());
        ENTITIES.add(registered);
        return registered;
    }


}
