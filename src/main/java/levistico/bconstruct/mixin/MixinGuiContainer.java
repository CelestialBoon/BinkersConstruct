package levistico.bconstruct.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = GuiContainer.class, remap = false)
public class MixinGuiContainer {
    @Redirect(method="<init> (Lnet/minecraft/core/player/inventory/Container;)V", at=@At(value = "INVOKE", target ="Lnet/minecraft/client/Minecraft; getMinecraft (Ljava/lang/Object;)Lnet/minecraft/client/Minecraft;"))
    public Minecraft stupidmc(Object caller){
        return Minecraft.getMinecraft(GuiContainer.class);
    }
}
