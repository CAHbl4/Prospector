package com.beerhunter.prospector.blocks;

import com.beerhunter.prospector.Prospector;
import com.beerhunter.prospector.Reference;
import com.beerhunter.prospector.entities.MinerEntity;
import com.beerhunter.prospector.network.GuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Miner extends BlockContainer {

    public Miner(String name) {
        super(Material.iron);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setBlockName(name);
        this.setBlockTextureName(Reference.MODID + ":" + name);
        this.setHardness(2.0f);
        this.setResistance(6.0f);
        this.setHarvestLevel("pickaxe", 2);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new MinerEntity();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        MinerEntity entity = (MinerEntity) world.getTileEntity(x, y, z);
        for (int i = 0; i < entity.getSizeInventory(); i++) {
            if (entity.getStackInSlot(i) != null)
                dropBlockAsItem(world, x, y, z, entity.getStackInSlot(i));
        }
        super.breakBlock(world, x, y, z, block, meta);
    }


    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
        if (stack.hasDisplayName())
            ((MinerEntity) world.getTileEntity(x, y, z)).setCustomName(stack.getDisplayName());
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ) {
        if (!world.isRemote)
            player.openGui(Prospector.instance, GuiHandler.MINER_ENTITY_GUI, world, x, y, z);
        return true;
    }
}
