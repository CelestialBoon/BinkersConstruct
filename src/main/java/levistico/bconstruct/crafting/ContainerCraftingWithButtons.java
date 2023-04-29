package levistico.bconstruct.crafting;

import levistico.bconstruct.recipes.BRecipe;
import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.List;

public abstract class ContainerCraftingWithButtons extends ContainerCrafting implements IOnCraftResult {

    public BRecipe recipe;
    public List<GuiButton> buttons = new ArrayList<>();

    public ContainerCraftingWithButtons(InventoryPlayer inventoryplayer, CraftingTileEntity tileEntity) {
        super(inventoryplayer, tileEntity);
    }

    public abstract BRecipe getRecipe() ;

    public void onCraftMatrixChanged() {
        onCraftMatrixChanged(null);
    }
    @Override
    public void onCraftMatrixChanged(IInventory noUse) {
        this.craftResult.setInventorySlotContents(0, getRecipe().getCraftingResult(tileEntity.inventoryCrafting));
    }

    public ItemStack onCraftResult() {
        recipe.onCraftResult(tileEntity.inventoryCrafting);
        return new ItemStack(Block.marble);
    }
}
