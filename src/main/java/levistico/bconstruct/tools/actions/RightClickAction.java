package levistico.bconstruct.tools.actions;

import levistico.bconstruct.utils.Utils;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class RightClickAction {
    public final int eRCA;

    public RightClickAction(int eRCA) {
        this.eRCA = eRCA;
        Utils.setAt(RightClickActions.rcactions, eRCA, this);
    }

    public Optional<Integer> onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int sideHit, double heightPlaced) {
        return Optional.empty();
    }
    public Optional<ItemStack> onItemRightClick(@NotNull ItemStack itemstack, World world, EntityPlayer entityplayer) {
        return Optional.empty();
    }
    public Optional<Integer> useItemOnEntity(ItemStack itemstack, EntityLiving entityliving, EntityPlayer entityPlayer) {
        return Optional.empty();
    }
}
