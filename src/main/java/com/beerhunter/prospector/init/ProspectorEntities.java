package com.beerhunter.prospector.init;

import com.beerhunter.prospector.entities.MinerEntity;
import cpw.mods.fml.common.registry.GameRegistry;

public class ProspectorEntities {

    public static final void registerEntities() {
        GameRegistry.registerTileEntity(MinerEntity.class, "prospector_miner_entity");
    }

}
