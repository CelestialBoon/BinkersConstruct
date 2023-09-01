package levistico.bconstruct.mixin;

import levistico.bconstruct.tools.ToolStack;
import net.minecraft.core.achievement.stat.StatList;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Block.class, remap = false)
public class MixinBlock {
    @Shadow int id;
    @Inject(method = "harvestBlock (Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/player/EntityPlayer;IIIILnet/minecraft/core/block/entity/TileEntity;)V", cancellable = true, at = @At("HEAD"))
    void bconstruct_harvestBlock_Inject(World world, EntityPlayer entityPlayer, int x, int y, int z, int meta, TileEntity tileEntity, CallbackInfo ci) {
        entityPlayer.addStat(StatList.mineBlockStatArray[this.id], 1);
        ItemStack heldItemStack = entityPlayer.inventory.getCurrentItem();
        if(ToolStack.isBTool(heldItemStack)) {
            ToolStack.harvestBlock(heldItemStack, (Block) (Object)this, world, entityPlayer, x, y, z, meta, tileEntity);
            ci.cancel();
        }
    }
}
