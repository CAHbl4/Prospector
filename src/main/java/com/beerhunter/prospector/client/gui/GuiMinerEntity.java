package com.beerhunter.prospector.client.gui;

import com.beerhunter.prospector.entities.MinerEntity;
import com.beerhunter.prospector.guicontainer.ContainerMinerEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiMinerEntity extends GuiContainer {

    private IInventory playerInv;
    private MinerEntity entity;

    public GuiMinerEntity(IInventory playerInv, MinerEntity entity) {
        super(new ContainerMinerEntity(playerInv, entity));
        this.playerInv = playerInv;
        this.entity = entity;

        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("prospector:textures/gui/container/miner_gui.png"));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = this.entity.getInventoryName();
        this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);     //#404040
        this.fontRendererObj.drawString(playerInv.getInventoryName(), 8, 72, 4210752);                  //#404040
    }

}
