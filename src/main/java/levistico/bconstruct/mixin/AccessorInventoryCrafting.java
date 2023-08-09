package levistico.bconstruct.mixin;

import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.InventoryCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = InventoryCrafting.class, remap = false)
public interface AccessorInventoryCrafting {
    @Accessor("eventHandler")
    void setEventHandler(Container eventHandler);
}
