package levistico.bconstruct.mixin;

import levistico.bconstruct.BConstruct;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.core.player.inventory.InventoryPlayer;

@Mixin(value = EntityPlayer.class, remap = false)
public class MixinEntityPlayer extends EntityLiving {
    @Shadow
    public InventoryPlayer inventory;

    public MixinEntityPlayer(World world) {
        super(world);
    }

    @Inject(method = "causeFallDamage (F)V", cancellable = true, at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/EntityLiving; causeFallDamage (F)V"))
    private void bconsctruct_fallInject(float f, CallbackInfo ci) {
        if (inventory.armorInventory[0] != null && inventory.armorInventory[0].getItem() != null && inventory.armorInventory[0].getItem() == BConstruct.slimeBoots) {
            EntityPlayer thePlayer = (EntityPlayer) (Object) this;
            if (thePlayer.isSneaking()) {
                super.causeFallDamage(f * 0.3f);
            } else {
                //do the bounce
                if (yd < 2f) {
                    yd *= -0.7f;
                } else {
                    yd *= -0.9f;
                }
                if (yd < 0.175f) yd = 0f;
            }
            ci.cancel();
        }
    }
}
