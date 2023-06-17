package levistico.bconstruct.mixin;

import net.minecraft.src.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = Item.class, remap = false)
public interface AccessorItem {
    @Accessor()
    void setMaxDamage(int i);
}
