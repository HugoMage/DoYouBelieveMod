package com.hugomage.doyoubelieve.common.tileentities;

import com.hugomage.doyoubelieve.client.screens.BulletinBoardGui;
import com.hugomage.doyoubelieve.common.item.DYBClueItem;
import com.hugomage.doyoubelieve.registry.DYBItems;
import com.hugomage.doyoubelieve.registry.DYBTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BulletinBoardTileEntity extends TileEntity implements ITickableTileEntity {

    protected final ItemStackHandler inventory = new BulletinBoardItemHandler(this);
    protected final LazyOptional<IItemHandler> lazyOptional = LazyOptional.of(() -> inventory);
    protected final ItemStackHandler clues = new BulletinBoardItemHandler(this, 42);
    protected final List<BulletinBoardGui.ClueArea> clueAreas = new ArrayList<>();

    public BulletinBoardTileEntity() {
        super(DYBTileEntities.BULLETIN_BOARD.get());
    }

    @Override
    public void tick() {
        if (level == null) return;
        if (level.isClientSide()) return;

        ItemStack item = inventory.getStackInSlot(0);
        if (item.getItem() instanceof DYBClueItem) {
            for (int i = 0; i < item.getCount(); i++) {
                ItemStack s = item.copy();
                s.setCount(1);
                addClue(s);
                inventory.extractItem(0, 1, false);
            }
        }

        setChanged();
    }

    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getBlockPos(), 3, this.getUpdateTag());
    }

    public void handleUpdateTag(BlockState state, @Nonnull CompoundNBT tag) {
        this.load(state, tag);
    }

    public CompoundNBT getUpdateTag() {
        return this.save(super.getUpdateTag());
    }

    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        if (this.level != null) {
            this.handleUpdateTag(this.level.getBlockState(this.getBlockPos()), pkt.getTag());
            super.onDataPacket(net, pkt);
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("inv", inventory.serializeNBT());
        nbt.put("clues", clues.serializeNBT());

        ListNBT listNBT = new ListNBT();

        for (BulletinBoardGui.ClueArea clueArea : getClueAreas()) {
            listNBT.add(clueArea.save());
        }

        nbt.put("clueAreas", listNBT);
        compound.put("bulletin_board", nbt);
        return compound;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        CompoundNBT compoundNBT = nbt.getCompound("bulletin_board");
        inventory.deserializeNBT(compoundNBT.getCompound("inv"));
        clues.deserializeNBT(compoundNBT.getCompound("clues"));

        ListNBT listNBT = compoundNBT.getList("clueAreas", Constants.NBT.TAG_COMPOUND);
        List<BulletinBoardGui.ClueArea> clueAreas = new ArrayList<>();
        for (INBT clueAreaNBT : listNBT) {
            clueAreas.add(BulletinBoardGui.ClueArea.load((CompoundNBT) clueAreaNBT));
        }
        setClueAreas(clueAreas);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public ItemStackHandler getClues() {
        return clues;
    }

    public void updateBlock() {
        if (level == null) return;
        BlockState state = level.getBlockState(getBlockPos());
        level.sendBlockUpdated(getBlockPos(), state, state, 2);
        setChanged();
    }

    private void addClue(ItemStack itemToAdd) {
        for (int i = 0; i < 42; i++) {
            ItemStack stack = clues.getStackInSlot(i);
            if (!stack.isEmpty()) continue;
            if (stack.getCount() > 1) continue;
            clues.insertItem(i, itemToAdd, false);
            updateBlock();
            break;
        }
    }

    public void setClueAreas(List<BulletinBoardGui.ClueArea> clueAreas) {
        this.clueAreas.clear();
        this.clueAreas.addAll(clueAreas);
        setChanged();
    }

    public List<BulletinBoardGui.ClueArea> getClueAreas() {
        return this.clueAreas;
    }

    protected static class BulletinBoardItemHandler extends ItemStackHandler {
        protected final BulletinBoardTileEntity te;

        public BulletinBoardItemHandler(BulletinBoardTileEntity te) {
            super(2);
            this.te = te;
        }

        public BulletinBoardItemHandler(BulletinBoardTileEntity te, int size) {
            super(size);
            this.te = te;
        }

        @Override
        protected void onContentsChanged(int slot) {
            te.updateBlock();
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            if (stacks.size() != 2) return super.isItemValid(slot, stack);
            if (slot == 0) {
                return stack.getItem() instanceof DYBClueItem;
            }
            return stack.getItem() == DYBItems.CLUE_STRING.get();
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (stacks.size() != 2) return super.insertItem(slot, stack, simulate);
            return isItemValid(slot, stack) ? super.insertItem(slot, stack, simulate) : stack;
        }
    }
}
