package levistico.bconstruct.gui;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.gui.texture.TextureUtils;
import levistico.bconstruct.materials.BToolMaterial;
import levistico.bconstruct.parts.BToolPart;
import levistico.bconstruct.parts.BToolParts;
import levistico.bconstruct.tools.BTool;
import levistico.bconstruct.tools.ToolStack;
import levistico.bconstruct.utils.Utils;
import net.minecraft.client.gui.GuiTooltip;
import net.minecraft.client.render.RenderEngine;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextureFX;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.List;
import java.util.stream.Collectors;

import static net.minecraft.core.Global.TEXTURE_ATLAS_WIDTH_TILES;

public class GUIUtils {

    public static final int GL_BLOCK_ITEM_MAGIC_NUMBER = 32826;
    public static ItemEntityRenderer itemRenderer = new ItemEntityRenderer();
    public static GuiTooltip tooltip = new GuiTooltip(BConstruct.mc);

    public static boolean isMouseOverSlot(Slot slot, int relativeMouseX, int relativeMouseY) {
        if (slot instanceof BSlotCustomizable && !((BSlotCustomizable)slot).isActive) return false;

        return relativeMouseX >= slot.xDisplayPosition - 1 && relativeMouseX < slot.xDisplayPosition + 16 + 1
                && relativeMouseY >= slot.yDisplayPosition - 1 && relativeMouseY < slot.yDisplayPosition + 16 + 1;
    }

    public static StringBuilder getToolPartTooltip(StringBuilder text,ItemStack stack) {
        BToolPart part = (BToolPart) stack.getItem();
        return text.append(String.format("%s %s", Utils.translateKey(BToolPart.getToolMaterial(stack)), Utils.translateKey(part)));
    }

    public static StringBuilder getToolTooltip(StringBuilder text, ItemStack stack, boolean control, boolean shift) {
        BTool tool = (BTool) stack.getItem();
        text.append(TextFormatting.get(stack.getData().getByte(ToolStack.COLOR))).append(stack.getItemName());
        if(ToolStack.isToolBroken(stack)) text.append(" (Broken)");
        text.append("\n");
        if (control) {
//            Control:
            int partCount = 0;
            for (BToolMaterial mat : ToolStack.getMaterials(stack)) {
                stringifyToolPart(text, tool.composition.get(partCount), mat);
                partCount++;
            }

//            underlined colored material toolpart
//            tool part stats (like durability, mining speed, attack damage, mining level, properties, other contributions)

        } else if (shift) {
//            Shift:
//            item name
//            properties (colored)
            text.append(TextFormatting.LIGHT_GRAY).append("Properties go here\n");
//            repair materials

//            durability (number is light blue)
            int maxDurability = ToolStack.getMaxDurability(stack);
            text.append(TextFormatting.WHITE).append("\nDurability: ").append(TextFormatting.ORANGE)
                    .append(maxDurability - stack.getMetadata()).append("/").append(maxDurability);
//            effective durability

//            attack damage (blue)
            text.append(TextFormatting.WHITE).append("\nAttack damage: ").append(TextFormatting.RED).append(ToolStack.getAttackDamage(stack));
//                    mining speed (yellow)
            text.append(TextFormatting.WHITE).append("\nMining speed: ").append(TextFormatting.YELLOW).append(ToolStack.getMiningSpeed(stack));
//            modifiers remaining (2)
            text.append(TextFormatting.LIGHT_GRAY).append("\nModifiers go here");
//            modifiers:
//            (in their colors)

        } else {
//            item name
            text.append(TextFormatting.LIGHT_GRAY).append("Properties go here\n");
//            properties go here
//            Hold Shift (yellow) for stats
            text.append(TextFormatting.LIGHT_GRAY).append("Hold ").append(TextFormatting.YELLOW).append("Shift").append(TextFormatting.LIGHT_GRAY).append(" for stats\n");
//            Hold Ctrl (minecraft blue) for material
            text.append(TextFormatting.LIGHT_GRAY).append("Hold ").append(TextFormatting.BLUE).append("Ctrl").append(TextFormatting.LIGHT_GRAY).append(" for materials\n");
        }

        return text;
    }

    private static StringBuilder stringifyToolPart(StringBuilder sb, BToolPart part, BToolMaterial mat) {
        return sb.append(mat.chatcolor)
//                .append(ChatFormat.underline)
                .append(Utils.translateKey(mat)).append(" ").append(Utils.translateKey(part)).append("\n");
    }

    public static void drawTexturedModalRect(int x, int y, int u, int v, int width, int height, int tileWidth, int tileHeight, float factor, float zLevel) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + height, zLevel, (float)(u) * factor, (float)(v + tileHeight) * factor);
        tessellator.addVertexWithUV(x + width, y + height, zLevel, (float)(u + tileWidth) * factor, (float)(v + tileHeight) * factor);
        tessellator.addVertexWithUV(x + width, y, zLevel, (float)(u + tileWidth) * factor, (float)(v) * factor);
        tessellator.addVertexWithUV(x, y, zLevel, (float)(u) * factor, (float)(v) * factor);
        tessellator.draw();
    }

    public static void drawRegularGUITexture(int x, int y, int k, float zLevel) {
        drawTexturedModalRect(x, y, k % TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthItems,
                k / TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthItems, 16, 16,
                TextureFX.tileWidthItems, TextureFX.tileWidthItems, TextureUtils.REGULAR_TEXTURE_FACTOR, zLevel);
    }

    public static void drawLargeGUITexture(int x, int y, int u, int v, float zLevel) {
        drawTexturedModalRect(x, y, u*18, v*18,18, 18, 18, 18,TextureUtils.LARGE_TEXTURE_FACTOR, zLevel);
    }

    public static String formatDescription(final String description, final int preferredLineLength) {
        final StringBuilder string = new StringBuilder();
        string.append(TextFormatting.LIGHT_GRAY);
        int lineLength = 0;
        for (int i = 0; i < description.length(); ++i) {
            final char c = description.charAt(i);
            if (c == ' ') {
                if (lineLength > preferredLineLength) {
                    lineLength = 0;
                    string.append("\n").append(TextFormatting.LIGHT_GRAY);
                }
                else {
                    string.append(c);
                }
            }
            else {
                ++lineLength;
                string.append(c);
            }
        }
        return string.toString();
    }


}
