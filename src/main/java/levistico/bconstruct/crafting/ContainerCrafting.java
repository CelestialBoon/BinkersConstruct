package levistico.bconstruct.crafting;

import levistico.bconstruct.mixin.MixinInventoryCrafting;
import net.minecraft.src.*;

public abstract class ContainerCrafting extends Container {
    public CraftingTileEntity tileEntity;
    public IInventory craftResult = new InventoryCraftResult();
    public Integer maxSlot = 10;

    public ContainerCrafting(InventoryPlayer inventoryplayer, CraftingTileEntity tileEntity) {
        tileEntity.eventHandler = this;
        ((MixinInventoryCrafting)tileEntity.inventoryCrafting).setEventHandler(this);
        this.tileEntity = tileEntity;
//        this.addSlot(new SlotCrafting(inventoryplayer.player, tileEntity.inventoryCrafting, this.craftResult, 0, 124, 35)); //craft result
//
        int j1;
        int l1;
//        for(j1 = 0; j1 < 3; ++j1) { //crafting slots
//            for(l1 = 0; l1 < 3; ++l1) {
//                this.addSlot(new Slot(this.tileEntity.inventoryCrafting, l1 + j1 * 3, 30 + l1 * 18, 17 + j1 * 18));
//            }
//        }

        for(j1 = 0; j1 < 3; ++j1) { //inventory slots
            for(l1 = 0; l1 < 9; ++l1) {
                this.addSlot(new Slot(inventoryplayer, l1 + j1 * 9 + 9, 8 + l1 * 18, 84 + j1 * 18));
            }
        }

        for(j1 = 0; j1 < 9; ++j1) { //hotbar
            this.addSlot(new Slot(inventoryplayer, j1, 8 + j1 * 18, 142));
        }

        this.onCraftMatrixChanged(this.tileEntity.inventoryCrafting);
    }

    public abstract void onCraftMatrixChanged(IInventory iinventory);

    public void onCraftGuiClosed(EntityPlayer entityplayer) {
        super.onCraftGuiClosed(entityplayer);
//        if (!this.worldObj.isMultiplayerAndNotHost) {
//            for(int i = 0; i < 9; ++i) {
//                ItemStack itemstack = this.craftMatrix.getStackInSlot(i);
//                if (itemstack != null) {
//                    entityplayer.dropPlayerItem(itemstack);
//                }
//            }
//        }
    }

    public void quickMoveItems(int slotID, EntityPlayer player, boolean shift, boolean control) {
        Slot slot = (Slot)this.inventorySlots.get(slotID);
        if (slot != null && slot.hasStack()) {
            ItemStack item = slot.getStack();
            ItemStack originalItem = item.copy();
            if (slotID == 0) { //it's the result slot?
                int craftCount = 65536;
                if (control && !shift) {
                    craftCount = 1;
                }

                if (shift && !control) {
                    craftCount = item.getMaxStackSize() / item.stackSize;
                }

                for(int j = 0; j < craftCount; ++j) {
                    ItemStack craftItem = slot.getStack();
                    if (craftItem == null || craftItem.itemID != originalItem.itemID || craftItem.getMetadata() != originalItem.getMetadata()) {
                        break;
                    }

                    boolean stop = false;
                    boolean itemsCrafted = false;
                    itemsCrafted = this.onStackMergeShiftClick(craftItem, 10, 46, true);
                    if (itemsCrafted) {
                        if (craftItem.stackSize > 0) {
                            player.dropPlayerItem(craftItem.copy());
                            stop = true;
                        }
                    } else {
                        stop = true;
                    }

                    if (craftItem.stackSize == 0) {
                        slot.putStack((ItemStack)null);
                    } else {
                        slot.onSlotChanged();
                    }

                    if (itemsCrafted) {
                        slot.onPickupFromSlot(originalItem);
                    }

                    if (stop) {
                        break;
                    }
                }

            } else {
                if (slotID >= 10 && slotID < 37) {
                    this.onStackMergeShiftClick(item, 1, maxSlot, false);
                } else if (slotID >= 37 && slotID < 46) {
                    this.onStackMergeShiftClick(item, 1, maxSlot, false);
                } else {
                    this.onStackMergeShiftClick(item, 10, 46, false);
                }

                if (item.stackSize == 0) {
                    slot.putStack((ItemStack)null);
                } else {
                    slot.onSlotChanged();
                }

                if (item.stackSize != originalItem.stackSize) {
                    slot.onPickupFromSlot(originalItem);
                }
            }
        }
    }

    public boolean isUsableByPlayer(EntityPlayer entityplayer) {
        return tileEntity.inventoryCrafting.canInteractWith(entityplayer);
    }
}
