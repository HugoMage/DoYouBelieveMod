package com.hugomage.doyoubelieve.common.network;

import com.hugomage.doyoubelieve.common.tileentities.BulletinBoardTileEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketConsume {
    private final BlockPos pos;
    private final ConsumeType type;
    private final int amount;
    private final int[] indexes;

    public PacketConsume(BlockPos pos, ConsumeType type, int amount, int... indexes) {
        this.pos = pos;
        this.type = type;
        this.amount = amount;
        this.indexes = indexes;
    }

    public PacketConsume(PacketBuffer buf) {
        pos = buf.readBlockPos();
        type = ConsumeType.valueOf(buf.readUtf());
        amount = buf.readInt();
        indexes = buf.readVarIntArray();
    }

    public void toBytes(PacketBuffer buf) {;
        buf.writeBlockPos(pos);
        buf.writeUtf(type.toString());
        buf.writeInt(amount);
        buf.writeVarIntArray(indexes);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            World world = ctx.get().getSender().level;
            TileEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof BulletinBoardTileEntity) {
                BulletinBoardTileEntity te = (BulletinBoardTileEntity) tileEntity;
                switch (type) {
                    case CONSUME_STRING:
                        te.getInventory().extractItem(1, amount, false);
                        break;
                    case CONSUME_CLUE:
                        for (int i : indexes) {
                            te.getClues().extractItem(i, amount, false);
                        }
                        break;
                }
            } else {
                throw new IllegalStateException("This cannot happen! Tried to add clue area information to a non BulletinBoard tile entity");
            }
        });
        return true;
    }

    public enum ConsumeType {
        CONSUME_STRING,
        CONSUME_CLUE
    }
}
