package levistico.bconstruct.mixin;

import net.minecraft.src.RenderEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.awt.image.BufferedImage;
import java.util.Map;

@Mixin(value = RenderEngine.class, remap = false)
public interface AccessorRenderEngine {
    @Accessor("textureNameToImageMap")
    public Map<Integer, BufferedImage> getTextureNameToImageMap();
}
