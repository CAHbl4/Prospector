package com.beerhunter.prospector.entities;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.ArrayList;
import java.util.List;

public class MinerEntity extends TileEntity implements IInventory {

    private ItemStack[] inventory;
    private String customName;
    private int minePosition = -1;
    private boolean mineIsComplete = false;
    private short time = 1;

    public MinerEntity() {
        super();
        this.inventory = new ItemStack[this.getSizeInventory()];
    }

    private void dig() {
        if (time % 20 != 0) {
            time++;
            return;
        } else {
            time = 1;
        }

        if (mineIsComplete)
            return;
        if (inventory[0] == null || !(inventory[0].getItem() instanceof ItemPickaxe))
            return;

        int damage = inventory[0].getItemDamage();
        int maxDamage = inventory[0].getMaxDamage();

        if (damage + 1 > maxDamage)
            return;

        if (this.minePosition == -1)
            this.minePosition = this.yCoord - 1;

        Block block = worldObj.getBlock(xCoord, minePosition, yCoord);

        if (this.minePosition == 0 || block.getBlockHardness(worldObj, xCoord, minePosition, yCoord) < 0)
            return;

        List<ItemStack> drops = getItemStackFromBlock(this.worldObj, this.xCoord, this.minePosition, this.zCoord);
        this.worldObj.setBlockToAir(this.xCoord, this.minePosition, this.zCoord);
        this.minePosition -= 1;
        if (drops != null) {
            inventory[0].setItemDamage(damage + 1);
            for (ItemStack item : drops) {
                mine(item);
            }
        }
    }

    public List<ItemStack> getItemStackFromBlock(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);

        if (block == null) {
            return null;
        }

        if (block.isAir(world, x, y, z)) {
            return null;
        }

        int meta = world.getBlockMetadata(x, y, z);

        ArrayList<ItemStack> dropsList = block.getDrops(world, x, y, z, meta, 0);
        float dropChance = ForgeEventFactory.fireBlockHarvesting(dropsList, world, block, x, y, z, meta, 0, 1.0F, false, null);

        ArrayList<ItemStack> returnList = new ArrayList<ItemStack>();
        for (ItemStack s : dropsList) {
            if (world.rand.nextFloat() <= dropChance) {
                returnList.add(s);
            }
        }

        return returnList;
    }

    private void mine(ItemStack stack) {
        stack.stackSize -= putToOutput(stack);

        if (stack.stackSize > 0) {
            float f = worldObj.rand.nextFloat() * 0.8F + 0.1F;
            float f1 = worldObj.rand.nextFloat() * 0.8F + 0.1F;
            float f2 = worldObj.rand.nextFloat() * 0.8F + 0.1F;

            EntityItem entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1 + 0.5F, zCoord + f2, stack);

            entityitem.delayBeforeCanPickup = 10;

            float f3 = 0.05F;
            entityitem.motionX = (float) worldObj.rand.nextGaussian() * f3;
            entityitem.motionY = (float) worldObj.rand.nextGaussian() * f3 + 1.0F;
            entityitem.motionZ = (float) worldObj.rand.nextGaussian() * f3;
            worldObj.spawnEntityInWorld(entityitem);
        }
    }

    private int putToOutput(ItemStack stack) {
        for (int i = 10; i < 19; i++) {
            if (stack.stackSize == 0)
                return 0;
            if (inventory[i] == null) {
                inventory[i] = stack;
                this.markDirty();
                return stack.stackSize;
            } else if (inventory[i].isItemEqual(stack)) {
                int max = inventory[i].getMaxStackSize();
                int free = max - inventory[i].stackSize;
                if (free >= stack.stackSize) {
                    inventory[i].stackSize += stack.stackSize;
                    return stack.stackSize;
                } else {
                    inventory[i].stackSize = max;
                    stack.stackSize -= free;
                }
            }
        }
        return stack.stackSize;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    @Override
    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.customName : "miner.inventory";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return this.customName != null && !this.customName.equals("");
    }

    @Override
    public int getSizeInventory() {
        return 19;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        if (index < 0 || index >= this.getSizeInventory())
            return null;
        return this.inventory[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (this.getStackInSlot(index) != null) {
            ItemStack itemstack;

            if (this.getStackInSlot(index).stackSize <= count) {
                itemstack = this.getStackInSlot(index);
                this.setInventorySlotContents(index, null);
                this.markDirty();
                return itemstack;
            } else {
                itemstack = this.getStackInSlot(index).splitStack(count);

                if (this.getStackInSlot(index).stackSize <= 0) {
                    this.setInventorySlotContents(index, null);
                } else {
                    //Just to show that changes happened
                    this.setInventorySlotContents(index, this.getStackInSlot(index));
                }

                this.markDirty();
                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int index) {
        ItemStack stack = this.getStackInSlot(index);
        this.setInventorySlotContents(index, null);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (index < 0 || index >= this.getSizeInventory())
            return;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
            stack.stackSize = this.getInventoryStackLimit();

        if (stack != null && stack.stackSize == 0)
            stack = null;

        this.inventory[index] = stack;
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this &&
                player.getDistance(this.xCoord, this.yCoord, this.zCoord) <= 8;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public void updateEntity() {
        if (!this.worldObj.isRemote)
            dig();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        NBTTagList list = new NBTTagList();
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            if (this.getStackInSlot(i) != null) {
                NBTTagCompound stackTag = new NBTTagCompound();
                stackTag.setByte("Slot", (byte) i);
                this.getStackInSlot(i).writeToNBT(stackTag);
                list.appendTag(stackTag);
            }
        }
        nbt.setTag("Items", list);

        if (this.hasCustomInventoryName()) {
            nbt.setString("CustomName", this.getCustomName());
        }

        nbt.setInteger("minePosition", this.minePosition);
    }


    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);


        NBTTagList list = nbt.getTagList("Items", 10);
        for (int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound stackTag = list.getCompoundTagAt(i);
            int slot = stackTag.getByte("Slot") & 255;
            this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
        }

        if (nbt.hasKey("CustomName", 8)) {
            this.setCustomName(nbt.getString("CustomName"));
        }

        this.minePosition = nbt.getInteger("minePosition");
    }
}
