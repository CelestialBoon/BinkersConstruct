package levistico.bconstruct.mixin;

import levistico.bconstruct.BConstruct;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
/*

@Mixin(value = RenderPlayer.class, remap = false)
public class MixinRenderPlayer extends RenderLiving {
    @Shadow @Final
    private static String[] armorFilenamePrefix;
    public MixinRenderPlayer(ModelBase modelbase, float f) {
        super(modelbase, f);
    }
    @Inject(method = "setArmorModel", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "INVOKE", target = "Lnet/minecraft/src/RenderPlayer;loadTexture(Ljava/lang/String;)V", shift = At.Shift.AFTER))
    private void injected(EntityPlayer entityplayer, int i, float f, CallbackInfoReturnable<Boolean> cir, ItemStack itemstack, Item item) {

        BConstruct.LOGGER.info("" + armorFilenamePrefix[6] + armorFilenamePrefix.length);
        String s = "/armor/" + armorFilenamePrefix[((ItemArmor)item).material.renderIndex] + "_" + (i != 2 ? 1 : 2) + ".png";
        BConstruct.LOGGER.info(String.format("%s:%s", this.renderManager.renderEngine.getTexture(s), s));
    }

}
*/
