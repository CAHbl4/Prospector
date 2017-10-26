package com.beerhunter.prospector.init;

import com.beerhunter.prospector.blocks.Miner;
import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public final class ProspectorBlocks {

    public static final void registerBlocks() {
        GameRegistry.registerBlock(new Miner("Miner"), "prospectorMiner");
    }

}