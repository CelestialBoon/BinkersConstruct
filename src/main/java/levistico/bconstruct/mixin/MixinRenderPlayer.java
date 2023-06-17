package levistico.bconstruct.mixin;

import levistico.bconstruct.BConstruct;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = RenderPlayer.class, remap = false)
public class MixinRenderPlayer extends RenderLiving {
    //TODO replace first loadTexture in renderPlayer to take our own array of armorFilenamePrefix
    //
//    @Shadow private static String[] armorFilenamePrefix;

    public MixinRenderPlayer(ModelBase modelbase, float f) {
        super(modelbase, f);
    }

    @Inject(method = "setArmorModel (Lnet/minecraft/src/EntityPlayer;IF)Z", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "INVOKE", target = "Lnet/minecraft/src/RenderPlayer;loadTexture(Ljava/lang/String;)V", shift = At.Shift.AFTER))
    private void injected(EntityPlayer entityplayer, int i, float f, CallbackInfoReturnable<Boolean> cir, ItemStack itemstack, Item item, ItemArmor itemarmor) {
        this.loadTexture("/armor/" + BConstruct.armorFilenamePrefix[itemarmor.material.renderIndex] + "_" + ((i != 2) ? 1 : 2) + ".png");
    }
}
