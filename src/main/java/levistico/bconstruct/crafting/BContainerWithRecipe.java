package levistico.bconstruct.crafting;

import levistico.bconstruct.gui.panels.IPanel;
import levistico.bconstruct.recipes.BRecipe;
import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.List;

public abstract class BContainerWithRecipe extends BContainer {
    public BRecipe recipe;

    public BContainerWithRecipe(InventoryPlayer inventoryplayer, CraftingTileEntity tileEntity) {
        super(inventoryplayer, tileEntity);
    }

    public abstract BRecipe getRecipe();

    public void onCraftMatrixChanged() {
        onCraftMatrixChanged(null);
    }
    @Override
    public void onCraftMatrixChanged(IInventory noUse) {
        this.craftResult.setInventorySlotContents(0, getRecipe().getCraftingResult(tileEntity.inventoryCrafting));
    }

    @Override
    public void onCraftResult(ItemStack stack, EntityPlayer player) {
        recipe.onCraftResult(tileEntity.inventoryCrafting);
    }
}
