package com.hugomage.doyoubelieve.common.network;

import com.hugomage.doyoubelieve.client.screens.BulletinBoardGui;
import com.hugomage.doyoubelieve.common.tileentities.BulletinBoardTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PacketSetClueAreas {
    private final BlockPos pos;
    private final List<BulletinBoardGui.ClueArea> clueAreas;

    public PacketSetClueAreas(BlockPos pos, List<BulletinBoardGui.ClueArea> clueAreas) {
        this.pos = pos;
        this.clueAreas = clueAreas;
    }

    public PacketSetClueAreas(PacketBuffer buf) {
        pos = buf.readBlockPos();

        List<BulletinBoardGui.ClueArea> ca = new ArrayList<>();

        for (INBT nbt : buf.readNbt().getList("clueAreas", Constants.NBT.TAG_COMPOUND)) {
            ca.add(BulletinBoardGui.ClueArea.load((CompoundNBT) nbt));
        }

        clueAreas = ca;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos);

        CompoundNBT nbt = new CompoundNBT();
        ListNBT listNBT = new ListNBT();

        for (BulletinBoardGui.ClueArea ca : clueAreas) {
            listNBT.add(ca.save());
        }
        nbt.put("clueAreas", listNBT);

        buf.writeNbt(nbt);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            World world = ctx.get().getSender().level;
            TileEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof BulletinBoardTileEntity) {
                BulletinBoardTileEntity te = (BulletinBoardTileEntity) tileEntity;
                te.setClueAreas(clueAreas);
            } else {
                throw new IllegalStateException("This cannot happen! Tried to add clue area information to a non BulletinBoard tile entity");
            }
        });
        return true;
    }
}
