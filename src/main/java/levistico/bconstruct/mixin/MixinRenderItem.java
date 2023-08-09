package levistico.bconstruct.mixin;

import levistico.bconstruct.gui.GUIUtils;
import levistico.bconstruct.materials.BToolMaterial;
import levistico.bconstruct.parts.BToolPart;
import levistico.bconstruct.gui.texture.TextureUtils;
import levistico.bconstruct.tools.BTool;
import levistico.bconstruct.gui.texture.ITexturedPart;
import levistico.bconstruct.tools.ToolStack;
import net.minecraft.client.gui.GuiRenderItem;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import turniplabs.halplibe.mixin.accessors.RenderManagerAccessor;

import java.util.Random;

import static levistico.bconstruct.BConstruct.mc;
import static net.minecraft.core.Global.TEXTURE_ATLAS_WIDTH_TILES;

@Mixin(value = ItemEntityRenderer.class, remap = false)
public abstract class MixinRenderItem extends EntityRenderer<EntityItem> {
    @Shadow
    public boolean field_27004_a;
    @Final @Shadow
    private final Random random = new Random();
    @Shadow
    public abstract void drawItemIntoGui(FontRenderer fontrenderer, RenderEngine renderengine, int i, int j, int k, int l, int i1, float brightness, float alpha);
    @Shadow
    public abstract void renderTexturedQuad(int x, int y, int tileX, int tileY, int tileWidth, int tileHeight);
    @Shadow
    public abstract void doRenderItem(EntityItem entityitem, double d, double d1, double d2, float f, float f1);

    /**
     * @author Levistico
     * @reason Injecting my own tool render
     */
    @Overwrite()
    public void renderItemIntoGUI(FontRenderer fontrenderer, RenderEngine renderengine, ItemStack itemstack, int i, int j, float alpha) {
        renderItemIntoGUI(fontrenderer, renderengine, itemstack, i, j, 1.0F, alpha);
    }
    /**
     * @author Levistico
     * @reason Injecting my own tool render
     */
    @Overwrite()
    public void renderItemIntoGUI(FontRenderer fontrenderer, RenderEngine renderengine, ItemStack itemstack, int i, int j, float brightness, float alpha) {
        if (itemstack == null) return;
        Item item = itemstack.getItem();
        if(!(item instanceof BToolPart || item instanceof BTool)) {
//            if(!(item instanceof BTool || item instanceof BToolPart)) {
            this.drawItemIntoGui(fontrenderer, renderengine, itemstack.itemID, itemstack.getMetadata(), itemstack.getIconIndex(), i, j, brightness, alpha);
            return;
        }

        float f1;
        float f3;
        GL11.glDisable(GL11.GL_LIGHTING);
        int tileWidth;

        tileWidth = TextureFX.tileWidthItems;

        int k1 = Item.itemsList[itemstack.itemID].getColorFromDamage(itemstack.getMetadata());
        float f = (float)(k1 >> 16 & 255) / 255.0F;
        f1 = (float)(k1 >> 8 & 255) / 255.0F;
        f3 = (float)(k1 & 255) / 255.0F;
        if (this.field_27004_a) {
            GL11.glColor4f(f * brightness, f1 * brightness, f3 * brightness, alpha);
        } else {
            GL11.glColor4f(brightness, brightness, brightness, alpha);
        }
//      int k = item.getIconIndex(itemstack);
        if(item instanceof BToolPart) {
            renderengine.bindTexture(TextureUtils.TOOL_PARTS_TEXTURE_INDEX);
//            BConstruct.LOGGER.info("rendering tool parts at texture: " + renderengine.getTexture(GBitsUtil.TOOL_PARTS_TEXTURE));
            BToolPart tpart = (BToolPart) item;
            int ti = tpart.texturedPart.getIconIndex(BToolPart.getToolMaterial(itemstack).eNumber, false);
            this.renderTexturedQuad(i, j, ti % TEXTURE_ATLAS_WIDTH_TILES * tileWidth, ti / TEXTURE_ATLAS_WIDTH_TILES * tileWidth, tileWidth, tileWidth);
        } else {
            renderengine.bindTexture(TextureUtils.TOOL_BITS_TEXTURE_INDEX);
//            BConstruct.LOGGER.info("rendering tool bits at texture: " + renderengine.getTexture(GBitsUtil.TOOL_BITS_TEXTURE));
            BTool tool = (BTool) item;
            boolean broken = ToolStack.isToolBroken(itemstack);
            BToolMaterial[] materials = ToolStack.getMaterials(itemstack);
            for(Integer npart : tool.renderOrder) {
                ITexturedPart part = tool.texturedParts.get(npart);
                int k = part.getIconIndex(materials[npart].eNumber, broken);
                this.renderTexturedQuad(i, j, k % TEXTURE_ATLAS_WIDTH_TILES * tileWidth, k / TEXTURE_ATLAS_WIDTH_TILES * tileWidth, tileWidth, tileWidth);
            }
        }

        GL11.glEnable(GL11.GL_LIGHTING);

        GL11.glEnable(GL11.GL_CULL_FACE);
    }

    /**
     * @author Levistico
     * @reason Dealing with item render
     */
    @Overwrite
    public void doRender(EntityItem entityitem, double d, double d1, double d2, float f, float f1) {
//        BConstruct.LOGGER.info("doRender is being called");
        if(!(entityitem.item.getItem() instanceof BToolPart || entityitem.item.getItem() instanceof BTool)) {
            this.doRenderItem(entityitem, d, d1, d2, f, f1);
            return;
        }

        this.random.setSeed(187L);
        ItemStack itemstack = entityitem.item;
        GL11.glPushMatrix();
        float f2 = MathHelper.sin(((float)entityitem.age + f1) / 10.0F + entityitem.field_804_d) * 0.1F + 0.1F;
        float f3 = (((float)entityitem.age + f1) / 20.0F + entityitem.field_804_d) * 57.29578F;
        byte renderCount = 1;
        if (entityitem.item.stackSize > 1) {
            renderCount = 2;
        }

        if (entityitem.item.stackSize > 5) {
            renderCount = 3;
        }

        if (entityitem.item.stackSize > 20) {
            renderCount = 4;
        }

        GL11.glTranslatef((float)d, (float)d1 + f2, (float)d2);
        GL11.glEnable(GUIUtils.GL_BLOCK_ITEM_MAGIC_NUMBER);

//        int tileWidth;
//        float f6;
//        float f8;
//        float f10;

        GL11.glScalef(0.5F, 0.5F, 0.5F);



//        float f12 = 1.0F;
//        float f13 = 0.5F;
//        float f14 = 0.25F;
        int j;
        float f16;
        float f18;
        float f20;
        if (this.field_27004_a) {
            j = Item.itemsList[itemstack.itemID].getColorFromDamage(itemstack.getMetadata());
            f16 = (float)(j >> 16 & 255) / 255.0F;
            f18 = (float)(j >> 8 & 255) / 255.0F;
            f20 = (float)(j & 255) / 255.0F;
            float f21 = entityitem.getBrightness(f1);
            if (mc.fullbright) {
                f21 = 1.0F;
            }

            GL11.glColor4f(f16 * f21, f18 * f21, f20 * f21, 1.0F);
        }

        if ((Boolean)mc.gameSettings.items3D.value) {
            GL11.glPushMatrix();
            GL11.glScaled(1.0, 1.0, 1.0);
            GL11.glRotated((double)f3, 0.0, 1.0, 0.0);
            GL11.glTranslated(-0.5, 0.0, -0.05 * (double)(renderCount - 1));

            for(j = 0; j < renderCount; ++j) {
                GL11.glPushMatrix();
                GL11.glTranslated(0.0, 0.0, 0.1 * (double)j);
                EntityRenderDispatcher.instance.itemRenderer.renderItem(entityitem, itemstack, false);
                GL11.glPopMatrix();
            }

            GL11.glPopMatrix();
        } else {
            for(j = 0; j < renderCount; ++j) {
                GL11.glPushMatrix();
                if (j > 0) {
                    f16 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    f18 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    f20 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    GL11.glTranslatef(f16, f18, f20);
                }

                GL11.glRotatef(180.0F - this.renderDispatcher.viewLerpYaw, 0.0F, 1.0F, 0.0F);

                if(itemstack.getItem() instanceof BToolPart) {
                    this.renderDispatcher.renderEngine.bindTexture(TextureUtils.TOOL_PARTS_TEXTURE_INDEX);
                    BToolPart tpart = (BToolPart) itemstack.getItem();
                    int ti = tpart.texturedPart.getIconIndex(BToolPart.getToolMaterial(itemstack).eNumber, false);
                    doRenderTexturedQuad(ti);
                    GL11.glPopMatrix();
                } else {
                    this.renderDispatcher.renderEngine.bindTexture(TextureUtils.TOOL_BITS_TEXTURE_INDEX);
                    BTool tool = (BTool) itemstack.getItem();
                    boolean broken = ToolStack.isToolBroken(itemstack);
                    BToolMaterial[] materials = ToolStack.getMaterials(itemstack);
                    for (Integer npart : tool.renderOrder) {
                        ITexturedPart part = tool.texturedParts.get(npart);
                        int ti = part.getIconIndex(materials[npart].eNumber, broken);
                        this.doRenderTexturedQuad(ti);
                    }
                }
                GL11.glPopMatrix();
            }
        }
        GL11.glDisable(GUIUtils.GL_BLOCK_ITEM_MAGIC_NUMBER);
        GL11.glPopMatrix();
    }

    private void doRenderTexturedQuad(int i) {
        Tessellator tessellator = Tessellator.instance;
        int tileWidth = TextureFX.tileWidthItems;
        float f6 = (float)(i % TEXTURE_ATLAS_WIDTH_TILES * tileWidth) / (float)(TEXTURE_ATLAS_WIDTH_TILES * tileWidth);
        float f8 = (float)(i % TEXTURE_ATLAS_WIDTH_TILES * tileWidth + tileWidth) / (float)(TEXTURE_ATLAS_WIDTH_TILES * tileWidth);
        float f10 = (float)(i / TEXTURE_ATLAS_WIDTH_TILES * tileWidth) / (float)(TEXTURE_ATLAS_WIDTH_TILES * tileWidth);
        float f11 = (float)(i / TEXTURE_ATLAS_WIDTH_TILES * tileWidth + tileWidth) / (float)(TEXTURE_ATLAS_WIDTH_TILES * tileWidth);

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV(-0.5, -0.25, 0.0, f6, f11);
        tessellator.addVertexWithUV(0.5, -0.25, 0.0, f8, f11);
        tessellator.addVertexWithUV(0.5, 0.75, 0.0, f8, f10);
        tessellator.addVertexWithUV(-0.5, 0.75, 0.0, f6, f10);
        tessellator.draw();
    }
}
