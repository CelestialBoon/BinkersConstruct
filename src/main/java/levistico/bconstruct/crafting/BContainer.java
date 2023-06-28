package levistico.bconstruct.crafting;

import levistico.bconstruct.gui.BSlotCustomizable;
import levistico.bconstruct.gui.BSlotCraftingResult;
import levistico.bconstruct.mixin.AccessorInventoryCrafting;
import levistico.bconstruct.utils.Utils;
import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.List;

public abstract class BContainer extends Container implements IOnCraftResult {
    public CraftingTileEntity tileEntity;
    public IInventory craftResult = new InventoryCraftResult();
    Integer maxSlot = 9;
    public BSlotCraftingResult resultSlot;
    public ArrayList<BSlotCustomizable> craftingSlots = new ArrayList<>();
    public ArrayList<Slot> lowerSlots = new ArrayList<>();
    public InventoryPlayer inventoryPlayer;

    public BContainer(InventoryPlayer inventoryplayer, CraftingTileEntity tileEntity) {
        tileEntity.eventHandler = this;
        ((AccessorInventoryCrafting)tileEntity.inventoryCrafting).setEventHandler(this);
        this.tileEntity = tileEntity;
        this.inventoryPlayer = inventoryplayer;

        int j1;
        int l1;

        resultSlot = new BSlotCraftingResult(this, 0, inventoryplayer.player, this.craftResult, 124, 35);
        this.addSlot(resultSlot);

        for(j1 = 0; j1 < 3; ++j1) { //crafting slots
            for(l1 = 0; l1 < 3; ++l1) {
                BSlotCustomizable craftSlot = new BSlotCustomizable(this.tileEntity.inventoryCrafting, l1 + j1 * 3, true, 30 + l1 * 18, 17 + j1 * 18);
                craftingSlots.add(craftSlot);
                this.addSlot(craftSlot);
            }
        }

        for(j1 = 0; j1 < 3; ++j1) { //inventory slots
            for(l1 = 0; l1 < 9; ++l1) {
                int id = l1 + j1 * 9 + 9;
                Slot slot = new Slot(inventoryplayer, id, 8 + l1 * 18, 1 + j1 * 18);
                Utils.setAt(lowerSlots, id, slot);
                this.addSlot(slot);
            }
        }

        for(j1 = 0; j1 < 9; ++j1) { //hotbar
            Slot slot = new Slot(inventoryplayer, j1, 8 + j1 * 18, 59);
            Utils.setAt(lowerSlots, j1, slot);
            this.addSlot(slot);
        }

        this.onCraftMatrixChanged(this.tileEntity.inventoryCrafting);
    }

    void resizeCraftingSlots(Integer newmax) {
        if(newmax > maxSlot) {
            //open slots
            for(Integer j : Utils.range(maxSlot, newmax)) {
                craftingSlots.get(j).isActive = true;
            }
        } else if (maxSlot > newmax) {
            //return items and close slots
            for(int j = maxSlot; j > newmax; j--) {
                quickMoveItems(j, inventoryPlayer.player, true, true);
                craftingSlots.get(j-1).isActive = false;
            }
        }
        maxSlot = newmax;
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
                if (slotID >= 10 && slotID < 46) { // base inventory+hotbar
                    this.onStackMergeShiftClick(item, 1, maxSlot+1, false);
                } else { //from crafting slots
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

    public void onCraftResult(ItemStack stack, EntityPlayer player) { //default crafting behaviour, to be occasionally overridden
        Utils.decreaseAllInventoryBy(tileEntity.inventoryCrafting, 1);
    }

    @Override
    protected boolean onStackMergeShiftClick(ItemStack itemStack, int minSlot, int maxSlot, boolean sendToLastSlot) {
        if (itemStack == null)
            return false;

        List<Integer> emptySlots = new ArrayList<>();
        List<Integer> sameStackSlots = new ArrayList<>();

        for (Integer i : Utils.range(minSlot, maxSlot)) {
            Slot slot = this.getSlot(i);
            ItemStack stack = slot.getStack();
            if(stack == null && slot.canPutStackInSlot(itemStack)) {
                emptySlots.add(i);
            } else if (stack != null && itemStack.canStackWith(stack) && stack.stackSize < stack.getMaxStackSize()) {
                sameStackSlots.add(i);
            }
        }

        if (sameStackSlots.isEmpty() && emptySlots.isEmpty())
            return false;

        ItemStack iStack;
        Slot iSlot;
        if(sendToLastSlot) {
            sameStackSlots = Utils.reverseList(sameStackSlots);
            emptySlots = Utils.reverseList(emptySlots);
        }
        for(Integer i : sameStackSlots) {
            iSlot = this.getSlot(i);
            iStack = iSlot.getStack();
            int a = Utils.clamp(0, iStack.getMaxStackSize() - iStack.stackSize, itemStack.stackSize);
            if (a > 0) {
                itemStack.stackSize -= a;
                iStack.stackSize += a;
                iSlot.onSlotChanged();
                if (itemStack.stackSize == 0) {
                    break;
                }
            }
        }

        if (itemStack.stackSize == 0)
            return true;

        for(Integer i : emptySlots) {
            iSlot = this.getSlot(i);
            iSlot.putStack(itemStack.copy());
            itemStack.stackSize = 0;
            iSlot.onSlotChanged();
            break;
        }
        return true;
    }
}