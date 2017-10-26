package com.beerhunter.prospector.guicontainer;


import com.beerhunter.prospector.entities.MinerEntity;
import com.beerhunter.prospector.utils.OutputSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMinerEntity extends Container {

    private final MinerEntity entity;

    /*
    * SLOTS:
    * Entity Pickaxe Slot 0 ...0
    * Entity Input 1-9 ....... 1  - 9
    * Entity Output 10-18 .... 10 - 18
    * Player Inventory 9-35 .. 19 - 45
    * Player Inventory 0-8 ... 46 - 54
    */
    public ContainerMinerEntity(IInventory playerInv, MinerEntity entity) {
        this.entity = entity;

        // Entity Pickaxe, Slot 0, Slot ID 0
        this.addSlotToContainer(new Slot(entity, 0, 80, 35));

        // Entity Input, Slot 1-9, Slot IDs 1-9
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                this.addSlotToContainer(new Slot(entity, x + y * 3 + 1, 8 + x * 18, 17 + y * 18));
            }
        }

        // Entity Output, Slot 10-18, Slot IDs 10-18
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                this.addSlotToContainer(new OutputSlot(entity, x + y * 3 + 10, 116 + x * 18, 17 + y * 18));
            }
        }

        // Player Inventory, Slot 9-35, Slot IDs 9-35
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        // Player Inventory, Slot 0-8, Slot IDs 36-44
        for (int x = 0; x < 9; ++x) {
            this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
        }

    }

    @Override
    public void putStackInSlot(int index, ItemStack stack) {
        if (index > 9 && index < 19)
            return;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int fromSlot) {
        ItemStack previous = null;
        Slot slot = (Slot) this.inventorySlots.get(fromSlot);

        if (slot != null && slot.getHasStack()) {
            ItemStack current = slot.getStack();
            previous = current.copy();

            if (fromSlot < 19) {
                // From Miner Inventory to Player Inventory
                if (!this.mergeItemStack(current, 19, 55, true))
                    return null;
            } else {
                // From Player Inventory to Miner Inventory
                if (!this.mergeItemStack(current, 0, 19, false))
                    return null;
            }

            if (current.stackSize == 0)
                slot.putStack(null);
            else
                slot.onSlotChanged();

            if (current.stackSize == previous.stackSize)
                return null;
            slot.onPickupFromSlot(player, current);
        }

        return previous;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.entity.isUseableByPlayer(player);
    }

}
