package levistico.bconstruct.crafting;

import net.minecraft.src.*;

public final class ContainerCraftingStation extends ContainerCrafting {
    public ContainerCraftingStation(InventoryPlayer inventoryplayer, CraftingTileEntity tileEntity) {
        super(inventoryplayer, tileEntity);

        this.addSlot(new SlotCrafting(inventoryplayer.player, tileEntity.inventoryCrafting, this.craftResult, 0, 124, 35)); //craft result

        int j1;
        int l1;
        for(j1 = 0; j1 < 3; ++j1) { //crafting slots
            for(l1 = 0; l1 < 3; ++l1) {
                this.addSlot(new Slot(this.tileEntity.inventoryCrafting, l1 + j1 * 3, 30 + l1 * 18, 17 + j1 * 18));
            }
        }
    }

    @Override
    public void onCraftMatrixChanged(IInventory iinventory) {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.tileEntity.inventoryCrafting));
    }
}
