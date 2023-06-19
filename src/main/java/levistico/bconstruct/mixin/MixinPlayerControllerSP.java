package levistico.bconstruct.mixin;

import levistico.bconstruct.tools.ToolStack;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = PlayerControllerSP.class, remap = false)
public class MixinPlayerControllerSP {
    @Redirect(method = "destroyBlock (IIII)Z", at=@At(value = "INVOKE", target = "Lnet/minecraft/src/Block;harvestBlock (Lnet/minecraft/src/World;Lnet/minecraft/src/EntityPlayer;IIII)V"))
    void bconstruct_destroyBlockInject(Block block, World world, EntityPlayer entityplayer, int x, int y, int z, int meta) {
        ItemStack stack = entityplayer.getCurrentEquippedItem();
        if(ToolStack.isBTool(stack)) {
            ToolStack.harvestBlock(stack, block, world, entityplayer, x, y, z, meta);
        } else block.harvestBlock(world, entityplayer, x, y, z, meta);
    }
}
