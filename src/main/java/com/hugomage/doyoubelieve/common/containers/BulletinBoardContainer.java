package com.hugomage.doyoubelieve.common.containers;

import com.hugomage.doyoubelieve.registry.DYBBlocks;
import com.hugomage.doyoubelieve.registry.DYBContainers;
import com.hugomage.doyoubelieve.common.tileentities.BulletinBoardTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

import java.util.Objects;

public class BulletinBoardContainer extends Container {
    public final BulletinBoardTileEntity te;
    private final IWorldPosCallable worldPosCallable;

    public BulletinBoardContainer(final int windowId, final PlayerInventory playerInventory, BulletinBoardTileEntity te) {
        super(DYBContainers.BULLETIN_BOARD.get(), windowId);
        this.te = te;
        this.worldPosCallable = IWorldPosCallable.create(Objects.requireNonNull(te.getLevel()), te.getBlockPos());

        // Tile Entity
        this.addSlot(new Slot(te, 0, 14, 17));
        this.addSlot(new Slot(te, 1, 32, 17));

        // Main Player Inv
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 166 - (4 - row) * 18 - 10));
            }
        }

        // Player Hotbar
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }

    }

    public BulletinBoardContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data));
    }

    private static BulletinBoardTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "Player Inventory cannot be null");
        Objects.requireNonNull(data, "Packet Buffer cannot be null");
        final TileEntity te = playerInventory.player.level.getBlockEntity(data.readBlockPos());

        if (te instanceof BulletinBoardTileEntity) {
            return (BulletinBoardTileEntity) te;
        }

        throw new IllegalStateException("Tile Entity is not correct");
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return stillValid(worldPosCallable, playerIn, DYBBlocks.BULLETIN_BOARD.get());
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack1 = slot.getItem();
            stack = stack1.copy();

            if (index < BulletinBoardTileEntity.slots) {
                if (!this.moveItemStackTo(stack1, 0, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.moveItemStackTo(stack1, 0, 10, true)) {
                return ItemStack.EMPTY;
            }

            if (stack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            }
            else {
                slot.setChanged();
            }
        }

        return stack;
    }
}
