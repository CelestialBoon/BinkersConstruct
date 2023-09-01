package levistico.bconstruct.crafting;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;

public final class ContainerTileEntity extends TileEntity implements IInventory {
    public final int size;
    public final String name;
    public ItemStack[] contents;

    public ContainerTileEntity(int size, String name) {
        this.size = size;
        this.name = name;
        contents = new ItemStack[size];
    }

    public int getSizeInventory() {
        return size;
    }

    public ItemStack getStackInSlot(int i) {
        return i >= this.getSizeInventory() ? null : this.contents[i];
    }

    public ItemStack decrStackSize(int i, int j) {
        if (this.contents[i] != null) {
            ItemStack itemstack1;
            if (this.contents[i].stackSize <= j) {
                itemstack1 = this.contents[i];
                this.contents[i] = null;
                this.onInventoryChanged();
                return itemstack1;
            } else {
                itemstack1 = this.contents[i].splitStack(j);
                if (this.contents[i].stackSize == 0) {
                    this.contents[i] = null;
                }

                this.onInventoryChanged();
                return itemstack1;
            }
        } else {
            return null;
        }
    }

    public void setInventorySlotContents(int i, ItemStack itemstack) {
        this.contents[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
            itemstack.stackSize = this.getInventoryStackLimit();
        }

        this.onInventoryChanged();
    }

    public String getInvName() {
        return name;
    }

    public void readFromNBT(CompoundTag nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        ListTag nbttaglist = nbttagcompound.getList("Items");
        this.contents = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            CompoundTag nbttagcompound1 = (CompoundTag) nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;
            if (j >= 0 && j < this.contents.length) {
                this.contents[j] = ItemStack.readItemStackFromNbt(nbttagcompound1);
            }
        }

    }

    public void writeToNBT(CompoundTag nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        ListTag nbttaglist = new ListTag();

        for (int i = 0; i < this.contents.length; ++i) {
            if (this.contents[i] != null) {
                CompoundTag nbttagcompound1 = new CompoundTag();
                nbttagcompound1.putByte("Slot", (byte) i);
                this.contents[i].writeToNBT(nbttagcompound1);
                nbttaglist.addTag(nbttagcompound1);
            }
        }

        nbttagcompound.put("Items", nbttaglist);
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean canInteractWith(EntityPlayer entityPlayer) {
        if (this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this) {
            return false;
        } else {
            return entityPlayer.distanceToSqr((double) this.xCoord + 0.5, (double) this.yCoord + 0.5, (double) this.zCoord + 0.5) <= 64.0;
        }
    }
}