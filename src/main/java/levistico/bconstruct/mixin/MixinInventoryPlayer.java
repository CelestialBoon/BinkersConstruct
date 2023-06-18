package levistico.bconstruct.mixin;

import net.minecraft.src.Block;
import net.minecraft.src.InventoryPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = InventoryPlayer.class, remap = false)
public abstract class MixinInventoryPlayer {
    @Inject(method = "canHarvestBlock", at = @At("HEAD"), remap = false, cancellable = true)
    void canHarvestBlock(Block block, CallbackInfoReturnable<Boolean> cir) {
        if(block == null) cir.setReturnValue(false);
    }
}
