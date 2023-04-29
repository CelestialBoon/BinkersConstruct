package levistico.bconstruct.mixin;

import net.minecraft.src.Container;
import net.minecraft.src.InventoryCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = InventoryCrafting.class, remap = false)
public interface MixinInventoryCrafting {
    @Accessor("eventHandler")
    void setEventHandler(Container eventHandler);
}
