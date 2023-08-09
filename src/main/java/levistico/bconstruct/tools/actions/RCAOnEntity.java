package levistico.bconstruct.tools.actions;

import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import org.apache.commons.lang3.function.TriFunction;

import java.util.Optional;

public class RCAOnEntity extends RightClickAction{
    public final TriFunction<ItemStack, EntityLiving, EntityPlayer, Optional<Integer>> useItemOnEntity;

    public RCAOnEntity(int eRCA, TriFunction<ItemStack, EntityLiving, EntityPlayer, Optional<Integer>> useItemOnEntity) {
        super(eRCA);
        this.useItemOnEntity = useItemOnEntity;
    }

    @Override
    public Optional<Integer> useItemOnEntity(ItemStack itemstack, EntityLiving entityliving, EntityPlayer entityPlayer) {
        return useItemOnEntity.apply(itemstack, entityliving, entityPlayer);
    }
}
