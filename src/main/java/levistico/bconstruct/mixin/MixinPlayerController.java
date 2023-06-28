package levistico.bconstruct.mixin;


import levistico.bconstruct.tools.BTool;
import levistico.bconstruct.tools.ToolStack;
import net.minecraft.client.Minecraft;
import net.minecraft.src.ItemStack;
import net.minecraft.src.PlayerController;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = PlayerController.class, remap = false)
public class MixinPlayerController {

    @Shadow protected int blockHitDelay;

    @Shadow @Final protected Minecraft mc;

    @Redirect(method = "mine (IIII)V", at = @At(value = "FIELD", target = "Lnet/minecraft/src/PlayerController;blockHitDelay:I", opcode = Opcodes.PUTFIELD, ordinal = 2))
    void bconstruct_mineInject(PlayerController instance, int value) {
        ItemStack stack = this.mc.thePlayer.inventory.getCurrentItem();
        if(stack.getItem() instanceof BTool) {
            this.blockHitDelay = ToolStack.getBlockHitDelay(stack);
        } else this.blockHitDelay = stack.getItem().getBlockHitDelay();
    }
}
