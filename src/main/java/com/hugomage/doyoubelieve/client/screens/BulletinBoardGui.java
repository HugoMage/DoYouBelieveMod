package com.hugomage.doyoubelieve.client.screens;

import com.hugomage.doyoubelieve.DoYouBelieve;
import com.hugomage.doyoubelieve.common.containers.BulletinBoardContainer;
import com.hugomage.doyoubelieve.common.item.DYBClueItem;
import com.hugomage.doyoubelieve.common.item.DYBClues;
import com.hugomage.doyoubelieve.common.network.DYBNetworking;
import com.hugomage.doyoubelieve.common.network.PacketConsume;
import com.hugomage.doyoubelieve.common.network.PacketSetClueAreas;
import com.hugomage.doyoubelieve.common.utils.Area;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.ItemStackHandler;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BulletinBoardGui extends ContainerScreen<BulletinBoardContainer> {
    private static final ResourceLocation BG = new ResourceLocation(DoYouBelieve.MOD_ID, "textures/gui/bulletin_board.png");

    private boolean isMouseDown;
    private Vector2f point1 = null;
    private Vector2f point2 = null;
    private int oldLeftPos = 0;
    private int oldTopPos = 0;
    private int errorCoolDown = 0;

    public BulletinBoardGui(BulletinBoardContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();

        // this is for re-positioning the areas on game window resize
        if (oldLeftPos == 0) {
            oldLeftPos = leftPos;
        }

        if (oldTopPos == 0) {
            oldTopPos = topPos;
        }

        for (ClueArea ca : getClueAreasClientSide()) {
            ca.setX(ca.getX() - oldLeftPos + leftPos);
            ca.setY(ca.getY() - oldTopPos + topPos);
        }

        oldLeftPos = leftPos;
        oldTopPos = topPos;
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        if (minecraft == null) return;
        if (menu.te == null) return;

        RenderSystem.pushMatrix();
        RenderSystem.color4f(1F, 1F, 1F, 1F);
        this.minecraft.getTextureManager().bind(BG);
        this.blit(matrixStack, this.leftPos, topPos, 0, 0, this.getXSize(), this.getYSize() + 5);

        ItemStackHandler clues = menu.te.getClues();

        int xToAdd = 5;
        int yToAdd = 2;

        for (int i = 0; i < clues.getSlots(); i++) {
            ItemStack stack = clues.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() instanceof DYBClueItem) {
                DYBClues clue = ((DYBClueItem) stack.getItem()).getClueData();
                if (clue == null) continue;
                Area textureInformation = clue.getTextureInformation();

                int x = this.leftPos + 44 + xToAdd;
                int y = this.topPos + 16 + yToAdd;

                int width = textureInformation.getWidth();
                int height = textureInformation.getHeight();

                this.blit(matrixStack, x, y, textureInformation.getX(), textureInformation.getY(), width, height);

                // add this clue area to the list if it doesn't already exist
                ClueArea clueArea = new ClueArea(x, y, width, height, i + 1, i);

                if (getClueAreasClientSide().stream().noneMatch(ca -> ca.getClueAreaID() == clueArea.getClueAreaID())) {
                    getClueAreasClientSide().add(clueArea);
                    modifyClueAreasServerSide(getClueAreasClientSide());
                    menu.te.setChanged();
                }

                xToAdd += width + 3;
                yToAdd += height + 2;
            }
        }

        // set point 2
        if (isMouseDown) {
            if (point1 != null) point2 = new Vector2f(mouseX, mouseY);
        }

        // create connection
        if (!isMouseDown) {
            if (point1 != null && point2 != null) {
                int flag1 = 0;
                int flag2 = 0;
                ClueArea ca1 = null;
                ClueArea ca2 = null;

                for (int i = 0; i < getClueAreasClientSide().size(); i++) {
                    ClueArea ca = getClueAreasClientSide().get(i);

                    if (ca.isPosOver(point1)) {
                        flag1 = i + 1;
                        ca1 = ca;
                    }
                    if (ca.isPosOver(point2)) {
                        flag2 = i + 1;
                        ca2 = ca;
                    }

                    if (flag1 != 0 && flag2 != 0 && flag1 != flag2)
                        break;
                }

                if (flag1 != 0 && flag2 != 0 && flag1 != flag2 && !ca1.getLinkedAreaID().contains(ca2.getClueAreaID()) && !ca2.getLinkedAreaID().contains(ca1.getClueAreaID())) {
                    if (menu.te.getInventory().getStackInSlot(1).isEmpty()) {
                        point1 = null;
                        point2 = null;
                        errorCoolDown = 200;
                        RenderSystem.popMatrix();
                        return;
                    }

                    DYBNetworking.sendToServer(new PacketConsume(menu.te.getBlockPos(), PacketConsume.ConsumeType.CONSUME_STRING, 1));

                    getClueAreasClientSide().get(flag1 - 1).addLinkedArea(ca2.getClueAreaID());
                    getClueAreasClientSide().get(flag2 - 1).addLinkedArea(ca1.getClueAreaID());
                    modifyClueAreasServerSide(getClueAreasClientSide());
                    menu.te.setChanged();
                }
            }
            point1 = null;
            point2 = null;
        }

        // draw line when making a connection
        if (point1 != null) {
            renderLine(matrixStack, point1, new Vector2f(mouseX, mouseY));
        }

        for (ClueArea clueArea : getClueAreasClientSide()) {
            // make point 1
            if (clueArea.isPosOver(mouseX, mouseY)) {
                if (isMouseDown) {
                    if (point1 == null) {
                        point1 = new Vector2f(mouseX, mouseY);
                    }
                }
            }

            // render line between linked areas
            for (int linked : clueArea.getLinkedAreaID()) {
                if (linked == 0) continue;
                // only keep the clue area with the area id of the linked area
                getClueAreasClientSide().stream().filter(ca -> ca.getClueAreaID() == linked).findFirst().ifPresent(ca -> {
                    renderLine(matrixStack,
                            new Vector2f(clueArea.getX() + clueArea.getWidth() / 2f, clueArea.getY() + clueArea.getHeight() / 16f),
                            new Vector2f(ca.getX() + ca.getWidth() / 2f, ca.getY() + ca.getHeight() / 16f)
                    );
                });
            }
        }

        // render error message
        if (errorCoolDown > 0) {
            matrixStack.pushPose();
            minecraft.font.drawShadow(matrixStack, new TranslationTextComponent("doyoubelieve.gui.bulletinboard.error"), mouseX, mouseY, TextFormatting.RED.getColor());
            matrixStack.popPose();
            errorCoolDown--;
        }

        RenderSystem.popMatrix();
    }

    private void renderLine(MatrixStack matrixStack, Vector2f point1, Vector2f point2) {
        RenderSystem.pushMatrix();
        matrixStack.pushPose();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();
        RenderSystem.disableTexture();
        RenderSystem.lineWidth(2);
        buffer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
        addVertex(matrixStack, buffer, point1, getBlitOffset());
        addVertex(matrixStack, buffer, point2, getBlitOffset());
        tessellator.end();
        RenderSystem.enableTexture();
        matrixStack.popPose();
        RenderSystem.popMatrix();
    }

    private List<ClueArea> getClueAreasClientSide() {
        return menu.te.getClueAreas();
    }

    private void modifyClueAreasServerSide(List<ClueArea> newCA) {
        DYBNetworking.sendToServer(new PacketSetClueAreas(menu.te.getBlockPos(), newCA));
    }

//    private boolean checkLinkedArea() {
//
//    }

    @Override
    public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
        isMouseDown = true;
        return super.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_);
    }

    @Override
    public boolean mouseReleased(double p_231048_1_, double p_231048_3_, int p_321048_5_) {
        isMouseDown = false;
        return super.mouseReleased(p_231048_1_, p_231048_3_, p_321048_5_);
    }

    private static void addVertex(MatrixStack matrixStack, BufferBuilder buffer, Vector2f position, int z) {
        buffer.vertex(matrixStack.last().pose(), position.x, position.y, z)
                .color(255, 0, 0, 255)
                .endVertex();
    }

    public static class ClueArea extends Area {
        private List<Integer> linkedAreaIDs = new ArrayList<>();
        private final int clueAreaID;
        public final int clueItemIndex;

        public ClueArea(int x, int y, int width, int height, int clueAreaID, int clueItem) {
            super(x, y, width, height);
            this.clueAreaID = clueAreaID;
            this.clueItemIndex = clueItem;
        }

        @Override
        public void setX(int x) {
            super.setX(x);
        }

        @Override
        public void setY(int y) {
            super.setY(y);
        }

        public List<Integer> getLinkedAreaID() {
            return linkedAreaIDs;
        }

        public void addLinkedArea(int linkedArea) {
            this.linkedAreaIDs.add(linkedArea);
        }

        public void setLinkedAreaID(List<Integer> linkedArea) {
            this.linkedAreaIDs = linkedArea;
        }

        public int getClueAreaID() {
            return clueAreaID;
        }

        public int getClueItem() {
            return clueItemIndex;
        }

        public static ClueArea load(CompoundNBT compoundNBT) {
            ClueArea clueArea = new ClueArea(compoundNBT.getInt("x"), compoundNBT.getInt("y"), compoundNBT.getInt("width"), compoundNBT.getInt("height"), compoundNBT.getInt("clueAreaID"), compoundNBT.getInt("clueItem"));
            clueArea.setLinkedAreaID(Arrays.stream(compoundNBT.getIntArray("linkedAreaID")).boxed().collect(Collectors.toList()));
            return clueArea;
        }

        public CompoundNBT save() {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putInt("x", getX());
            nbt.putInt("y", getY());
            nbt.putInt("width", getWidth());
            nbt.putInt("height", getHeight());
            nbt.putIntArray("linkedAreaID", getLinkedAreaID());
            nbt.putInt("clueAreaID", getClueAreaID());
            nbt.putInt("clueItem", getClueItem());
            return nbt;
        }
    }
}