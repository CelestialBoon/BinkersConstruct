package levistico.bconstruct.tools.actions;

import levistico.bconstruct.utils.Function9;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

import java.util.Optional;

public class RCAOnWorld extends RightClickAction {
    public final Function9<ItemStack, EntityPlayer, World, Integer, Integer, Integer, Side, Double, Double, Optional<Integer>> onItemUse;

    public RCAOnWorld(int eRCA, Function9<ItemStack, EntityPlayer, World, Integer, Integer, Integer, Side, Double, Double, Optional<Integer>> onItemUse) {
        super(eRCA);
        this.onItemUse = onItemUse;
    }

    @Override
    public Optional<Integer> onItemUse(ItemStack itemstack, EntityPlayer entityPlayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
        return onItemUse.apply(itemstack, entityPlayer, world, blockX, blockY, blockZ, side, xPlaced, yPlaced);
    }
}
