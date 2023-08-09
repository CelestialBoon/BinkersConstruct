package levistico.bconstruct.tools.actions;

import levistico.bconstruct.utils.Function8;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

import java.util.Optional;

public class RCAOnWorld extends RightClickAction {
    public final Function8<ItemStack, EntityPlayer, World, Integer, Integer, Integer, Integer, Double, Optional<Integer>> onItemUse;

    public RCAOnWorld(int eRCA, Function8<ItemStack, EntityPlayer, World, Integer, Integer, Integer, Integer, Double, Optional<Integer>> onItemUse) {
        super(eRCA);
        this.onItemUse = onItemUse;
    }

    @Override
    public Optional<Integer> onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int sideHit, double heightPlaced) {
        return onItemUse.apply(itemstack, entityplayer, world, x, y, z, sideHit, heightPlaced);
    }
}
