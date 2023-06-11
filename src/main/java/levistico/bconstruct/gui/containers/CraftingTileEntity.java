package levistico.bconstruct.gui.containers;

import levistico.bconstruct.gui.containers.DummyContainer;
import net.minecraft.src.*;

public final class CraftingTileEntity extends TileEntity {

    public InventoryCrafting inventoryCrafting = new InventoryCrafting(new DummyContainer(), 3, 3);
    public Container eventHandler;

    public CraftingTileEntity() {
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");

        for(int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;
            if (j >= 0 && j < inventoryCrafting.getSizeInventory()) {
                inventoryCrafting.setInventorySlotContents(j, new ItemStack(nbttagcompound1));
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        NBTTagList nbttaglist = new NBTTagList();

        for(int i = 0; i < inventoryCrafting.getSizeInventory(); ++i) {
            if (inventoryCrafting.getStackInSlot(i) != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                inventoryCrafting.getStackInSlot(i).writeToNBT(nbttagcompound1);
                nbttaglist.setTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Items", nbttaglist);
    }
    @Override
    public void onInventoryChanged() {
        super.onInventoryChanged();
        eventHandler.onCraftMatrixChanged(inventoryCrafting);
    }
   /* @Override
    public int getSizeInventory() {
        return inventoryCrafting.getSizeInventory();
    }

    public ItemStack getItemStackAt(int i, int j) {
        return inventoryCrafting.getItemStackAt(i,j);
    }

    public void setSlotContentsAt(int i, int j, ItemStack itemStack) {
        inventoryCrafting.setSlotContentsAt(i, j, itemStack);
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inventoryCrafting.getStackInSlot(i);
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        return inventoryCrafting.decrStackSize(i,j);
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack) {
        inventoryCrafting.setInventorySlotContents(i, itemStack);
    }

    @Override
    public String getInvName() {
        return name;
    }

    @Override
    public int getInventoryStackLimit() {
        return inventoryCrafting.getInventoryStackLimit();
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return inventoryCrafting.canInteractWith(entityPlayer);
    }
    */
}
