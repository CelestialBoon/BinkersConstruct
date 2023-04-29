package levistico.bconstruct.mixin;

import levistico.bconstruct.texture.GraphicsUtils;
import net.minecraft.src.RenderEngine;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

import java.util.Map;

@Mixin(value = RenderEngine.class, remap = false)
public abstract class MixinRenderEngine {

    @Shadow
    private Map<String, Integer> textureMap = new HashMap<>();

    @Inject(method = "refreshTextures", at = @At("HEAD"))
    private void binkers_initTextures(CallbackInfo ci) {
        GraphicsUtils.importGUITextures();
        GraphicsUtils.generateToolPartsTexture();
        GraphicsUtils.generateToolBitsTexture();
//        BToolParts.InitializeToolParts(MOD_ID);
//        BTools.InitializeTools(MOD_ID);
    }

}