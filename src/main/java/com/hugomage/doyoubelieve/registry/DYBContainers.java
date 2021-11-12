package com.hugomage.doyoubelieve.registry;

import com.hugomage.doyoubelieve.DoYouBelieve;
import com.hugomage.doyoubelieve.containers.BulletinBoardContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DYBContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, DoYouBelieve.MOD_ID);

    public static final RegistryObject<ContainerType<BulletinBoardContainer>> BULLETIN_BOARD = CONTAINERS.register("blender", () -> IForgeContainerType.create(BulletinBoardContainer::new));
}
