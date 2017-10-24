package com.beerhunter.prospector.blocks;

import com.beerhunter.prospector.Reference;
import com.beerhunter.prospector.entities.MinerEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Miner extends BlockContainer {

    public Miner(String name) {
        super(Material.iron);
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
    public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
        MinerEntity te = (MinerEntity) world.getTileEntity(x, y, z);
        for(int i=0; i<te.getSizeInventory(); i++)
        {
            if(te.getStackInSlot(i) != null)
                dropBlockAsItem(world, x, y, z, te.getStackInSlot(i));
        }
        super.breakBlock(world, x, y, z, block, meta);
    }


    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack)
    {
        if(stack.hasDisplayName())
            ((MinerEntity) world.getTileEntity(x, y, z)).setCustomName(stack.getDisplayName());
    }
}
