package levistico.bconstruct.tools.actions;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class RCAOnEmpty extends RightClickAction {
    public final TriFunction<ItemStack, World, EntityPlayer, Optional<ItemStack>> onItemRightClick;
    public RCAOnEmpty(int eRCA, TriFunction<ItemStack, World, EntityPlayer, Optional<ItemStack>> onItemRightClick) {
        super(eRCA);
        this.onItemRightClick = onItemRightClick;
    }
    @Override
    public Optional<ItemStack> onItemRightClick(@NotNull ItemStack itemstack, World world, EntityPlayer entityplayer) {
        return onItemRightClick.apply(itemstack, world, entityplayer);
    }
}
