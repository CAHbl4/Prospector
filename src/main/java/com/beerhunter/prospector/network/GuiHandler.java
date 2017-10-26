package com.beerhunter.prospector.network;

import com.beerhunter.prospector.client.gui.GuiMinerEntity;
import com.beerhunter.prospector.entities.MinerEntity;
import com.beerhunter.prospector.guicontainer.ContainerMinerEntity;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    public static final int MINER_ENTITY_GUI = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == MINER_ENTITY_GUI)
            return new ContainerMinerEntity(player.inventory, (MinerEntity) world.getTileEntity(x, y, z));

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == MINER_ENTITY_GUI)
            return new GuiMinerEntity(player.inventory, (MinerEntity) world.getTileEntity(x, y, z));

        return null;
    }
}
