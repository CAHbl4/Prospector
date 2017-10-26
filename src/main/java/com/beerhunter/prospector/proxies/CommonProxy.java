package com.beerhunter.prospector.proxies;

import com.beerhunter.prospector.Prospector;
import com.beerhunter.prospector.init.ProspectorBlocks;
import com.beerhunter.prospector.init.ProspectorEntities;
import com.beerhunter.prospector.network.GuiHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        ProspectorBlocks.registerBlocks();
        ProspectorEntities.registerEntities();
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(Prospector.instance, new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}