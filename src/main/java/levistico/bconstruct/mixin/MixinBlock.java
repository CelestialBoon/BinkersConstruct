package levistico.bconstruct.mixin;

import levistico.bconstruct.tools.ToolStack;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = Block.class, remap = false)
public class MixinBlock {
    //TODO refactor for new harvestBlock code
//    @Redirect(method = "harvestBlock (Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/player/EntityPlayer;IIIILnet/minecraft/core/block/entity/TileEntity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Block;harvestBlock(Lnet/minecraft/src/World;Lnet/minecraft/core/entity/EntityPlayer;IIII)V"))
//    void bconstruct_func_325_cInject(Block block, World world, EntityPlayer entityplayer, int x, int y, int z, int meta) {
//        ItemStack stack = entityplayer.getCurrentEquippedItem();
//        if(ToolStack.isBTool(stack)) {
//            ToolStack.harvestBlock(stack, block, world, entityplayer, x, y, z, meta);
//        } else block.harvestBlock(world, entityplayer, x, y, z, meta);
//    }
}
