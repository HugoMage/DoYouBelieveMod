package com.hugomage.doyoubelieve.registry;

import com.hugomage.doyoubelieve.DoYouBelieve;
import com.hugomage.doyoubelieve.common.containers.BulletinBoardContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.swing.*;

public class DYBContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, DoYouBelieve.MOD_ID);

    public static final RegistryObject<ContainerType<BulletinBoardContainer>> BULLETIN_BOARD = CONTAINERS.register("bulletin_board", () -> IForgeContainerType.create((wid, inv, data) -> new BulletinBoardContainer(wid, inv, inv.player.getCommandSenderWorld(), data.readBlockPos())));
}
