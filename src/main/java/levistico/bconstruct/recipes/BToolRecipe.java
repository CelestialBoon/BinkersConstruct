package levistico.bconstruct.recipes;

import levistico.bconstruct.materials.BToolMaterial;
import levistico.bconstruct.materials.BToolMaterials;
import levistico.bconstruct.parts.*;
import levistico.bconstruct.tools.BTool;
import net.minecraft.core.player.inventory.InventoryCrafting;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import levistico.bconstruct.utils.*;
import net.minecraft.core.item.material.ToolMaterial;

import java.util.ArrayList;
import java.util.List;

public class BToolRecipe extends BRecipe {

    public BToolRecipe(BTool resultTool) {
        this.result = resultTool;
    }

    protected Pair<Boolean, ItemStack> tryCrafting(InventoryCrafting inv) {
        BTool resultTool = (BTool) result;
        Pair<Boolean, ItemStack> falseresult = new Pair<>(false, null);
        List<BToolMaterial> materials = new ArrayList<>();
        int i = 0;
        for (BToolPart part : resultTool.composition) {
            boolean isCorrect = false;
            ItemStack itemStack = inv.getStackInSlot(i);
            if (itemStack == null) return falseresult;
            Item item = inv.getStackInSlot(i).getItem();
            BToolMaterial mat = null;
            if(item.id == Item.stick.id) {
                item = BToolParts.rod;
                mat = BToolMaterials.wood;
            }
            if(!(item instanceof BToolPart)) return falseresult;
            else switch (part.eToolPart) {
                case rod:
                    isCorrect = item instanceof TPRod;
                    break;
                case axeHead:
                    isCorrect = item instanceof TPAxeHead;
                    break;
                case binding:
                    isCorrect = item instanceof TPBinding;
                    break;
                case largeGuard:
                    isCorrect = item instanceof TPLargeGuard;
                    break;
                case pickaxeHead:
                    isCorrect = item instanceof TPPickaxeHead;
                    break;
                case shovelHead:
                    isCorrect = item instanceof TPShovelHead;
                    break;
                case blade:
                    isCorrect = item instanceof TPBlade;
                    break;
            }
            if(!isCorrect) return falseresult;
            if(mat == null) mat = BToolPart.getToolMaterial(itemStack);
            materials.add(mat);
            i++;
        }
        ItemStack resultStack = new ItemStack(resultTool);
        resultTool.initializeTags(resultStack.tag, materials);
        return new Pair<>(true, resultStack);
    }
    public ItemStack[] onCraftResult(InventoryCrafting inv) {
        return Utils.decreaseAllInventoryBy(inv, 1);
    }
}
