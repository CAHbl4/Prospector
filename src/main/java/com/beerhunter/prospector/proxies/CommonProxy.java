package com.beerhunter.prospector.proxies;

import com.beerhunter.prospector.init.ProspectorBlocks;
import com.beerhunter.prospector.init.ProspectorEntities;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        ProspectorBlocks.registerBlocks();
        ProspectorEntities.registerEntities();
    }

    public void init(FMLInitializationEvent e) {

    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}