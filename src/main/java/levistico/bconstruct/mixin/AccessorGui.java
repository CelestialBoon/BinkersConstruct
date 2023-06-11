package levistico.bconstruct.mixin;

import net.minecraft.src.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = Gui.class, remap = false)
public interface AccessorGui {
    @Accessor("zLevel")
    float getZLevel();
}
