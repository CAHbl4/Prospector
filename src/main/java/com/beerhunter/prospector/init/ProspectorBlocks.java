package com.beerhunter.prospector.init;

import com.beerhunter.prospector.blocks.Miner;
import cpw.mods.fml.common.registry.GameRegistry;

public final class ProspectorBlocks {

    public static final void registerBlocks() {
        GameRegistry.registerBlock(new Miner("Miner"), "prospectorMiner");
    }

}