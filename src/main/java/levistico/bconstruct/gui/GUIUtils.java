package levistico.bconstruct.gui;

import levistico.bconstruct.gui.texture.TextureUtils;
import levistico.bconstruct.materials.BToolMaterial;
import levistico.bconstruct.parts.BToolPart;
import levistico.bconstruct.parts.BToolParts;
import levistico.bconstruct.tools.BTool;
import levistico.bconstruct.utils.Utils;
import net.minecraft.src.*;
import net.minecraft.src.command.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

import static net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES;

public class GUIUtils {

    public static final int GL_BLOCK_ITEM_MAGIC_NUMBER = 32826;
    public static RenderItem itemRenderer = new RenderItem();

    public static boolean isMouseOverSlot(Slot slot, int relativeMouseX, int relativeMouseY) {
        if (slot instanceof BSlotActivatable && !((BSlotActivatable)slot).isActive) return false;

        return relativeMouseX >= slot.xDisplayPosition - 1 && relativeMouseX < slot.xDisplayPosition + 16 + 1
                && relativeMouseY >= slot.yDisplayPosition - 1 && relativeMouseY < slot.yDisplayPosition + 16 + 1;
    }

    public static StringBuilder getToolPartTooltip(StringBuilder text,ItemStack stack) {
        BToolPart part = (BToolPart) stack.getItem();
        return text.append(String.format("%s %s", BToolPart.getToolMaterial(stack).getName(), part.name));
    }

    public static StringBuilder getToolTooltip(StringBuilder text, ItemStack stack, boolean control, boolean shift) {
        BTool tool = (BTool) stack.getItem();
        text.append(tool.getItemNameIS(stack));
        if(BTool.isToolBroken(stack)) text.append(" (Broken)");
        text.append("\n");
        if (control) {
//            Control:
            List<BToolPart> parts = tool.composition.stream().map(e -> BToolParts.partArray.get(e.ordinal())).collect(Collectors.toList());
            int partCount = 0;
            for (BToolMaterial mat : BTool.getMaterials(stack)) {
                stringifyToolPart(text, parts.get(partCount), mat);
                partCount++;
            }

//            underlined colored material toolpart
//            tool part stats (like durability, mining speed, attack damage, mining level, properties, other contributions)

        } else if (shift) {
//            Shift:
//            item name
//            properties (colored)
            text.append(ChatColor.lightGray).append("Properties go here\n");
//            repair materials

//            durability (number is light blue)
            int maxDurability = BTool.getMaxDurability(stack);
            text.append(ChatColor.white).append("\nDurability: ").append(ChatColor.orange)
                    .append(maxDurability - stack.getMetadata()).append("/").append(maxDurability);
//            effective durability

//            attack damage (blue)
            text.append(ChatColor.white).append("\nAttack damage: ").append(ChatColor.red).append(BTool.getMobDamage(stack));
//                    mining speed (yellow)
            text.append(ChatColor.white).append("\nMining speed: ").append(ChatColor.yellow).append(BTool.getEfficiency(stack));
//            modifiers remaining (2)
            text.append(ChatColor.lightGray).append("\nModifiers go here");
//            modifiers:
//            (in their colors)

        } else {
//            item name
            text.append(ChatColor.lightGray).append("Properties go here\n");
//            properties go here
//            Hold Shift (yellow) for stats
            text.append(ChatColor.lightGray).append("Hold ").append(ChatColor.yellow).append("Shift").append(ChatColor.lightGray).append(" for stats\n");
//            Hold Ctrl (minecraft blue) for material
            text.append(ChatColor.lightGray).append("Hold ").append(ChatColor.blue).append("Ctrl").append(ChatColor.lightGray).append(" for materials\n");
        }

        return text;
    }

    private static StringBuilder stringifyToolPart(StringBuilder sb, BToolPart part, BToolMaterial mat) {
        return sb.append(mat.chatcolor)
//                .append(ChatFormat.underline)
                .append(mat.getName()).append(" ").append(Utils.translateItemName(part)).append("\n");
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


}
