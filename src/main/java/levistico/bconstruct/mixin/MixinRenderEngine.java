package levistico.bconstruct.mixin;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.gui.texture.TextureUtils;
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
    private void bconstruct_initTexturesHead(CallbackInfo ci) {
        TextureUtils.importGUITextures();
        TextureUtils.generateToolPartsTexture();
        TextureUtils.generateToolBitsTexture();
    }
    @Inject(method = "refreshTextures", at = @At("TAIL"))
    private void bconstruct_initTexturesTail(CallbackInfo ci) {
        TextureUtils.initializeSlimeTextures();
    }
}