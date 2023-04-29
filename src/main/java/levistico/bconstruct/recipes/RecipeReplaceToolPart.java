package levistico.bconstruct.recipes;

import levistico.bconstruct.materials.BToolMaterial;
import levistico.bconstruct.parts.BToolPart;
import levistico.bconstruct.tools.BTool;
import levistico.bconstruct.utils.Pair;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.ItemStack;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class RecipeReplaceToolPart extends BRecipe {
    @Override
    protected Pair<Boolean, ItemStack> tryCrafting(InventoryCrafting inv) {
        ItemStack toolStack = null;
        ItemStack partStack = null;
        Pair<Boolean, ItemStack> falseresult = new Pair<>(false, null);
        for(int i = 0; i < inv.getSizeInventory(); i++) {
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

        for(int i =0; i < tool.composition.size(); i++) {
            if(part.eToolPart == tool.composition.get(i)) { //that is the part to replace
                BToolMaterial[] materials = BTool.getMaterials(toolStack);
                BToolMaterial newMaterial = BToolPart.getToolMaterial(partStack);
                materials [i] = newMaterial;
                ItemStack resultStack = new ItemStack(tool);
                tool.constructBaseTags(resultStack.tag, Arrays.stream(materials).collect(Collectors.toList()));
                BTool.repairTool(newMaterial.getDurability(), resultStack);
                return new Pair<>(true, resultStack);
            }
        }
        return falseresult;
    }

    @Override
    public ItemStack[] onCraftResult(InventoryCrafting inv) {
        for(int i = 0; i < inv.getSizeInventory(); i++) {
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
