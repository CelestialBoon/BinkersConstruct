package levistico.bconstruct.gui.containers;

import net.minecraft.src.*;

public final class ContainerCraftingStation extends ContainerCrafting {
    public ContainerCraftingStation(InventoryPlayer inventoryplayer, CraftingTileEntity tileEntity) {
        super(inventoryplayer, tileEntity);
    }

    @Override
    public void onCraftMatrixChanged(IInventory iinventory) {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.tileEntity.inventoryCrafting));
    }

    @Override
    public ItemStack onCraftResult() {
        return null;
    }
}
