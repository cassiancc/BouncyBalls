package net.ninjadev.bouncyballs.init;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.ninjadev.bouncyballs.BouncyBalls;
import net.ninjadev.bouncyballs.entity.BouncyBallEntity;

import java.util.ArrayList;
import java.util.List;

public class ModEntities {

    public static final Registrar<EntityType<?>> REGISTRY = BouncyBalls.REGISTRY_MANAGER.get().get(RegistryKeys.ENTITY_TYPE);

    public static final List<RegistrySupplier<? extends EntityType<?>>> ENTITIES = new ArrayList<>();

    public static final RegistrySupplier<EntityType<BouncyBallEntity>> BOUNCY_BALL = register(BouncyBalls.id("bouncy_ball"), BouncyBallEntity::new);

    public static void init() {}

    private static <E extends Entity> RegistrySupplier<EntityType<E>> register(Identifier id, EntityType.EntityFactory<E> entity) {
        RegistrySupplier<EntityType<E>> registered = REGISTRY.register(id, () -> EntityType.Builder.create(entity, SpawnGroup.MISC).setDimensions(0.3f, 0.3f).build(id.toString()));
        ENTITIES.add(registered);
        return registered;
    }

}
