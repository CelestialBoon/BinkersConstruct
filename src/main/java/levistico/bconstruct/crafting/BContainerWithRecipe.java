package levistico.bconstruct.crafting;

import levistico.bconstruct.recipes.BRecipe;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.InventoryPlayer;

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
