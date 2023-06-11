package levistico.bconstruct.tools;

import levistico.bconstruct.parts.EToolPart;
import levistico.bconstruct.gui.texture.TexturedToolBitBreakable;
import levistico.bconstruct.gui.texture.TexturedToolBitReliable;
import levistico.bconstruct.utils.Pair;
import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;

public class ToolHatchet extends BTool {
    private static Material[] materialsEffectiveAgainst = {Material.wood, Material.leaves, Material.pumpkin, Material.woodWet, Material.cactus};

    public ToolHatchet(int id) {
        super(id,"Hatchet", "hatchet", materialsEffectiveAgainst, new Pair<>(6,0));
        composition.add(EToolPart.axeHead);
        texturedParts.add(new TexturedToolBitBreakable(EToolBit.Hatchet_Head, EToolBit.Hatchet_Head_Broken));
        composition.add(EToolPart.binding);
        texturedParts.add(new TexturedToolBitReliable(EToolBit.Hatchet_Binding));
        composition.add(EToolPart.rod);
        texturedParts.add(new TexturedToolBitReliable(EToolBit.BasicHandle));
        renderOrder.add(2); //handle
        renderOrder.add(1); //binding
        renderOrder.add(0); //head
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemstack, int i, int j, int k, int l, EntityLiving player) {
        Block block = Block.blocksList[i];
        if (block != null && block.getHardness() > 0.0F && block.blockMaterial != Material.leaves) {
            stressTool(1, itemstack, player);
        }
        return true;
    }
}
