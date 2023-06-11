package levistico.bconstruct.tools;

import levistico.bconstruct.parts.EToolPart;
import levistico.bconstruct.gui.texture.TexturedToolBitBreakable;
import levistico.bconstruct.gui.texture.TexturedToolBitReliable;
import levistico.bconstruct.utils.Pair;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemToolPickaxe;
import net.minecraft.src.Material;

public class ToolPickaxe extends BTool {
    static Material[] effectiveMaterials = {Material.rock, Material.iron, Material.piston, Material.moss, Material.ice, Material.glass, Material.circuits};

    public ToolPickaxe(int id) {
        super(id,"Pickaxe", "pickaxe", effectiveMaterials, new Pair<>(4,0));
        composition.add(EToolPart.pickaxeHead);
        texturedParts.add(new TexturedToolBitBreakable(EToolBit.Pickaxe_Head, EToolBit.Pickaxe_Head_Broken));
        composition.add(EToolPart.binding);
        texturedParts.add(new TexturedToolBitReliable(EToolBit.Pickaxe_Binding));
        composition.add(EToolPart.rod);
        texturedParts.add(new TexturedToolBitReliable(EToolBit.BasicHandle));
        renderOrder.add(2); //rod
        renderOrder.add(1); //binding
        renderOrder.add(0); //head
    }

    @Override
    public boolean canHarvestBlock(ItemStack itemstack, Block block) {
        if (isSilkTouch(itemstack)) return true;
        Integer miningLevel = ItemToolPickaxe.miningLevels.get(block);
        if (miningLevel != null) return getMiningLevel(itemstack) >= miningLevel;
        else return super.canHarvestBlock(itemstack, block);
    }
}
