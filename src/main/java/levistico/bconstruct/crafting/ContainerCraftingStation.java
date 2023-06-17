package levistico.bconstruct.crafting;

import net.minecraft.src.*;

public final class ContainerCraftingStation extends BContainer {
    public ContainerCraftingStation(InventoryPlayer inventoryplayer, CraftingTileEntity tileEntity) {
        super(inventoryplayer, tileEntity);
    }

    @Override
    public void onCraftMatrixChanged(IInventory iinventory) {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.tileEntity.inventoryCrafting));
    }
}
