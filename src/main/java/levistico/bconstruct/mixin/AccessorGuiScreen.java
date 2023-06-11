package levistico.bconstruct.mixin;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = GuiScreen.class, remap = false)
public interface AccessorGuiScreen {
    @Accessor
    FontRenderer getFontRenderer();
}
