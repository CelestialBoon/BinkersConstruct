package levistico.bconstruct.crafting;

import net.minecraft.core.crafting.CraftingManager;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.InventoryPlayer;

public final class ContainerCraftingStation extends BContainer {
    public ContainerCraftingStation(InventoryPlayer inventoryplayer, CraftingTileEntity tileEntity) {
        super(inventoryplayer, tileEntity);
    }

    @Override
    public void onCraftMatrixChanged(IInventory iinventory) {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.tileEntity.inventoryCrafting));
    }
}
