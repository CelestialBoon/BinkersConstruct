package levistico.bconstruct.recipes;

import levistico.bconstruct.materials.BToolMaterial;
import levistico.bconstruct.parts.BToolPart;
import levistico.bconstruct.tools.BTool;
import levistico.bconstruct.tools.ToolStack;
import levistico.bconstruct.utils.Pair;
import levistico.bconstruct.utils.Utils;
import net.minecraft.core.player.inventory.InventoryCrafting;
import net.minecraft.core.item.ItemStack;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class RecipeReplaceToolPart extends BRecipe {
    @Override
    protected Pair<Boolean, ItemStack> tryCrafting(InventoryCrafting inv) {
        ItemStack toolStack = null;
        ItemStack partStack = null;
        Pair<Boolean, ItemStack> falseresult = new Pair<>(false, null);
        for(Integer i : Utils.range(0, inv.getSizeInventory())) {
            ItemStack stack = inv.getStackInSlot(i);
            if(stack == null) continue;
            else if(stack.getItem() instanceof BTool) {
                if (toolStack != null) return falseresult;
                else toolStack = stack;
            } else if(stack.getItem() instanceof BToolPart) {
                if(partStack != null) return falseresult;
                else partStack = stack;
            }
        }
        if(toolStack == null || partStack == null) return falseresult;
        BTool tool = (BTool) toolStack.getItem();
        BToolPart part = (BToolPart) partStack.getItem();

        for(Integer i : Utils.range(0, tool.composition.size())) {
            if(part.eToolPart == tool.composition.get(i).eToolPart) { //that is the part to replace, this is where it is determined
                BToolMaterial[] materials = ToolStack.getMaterials(toolStack);
                BToolMaterial newMaterial = BToolPart.getToolMaterial(partStack);
                materials [i] = newMaterial;
                ItemStack resultStack = new ItemStack(tool);
                tool.initializeTags(resultStack.getData(), Arrays.stream(materials).collect(Collectors.toList()));
                ToolStack.repairTool(newMaterial.getDurability(), resultStack);
                return new Pair<>(true, resultStack);
            }
        }
        return falseresult;
    }

    @Override
    public ItemStack[] onCraftResult(InventoryCrafting inv) {
        for(Integer i : Utils.range(0, inv.getSizeInventory())) {
            ItemStack stack = inv.getStackInSlot(i);
            if(stack == null) continue;
            if(stack.getItem() instanceof BTool) {
                inv.setInventorySlotContents(i, null);
            } else if(stack.getItem() instanceof BToolPart) {
                inv.decrStackSize(i, 1);
            }
        }
        return new ItemStack[0];
    }
}
