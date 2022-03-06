package com.hugomage.doyoubelieve.common.network;

import com.hugomage.doyoubelieve.DoYouBelieve;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class DYBNetworking {
    private static SimpleChannel INSTANCE;
    private static int ID = 0;

    private static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(DoYouBelieve.MOD_ID, "networking"),
                () -> "1.0",
                s -> true,
                s -> true);
        INSTANCE.messageBuilder(PacketSpawn.class, nextID())
                .encoder(PacketSpawn::toBytes)
                .decoder(PacketSpawn::new)
                .consumer(PacketSpawn::handle)
                .add();
        INSTANCE.messageBuilder(PacketSetClueAreas.class, nextID())
                .encoder(PacketSetClueAreas::toBytes)
                .decoder(PacketSetClueAreas::new)
                .consumer(PacketSetClueAreas::handle)
                .add();
        INSTANCE.messageBuilder(PacketConsume.class, nextID())
                .encoder(PacketConsume::toBytes)
                .decoder(PacketConsume::new)
                .consumer(PacketConsume::handle)
                .add();
    }

    public static void sendToClient(Object packet, ServerPlayerEntity player) {
        INSTANCE.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}
