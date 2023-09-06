package levistico.bconstruct.mixin;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTooltip;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = GuiTooltip.class, remap = false)
public class MixinGuiTooltip extends Gui {
    //TODO add Btool and BtoolMaterial tooltips
}
