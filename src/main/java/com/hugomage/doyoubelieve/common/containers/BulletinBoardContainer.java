package com.hugomage.doyoubelieve.common.containers;

import com.hugomage.doyoubelieve.registry.DYBBlocks;
import com.hugomage.doyoubelieve.registry.DYBContainers;
import com.hugomage.doyoubelieve.common.tileentities.BulletinBoardTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.Objects;

public class BulletinBoardContainer extends Container {
    public final BulletinBoardTileEntity te;
    private final IWorldPosCallable worldPosCallable;

    public BulletinBoardContainer(int windowId, PlayerInventory playerInventory, World world, BlockPos pos) {
        super(DYBContainers.BULLETIN_BOARD.get(), windowId);
        this.te = (BulletinBoardTileEntity) world.getBlockEntity(pos);
        this.worldPosCallable = IWorldPosCallable.create(Objects.requireNonNull(te.getLevel()), te.getBlockPos());

        // Tile Entity
        this.addSlot(new SlotItemHandler(te.getInventory(), 0, 15, 17));
        this.addSlot(new SlotItemHandler(te.getInventory(), 1, 15, 54));

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

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return stillValid(worldPosCallable, playerIn, DYBBlocks.BULLETIN_BOARD.get());
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.getSlot(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();

            int startPlayerInvIndex = 2;
            int startPlayerHBIndex = 2 + 27;
            int endPlayerInvIndex = 2 + 27 + 9;

            if (index < startPlayerInvIndex) {
                if (!this.moveItemStackTo(stack, startPlayerInvIndex, endPlayerInvIndex, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, itemstack);
            } else {
                if (index >= startPlayerHBIndex) {
                    if (!this.moveItemStackTo(stack, 0, startPlayerHBIndex, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                if (index < endPlayerInvIndex) {
                    if (!this.moveItemStackTo(stack, 0, startPlayerInvIndex, false)) {
                        if (!this.moveItemStackTo(stack, startPlayerHBIndex, endPlayerInvIndex, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemstack;
    }
}
