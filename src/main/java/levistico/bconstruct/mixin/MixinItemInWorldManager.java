package levistico.bconstruct.mixin;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.tools.ToolStack;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ItemInWorldManager.class, remap = false)
public class MixinItemInWorldManager {
    @Redirect(method = "func_325_c (III)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Block;harvestBlock(Lnet/minecraft/src/World;Lnet/minecraft/src/EntityPlayer;IIII)V"))
    void bconstruct_func_325_cInject(Block block, World world, EntityPlayer entityplayer, int x, int y, int z, int meta) {
        ItemStack stack = entityplayer.getCurrentEquippedItem();
        if(ToolStack.isBTool(stack)) {
            ToolStack.harvestBlock(stack, block, world, entityplayer, x, y, z, meta);
        } else block.harvestBlock(world, entityplayer, x, y, z, meta);
    }
}
