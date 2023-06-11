package levistico.bconstruct.mixin;

import net.minecraft.src.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = NBTTagCompound.class, remap = false)
public interface AccessorNBTTagCompound {
    @Accessor()
    public Map getTagMap();

}
