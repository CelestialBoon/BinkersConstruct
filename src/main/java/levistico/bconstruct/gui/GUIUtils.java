package levistico.bconstruct.gui;

import levistico.bconstruct.materials.BToolMaterial;
import levistico.bconstruct.parts.BToolPart;
import levistico.bconstruct.parts.BToolParts;
import levistico.bconstruct.tools.BTool;
import levistico.bconstruct.utils.Utils;
import net.minecraft.src.*;
import net.minecraft.src.command.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class GUIUtils {

    public static boolean isMouseOverSlot(Slot slot, int mouseRelativeX, int mouseRelativeY) {
        if (slot instanceof BSlotActivatable && !((BSlotActivatable)slot).isActive) return false;

        return mouseRelativeX >= slot.xDisplayPosition - 1 && mouseRelativeX < slot.xDisplayPosition + 16 + 1
                && mouseRelativeY >= slot.yDisplayPosition - 1 && mouseRelativeY < slot.yDisplayPosition + 16 + 1;
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
    public static void drawGUITexture(int x, int y, float zLevel, int u, int v) {
        u *= 18;
        v *= 18;
        float div = (float) 1 / (18*14);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0, y + 18, zLevel, (float)(u + 0) * div, (float)(v + 18) * div);
        tessellator.addVertexWithUV(x + 18, y + 18, zLevel, (float)(u + 18) * div, (float)(v + 18) * div);
        tessellator.addVertexWithUV(x + 18, y + 0, zLevel, (float)(u + 18) * div, (float)(v + 0) * div);
        tessellator.addVertexWithUV(x + 0, y + 0, zLevel, (float)(u + 0) * div, (float)(v + 0) * div);
        tessellator.draw();
    }
}
