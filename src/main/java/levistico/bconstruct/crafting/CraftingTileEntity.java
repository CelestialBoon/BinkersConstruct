package levistico.bconstruct.crafting;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.InventoryCrafting;

public final class CraftingTileEntity extends TileEntity {

    public InventoryCrafting inventoryCrafting = new InventoryCrafting(new DummyContainer(), 3, 3);
    public Container eventHandler;

    public CraftingTileEntity() {
    }

    @Override
    public void readFromNBT(CompoundTag nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        ListTag nbttaglist = nbttagcompound.getList("Items");

        for(int i = 0; i < nbttaglist.tagCount(); ++i) {
            CompoundTag nbttagcompound1 = (CompoundTag)nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;
            if (j >= 0 && j < inventoryCrafting.getSizeInventory()) {
                inventoryCrafting.setInventorySlotContents(j, ItemStack.readItemStackFromNbt(nbttagcompound1));
            }
        }
    }

    @Override
    public void writeToNBT(CompoundTag nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        ListTag nbttaglist = new ListTag();

        for(int i = 0; i < inventoryCrafting.getSizeInventory(); ++i) {
            if (inventoryCrafting.getStackInSlot(i) != null) {
                CompoundTag nbttagcompound1 = new CompoundTag();
                nbttagcompound1.putByte("Slot", (byte)i);
                inventoryCrafting.getStackInSlot(i).writeToNBT(nbttagcompound1);
                nbttaglist.addTag(nbttagcompound1);
            }
        }

        nbttagcompound.put("Items", nbttaglist);
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
