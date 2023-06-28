package levistico.bconstruct.mixin;

import levistico.bconstruct.BConstruct;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityPlayer.class, remap = false)
public class MixinEntityPlayer extends EntityLiving {
    @Shadow
    public InventoryPlayer inventory;

    public MixinEntityPlayer(World world) {
        super(world);
    }

    @Inject(method = "fall (F)V", cancellable = true, at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityLiving; fall (F)V"))
    private void bconsctruct_fallInject(float f, CallbackInfo ci) {
        if (inventory.armorInventory[0] != null && inventory.armorInventory[0].getItem() != null && inventory.armorInventory[0].getItem() == BConstruct.slimeBoots) {
            EntityPlayer thePlayer = (EntityPlayer) (Object) this;
            if (thePlayer.isSneaking()) {
                super.fall(f * 0.3f);
            } else {
                //do the bounce
                if (motionY < 2f) {
                    motionY *= -0.7f;
                } else {
                    motionY *= -0.9f;
                }
                if (motionY < 0.175f) motionY = 0f;
            }
            ci.cancel();
        }
    }
}
