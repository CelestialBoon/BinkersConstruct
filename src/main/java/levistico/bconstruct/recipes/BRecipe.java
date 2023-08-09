package levistico.bconstruct.recipes;

import levistico.bconstruct.utils.Pair;
import net.minecraft.core.crafting.recipe.IRecipe;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventoryCrafting;

;
;

public abstract class BRecipe implements IRecipe {
    public Item result;
    @Override
    public boolean matches(InventoryCrafting inventoryCrafting) {
        Pair<Boolean, ItemStack> result = tryCrafting(inventoryCrafting);
        return result.first;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        Pair<Boolean, ItemStack> result = tryCrafting(inventoryCrafting);
        return result.second;
    }

    protected abstract Pair<Boolean, ItemStack> tryCrafting(InventoryCrafting inv);

    @Override
    public int getRecipeSize() {
        return 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    @Override
    public abstract ItemStack[] onCraftResult(InventoryCrafting inv);
}
