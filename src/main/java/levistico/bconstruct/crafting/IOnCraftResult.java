package levistico.bconstruct.crafting;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;

public interface IOnCraftResult {
    public void onCraftResult(ItemStack stack, EntityPlayer player);
}
