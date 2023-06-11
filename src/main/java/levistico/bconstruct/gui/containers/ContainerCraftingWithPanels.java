package levistico.bconstruct.gui.containers;

import levistico.bconstruct.crafting.IOnCraftResult;
import levistico.bconstruct.gui.panels.IPanel;
import levistico.bconstruct.recipes.BRecipe;
import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.List;

public abstract class ContainerCraftingWithPanels extends ContainerCrafting implements IOnCraftResult {

    //TODO handle hand and hand actions
    public BRecipe recipe;
    public List<IPanel> panels = new ArrayList<>();

    public ContainerCraftingWithPanels(InventoryPlayer inventoryplayer, CraftingTileEntity tileEntity) {
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
