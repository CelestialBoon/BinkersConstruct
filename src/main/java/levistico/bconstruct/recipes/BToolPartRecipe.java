package levistico.bconstruct.recipes;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.materials.BToolMaterial;
import levistico.bconstruct.materials.BToolMaterials;
import levistico.bconstruct.parts.BToolPart;
import levistico.bconstruct.utils.Pair;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventoryCrafting;
import net.minecraft.core.player.inventory.InventoryCrafting;
import net.minecraft.core.item.ItemStack;

public class BToolPartRecipe extends BRecipe {

    public BToolPartRecipe(BToolPart resultPart) {
        this.result = resultPart;
    }

    protected Pair<Boolean, ItemStack> tryCrafting(InventoryCrafting inv) {
        BToolPart partResult = (BToolPart) result;
        boolean hasPattern = !partResult.isNeedsPattern || (inv.getStackInSlot(0) != null && inv.getStackInSlot(0).getItem() == BConstruct.blankPattern);
        Pair<Boolean, BToolMaterial> resultMaterial = BToolMaterials.tryIsEnoughMaterial(inv.getStackInSlot(1), partResult);
        if(resultMaterial.first && hasPattern && (resultMaterial.second.possiblePartsFlag & partResult.partFlag) > 0) {
            ItemStack resultStack = new ItemStack(partResult);
            partResult.setToolMaterial(resultStack, resultMaterial.second);
            return new Pair<>(true, resultStack);
        } else return new Pair<>(false, null);
    }
    public ItemStack[] onCraftResult(InventoryCrafting inv) {
        BToolPart partResult = (BToolPart) result;
        if(partResult.isNeedsPattern) {
            inv.decrStackSize(0, 1); //
        }
        inv.decrStackSize(1, partResult.weight);

        return new ItemStack[inv.getSizeInventory()];
    }
}
