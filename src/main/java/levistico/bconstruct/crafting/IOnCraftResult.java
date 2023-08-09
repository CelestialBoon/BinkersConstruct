package levistico.bconstruct.crafting;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;

public interface IOnCraftResult {
    public void onCraftResult(ItemStack stack, EntityPlayer player);
}
