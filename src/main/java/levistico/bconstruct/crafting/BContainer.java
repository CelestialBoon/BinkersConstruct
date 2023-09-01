package levistico.bconstruct.crafting;

import levistico.bconstruct.gui.BSlotCustomizable;
import levistico.bconstruct.gui.BSlotCraftingResult;
import levistico.bconstruct.mixin.AccessorInventoryCrafting;
import levistico.bconstruct.utils.Utils;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.InventoryCraftResult;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                //quickMoveItems(j, inventoryPlayer.player, true, true); //TODO
                craftingSlots.get(j-1).isActive = false;
            }
        }
        maxSlot = newmax;
    }

    public abstract void onCraftMatrixChanged(IInventory iinventory);

    public void onCraftGuiClosed(EntityPlayer entityPlayer) {
        super.onCraftGuiClosed(entityPlayer);
    }
    public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
        return tileEntity.inventoryCrafting.canInteractWith(entityPlayer);
    }

    public void onCraftResult(ItemStack stack, EntityPlayer player) { //default crafting behaviour, to be occasionally overridden
        Utils.decreaseAllInventoryBy(tileEntity.inventoryCrafting, 1);
    }

    @Override
    public List<Integer> getMoveSlots(final InventoryAction action, final Slot slot, final int target, final EntityPlayer player) {
        if (slot.id == 0) {
            return this.getSlots(0, 1, false);
        }
        if (slot.id >= 1 && slot.id < 9) {
            return craftingSlots.stream().filter(s -> s.isActive).map(s -> s.id).collect(Collectors.toList());
//            return this.getSlots(1, 9, false);
        }
        if (action == InventoryAction.MOVE_SIMILAR) {
            if (slot.id >= 10 && slot.id <= 45) {
                return this.getSlots(10, 36, false);
            }
        }
        else {
            if (slot.id >= 10 && slot.id <= 36) {
                return this.getSlots(10, 27, false);
            }
            if (slot.id >= 37 && slot.id <= 45) {
                return this.getSlots(37, 9, false);
            }
        }
        return null;
    }

    @Override
    public List<Integer> getTargetSlots(final InventoryAction action, final Slot slot, final int target, final EntityPlayer player) {
        if (slot.id >= 10 && slot.id <= 45) {
            if (target == 1) {
                return craftingSlots.stream().filter(s -> s.isActive).map(s -> s.id).collect(Collectors.toList());
//                return this.getSlots(1, 9, false);
            }
            if (slot.id >= 10 && slot.id <= 36) {
                return this.getSlots(37, 9, false);
            }
            if (slot.id >= 37 && slot.id <= 45) {
                return this.getSlots(10, 27, false);
            }
            return null;
        }
        else {
            if (slot.id == 0) {
                return this.getSlots(10, 36, true);
            }
            return this.getSlots(10, 36, false);
        }
    }
}