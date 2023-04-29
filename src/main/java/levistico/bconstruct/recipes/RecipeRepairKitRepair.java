package levistico.bconstruct.recipes;

import levistico.bconstruct.materials.BToolMaterial;
import levistico.bconstruct.tools.BTool;
import levistico.bconstruct.utils.Pair;
import levistico.bconstruct.parts.TPRepairKit;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.ItemStack;


import java.util.Optional;
import java.util.Set;

public final class RecipeRepairKitRepair extends BRecipe {

    protected Pair<Boolean, ItemStack> tryCrafting(InventoryCrafting inv) {
        Pair<Boolean, ItemStack> falseResult = new Pair<>(false, null);
        ItemStack tool = null;
        ItemStack kit = null;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(stack == null) continue;
            else if(stack.getItem() instanceof BTool) {
                if (tool != null) return falseResult;
                tool = stack;
            } else if(stack.getItem() instanceof TPRepairKit) {
                if (kit != null) return falseResult;
                kit = stack;
            } else return falseResult;
        }
        if(tool == null || kit == null || tool.getMetadata() == 0) return falseResult;
        int repairAmount = repairAmount(tool, kit);
        if(repairAmount == 0) return falseResult;
        tool = tool.copy();
        BTool.repairTool(repairAmount, tool);
        return new Pair<>(true, tool);
    }

    public int repairAmount(ItemStack toolStack, ItemStack kitStack) {
        Set<BToolMaterial> materials = ((BTool)toolStack.getItem()).getRepairMaterials(toolStack);
        BToolMaterial kitMaterial = TPRepairKit.getToolMaterial(kitStack);
        Optional<BToolMaterial> match = materials.stream().filter(mat -> mat == kitMaterial).findAny();
        //TODO temporary for alpha, put realer repair formula next
        //make it dependent on the number of head parts of the same material the tool has, maybe a direct multiplier?
        // later editions of tinkers probably already allow multiple repair items, so go look at that for inspiration
        return match.map(BToolMaterial::getDurability).orElse(0);
    }

    @Override
    public ItemStack[] onCraftResult(InventoryCrafting inv) {
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(stack == null) continue;
            if(stack.getItem() instanceof BTool) {
                inv.setInventorySlotContents(i, null);
            } else if(stack.getItem() instanceof TPRepairKit) {
                inv.decrStackSize(i, 1);
            }
        }
        return new ItemStack[inv.getSizeInventory()];
    }
}
