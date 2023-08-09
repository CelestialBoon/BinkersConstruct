package levistico.bconstruct.mixin;

import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.block.Block;
import net.minecraft.core.player.inventory.InventoryPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = InventoryPlayer.class, remap = false)
public abstract class MixinInventoryPlayer {
    @Inject(method = "canHarvestBlock", at = @At("HEAD"), remap = false, cancellable = true)
    void bconsctruct_canHarvestBlockInject(Block block, CallbackInfoReturnable<Boolean> cir) {
        if(block == null) cir.setReturnValue(false);
    }
}
