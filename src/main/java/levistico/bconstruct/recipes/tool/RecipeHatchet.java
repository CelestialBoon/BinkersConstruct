package levistico.bconstruct.recipes.tool;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.materials.BToolMaterial;
import levistico.bconstruct.recipes.BToolRecipe;
import levistico.bconstruct.tools.BTool;
import levistico.bconstruct.utils.Pair;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;


/*public final class RecipeHatchet extends BToolRecipe {
    protected Pair<Boolean, ItemStack> tryCrafting(InventoryCrafting inv) {
        Pair<Boolean, ItemStack> falseResult = new Pair<>(false, null);

        if(inv.getItemStackAt(0, 0) != null &&
                inv.getItemStackAt(0, 0).getItem() == Item.flint &&
                inv.getItemStackAt(1, 0) != null &&
                inv.getItemStackAt(1, 0).getItem() == Item.flint &&
                inv.getItemStackAt(2, 0) == null &&
                inv.getItemStackAt(0, 1) != null &&
                inv.getItemStackAt(0, 1).getItem() == Item.flint &&
                inv.getItemStackAt(1, 1) != null &&
                inv.getItemStackAt(1, 1).getItem() == Item.stick &&
                inv.getItemStackAt(2, 1) == null &&
                inv.getItemStackAt(0, 2) == null &&
                inv.getItemStackAt(1, 2) != null &&
                inv.getItemStackAt(1, 2).getItem() == Item.stick &&
                inv.getItemStackAt(2, 2) == null
        ) {
            ItemStack hatchet = new ItemStack(BTool.hatchet);
            hatchet.tag = BTool.hatchet.constructTags(hatchet.tag, new BToolMaterial[]{BToolMaterial.flint}, new BToolMaterial[]{BToolMaterial.wood}, new BToolMaterial[]{BToolMaterial.wood});
            return new Pair<>(true, hatchet);
        } else return falseResult;
    }
    @Override
    public int getRecipeSize() {
        return 3;
    }}*/

    /*Pair<Boolean, ItemStack> tryCrafting(InventoryCrafting inv) {
        Pair<Boolean, ItemStack> falseResult = new Pair<>(false, null);
        ItemStack head = null;
        BToolMaterial headMaterial = null;
        ItemStack binding = null;
        BToolMaterial bindingMaterial = null;
        ItemStack rod = null;
        BToolMaterial rodMaterial = null;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack == null) continue;
            else if (i == 1 && stack.getItem() instanceof ToolPartPickaxeHead) {
                if (head != null) return falseResult;
                head = stack;
                headMaterial = getToolMaterial(stack);
            } else if (i == 4 && stack.getItem() instanceof ToolPartBinding) {
                if (binding != null) return falseResult;
                binding = stack;
                bindingMaterial = getToolMaterial(stack);
            } else if (i == 7 && stack.getItem() instanceof ToolPartRod) {
                if (rod != null) return falseResult;
                rod = stack;
                rodMaterial = getToolMaterial(stack);
            } else return falseResult;
        }
        if (head == null || binding == null || rod == null) return falseResult;
        BConstruct.LOGGER.info("trying to produce the pick");
        ItemStack pick = new ItemStack(BConstruct.pickaxe);

        BConstruct.pickaxe.constructTags(pick.tag, new BToolMaterial[]{headMaterial}, new BToolMaterial[]{bindingMaterial}, new BToolMaterial[]{rodMaterial});
        BConstruct.LOGGER.info("Produced the pick");
        return new Pair<>(true, pick);
    }*/

