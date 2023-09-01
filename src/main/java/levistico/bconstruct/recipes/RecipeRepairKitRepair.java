package levistico.bconstruct.recipes;

import levistico.bconstruct.materials.BToolMaterial;
import levistico.bconstruct.materials.BToolMaterials;
import levistico.bconstruct.materials.EToolMaterial;
import levistico.bconstruct.tools.BTool;
import levistico.bconstruct.tools.ToolStack;
import levistico.bconstruct.utils.Pair;
import levistico.bconstruct.parts.TPRepairKit;
import levistico.bconstruct.utils.Utils;
import net.minecraft.core.player.inventory.InventoryCrafting;
import net.minecraft.core.item.Item;;;
import net.minecraft.core.item.ItemStack;


import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class RecipeRepairKitRepair extends BRecipe {

    protected Pair<Boolean, ItemStack> tryCrafting(InventoryCrafting inv) {
        Pair<Boolean, ItemStack> falseResult = new Pair<>(false, null);
        ItemStack tool = null;
        ItemStack kit = null;
        for(Integer i : Utils.range(0, inv.getSizeInventory())) {
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
        ToolStack.repairTool(repairAmount, tool);
        return new Pair<>(true, tool);
    }

    public int repairAmount(ItemStack toolStack, ItemStack kitStack) {
        Map<Integer, Integer> materials = ToolStack.getRepairMaterials(toolStack);
        BToolMaterial kitMaterial = null;
        if(kitStack.itemID == Item.string.id) {
            kitMaterial = BToolMaterials.string;
        } else kitMaterial = TPRepairKit.getToolMaterial(kitStack);
        //make it dependent on the number of head parts of the same material the tool has, maybe a direct multiplier?
        // later editions of tinkers probably already allow multiple repair items, so go look at that for inspiration
        Integer eMat = kitMaterial.eNumber;
        return materials.keySet().stream().filter(mat -> Objects.equals(mat, eMat)).findAny().map(eNum -> {
            BToolMaterial mat = BToolMaterials.matList.get(eNum);
            float propertiesRepairFactor = Utils.reduceProduct(ToolStack.getProperties(toolStack).stream().map(prop -> prop.modifier.getRepairFactor(prop.level)));
            //formula includes the bonus for same-material parts
            return Utils.round((float) (mat.getDurability() * Math.pow(1.3, materials.get(eNum)) * propertiesRepairFactor));
        }).orElse(0);
    }

    @Override
    public ItemStack[] onCraftResult(InventoryCrafting inv) {
        for(Integer i : Utils.range(0, inv.getSizeInventory())) {
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
