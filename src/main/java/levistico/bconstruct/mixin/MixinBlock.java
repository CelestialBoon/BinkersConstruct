package levistico.bconstruct.mixin;

import levistico.bconstruct.tools.BTool;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = Block.class, remap = false)
public abstract class MixinBlock {

    @Shadow
    public abstract void dropBlockWhenCrushed(World world, int x, int y, int z, int meta);
    @Shadow
    public abstract void dropBlockAsItem(World world, int x, int y, int z, int meta);

    @Inject(method = "harvestBlock(Lnet/minecraft/src/World;Lnet/minecraft/src/EntityPlayer;IIII)V", cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value="INVOKE", target = "Lnet/minecraft/src/Item;isSilkTouch()Z", shift = At.Shift.BY, by=-2))
    private void harvestBlockInject(World world, EntityPlayer entityplayer, int x, int y, int z, int meta, CallbackInfo ci, Item currentItem) {

        ItemStack currentStack = entityplayer.inventory.getCurrentItem();
        if(currentStack != null && currentStack.getItem() instanceof BTool) {
            if (BTool.isSilkTouch(currentStack)) {
                this.dropBlockWhenCrushed(world, x, y, z, meta);
            } else {
                this.dropBlockAsItem(world, x, y, z, meta);
            }
            ci.cancel();
        }
    }
}
