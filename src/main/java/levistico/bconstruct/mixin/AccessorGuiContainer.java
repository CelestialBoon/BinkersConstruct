package levistico.bconstruct.mixin;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.client.gui.GuiRenderItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
@Mixin(value = GuiContainer.class, remap = false)
public interface AccessorGuiContainer {
        @Accessor("guiRenderItem")
        GuiRenderItem getGuiRenderItem();
}
