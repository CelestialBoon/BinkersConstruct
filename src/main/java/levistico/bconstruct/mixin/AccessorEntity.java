package levistico.bconstruct.mixin;

import net.minecraft.core.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = Entity.class, remap = false)
public interface AccessorEntity {
    @Accessor()
    public void setFallDistance(float f);
}
