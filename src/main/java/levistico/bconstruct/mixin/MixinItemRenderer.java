package levistico.bconstruct.mixin;

import levistico.bconstruct.gui.GUIUtils;
import levistico.bconstruct.materials.BToolMaterial;
import levistico.bconstruct.parts.BToolPart;
import levistico.bconstruct.gui.texture.TextureUtils;
import levistico.bconstruct.gui.texture.ITexturedPart;
import levistico.bconstruct.tools.BTool;
import levistico.bconstruct.tools.ToolStack;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = ItemRenderer.class, remap = false)
public class MixinItemRenderer {
    @Shadow
    private Minecraft mc;
    @Inject(method = "renderItem(Lnet/minecraft/src/Entity;Lnet/minecraft/src/ItemStack;Z)V", cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD, at = @At("HEAD"))
    private void bconsctruct_renderInject(Entity entity, ItemStack itemstack, boolean handheldTransform, CallbackInfo ci) {
        if(!(itemstack.getItem() instanceof BToolPart || itemstack.getItem() instanceof BTool)) {
            return;
        }
        GL11.glPushMatrix();



//        int i = itemstack.getItem().getIconIndex(itemstack);
//        if (entity instanceof EntityLiving) {
//            i = ((EntityLiving)entity).getItemIcon(itemstack);
//        }

        float f5 = 0.0F;
        float f6 = 0.3F;
        GL11.glEnable(GUIUtils.GL_BLOCK_ITEM_MAGIC_NUMBER);
        if (handheldTransform) {
            GL11.glTranslatef(-f5, -f6, 0.0F);
            float thickness = 1.5F;
            GL11.glScalef(thickness, thickness, thickness);
            GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
        }

        if(itemstack.getItem() instanceof BToolPart) {
            GL11.glBindTexture(3553, TextureUtils.TOOL_PARTS_TEXTURE_INDEX);
            BToolPart tpart = (BToolPart) itemstack.getItem();
            int ti = tpart.texturedPart.getIconIndex(BToolPart.getToolMaterial(itemstack).eNumber, false);
            doRenderItem(ti, handheldTransform);
        } else {
            GL11.glBindTexture(3553, TextureUtils.TOOL_BITS_TEXTURE_INDEX);
            BTool tool = (BTool) itemstack.getItem();
            boolean broken = ToolStack.isToolBroken(itemstack);
            BToolMaterial[] materials = ToolStack.getMaterials(itemstack);
            for(Integer npart : tool.renderOrder) {
                ITexturedPart part = tool.texturedParts.get(npart);
                int ti = part.getIconIndex(materials[npart].eNumber, broken);
                doRenderItem(ti, handheldTransform);
            }
        }

        GL11.glDisable(GUIUtils.GL_BLOCK_ITEM_MAGIC_NUMBER);

        GL11.glPopMatrix();
        ci.cancel();
    }

    private void doRenderItem(int i, boolean handheldTransform) {
        int tileWidth = TextureFX.tileWidthItems;

        Tessellator tessellator = Tessellator.instance;
        float f = ((float)(i % net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * tileWidth) + 0.0F) / (float)(net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * tileWidth);
        float f1 = ((float)(i % net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * tileWidth) + ((float)tileWidth - 0.01F)) / (float)(net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * tileWidth);
        float f2 = ((float)(i / net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * tileWidth) + 0.0F) / (float)(net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * tileWidth);
        float f3 = ((float)(i / net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * tileWidth) + ((float)tileWidth - 0.01F)) / (float)(net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * tileWidth);

        float f4 = 1.0F;
        float foon = 0.5F / (float)tileWidth / (float)net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES;
        float goon = 0.0625F * (16.0F / (float)tileWidth);
        float thickness;

        thickness = 0.0625F;
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, f1, f3);
        tessellator.addVertexWithUV(f4, 0.0, 0.0, f, f3);
        tessellator.addVertexWithUV(f4, 1.0, 0.0, f, f2);
        tessellator.addVertexWithUV(0.0, 1.0, 0.0, f1, f2);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        tessellator.addVertexWithUV(0.0, 1.0, (0.0F - thickness), f1, f2);
        tessellator.addVertexWithUV(f4, 1.0, (0.0F - thickness), f, f2);
        tessellator.addVertexWithUV(f4, 0.0, (0.0F - thickness), f, f3);
        tessellator.addVertexWithUV(0.0, 0.0, (0.0F - thickness), f1, f3);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);

        int i1;
        float f12;
        float f16;
        float f20;
        for(i1 = 0; i1 < tileWidth; ++i1) {
            f12 = (float)i1 / (float)tileWidth;
            f16 = f1 + (f - f1) * f12 - foon;
            f20 = f4 * f12;
            tessellator.addVertexWithUV(f20, 0.0, (0.0F - thickness), f16, f3);
            tessellator.addVertexWithUV(f20, 0.0, 0.0, f16, f3);
            tessellator.addVertexWithUV(f20, 1.0, 0.0, f16, f2);
            tessellator.addVertexWithUV(f20, 1.0, (0.0F - thickness), f16, f2);
        }

        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);

        for(i1 = 0; i1 < tileWidth; ++i1) {
            f12 = (float)i1 / (float)tileWidth;
            f16 = f1 + (f - f1) * f12 - foon;
            f20 = f4 * f12 + goon;
            tessellator.addVertexWithUV(f20, 1.0, (0.0F - thickness), f16, f2);
            tessellator.addVertexWithUV(f20, 1.0, 0.0, f16, f2);
            tessellator.addVertexWithUV(f20, 0.0, 0.0, f16, f3);
            tessellator.addVertexWithUV(f20, 0.0, (0.0F - thickness), f16, f3);
        }

        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);

        for(i1 = 0; i1 < tileWidth; ++i1) {
            f12 = (float)i1 / (float)tileWidth;
            f16 = f3 + (f2 - f3) * f12 - foon;
            f20 = f4 * f12 + goon;
            tessellator.addVertexWithUV(0.0, f20, 0.0, f1, f16);
            tessellator.addVertexWithUV(f4, f20, 0.0, f, f16);
            tessellator.addVertexWithUV(f4, f20, (0.0F - thickness), f, f16);
            tessellator.addVertexWithUV(0.0, f20, (0.0F - thickness), f1, f16);
        }

        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);

        for(i1 = 0; i1 < tileWidth; ++i1) {
            f12 = (float)i1 / (float)tileWidth;
            f16 = f3 + (f2 - f3) * f12 - foon;
            f20 = f4 * f12;
            tessellator.addVertexWithUV(f4, f20, 0.0, f, f16);
            tessellator.addVertexWithUV(0.0, f20, 0.0, f1, f16);
            tessellator.addVertexWithUV(0.0, f20, (0.0F - thickness), f1, f16);
            tessellator.addVertexWithUV(f4, f20, (0.0F - thickness), f, f16);
        }
        tessellator.draw();
    }
}
