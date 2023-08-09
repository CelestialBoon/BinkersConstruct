package levistico.bconstruct.tools;

import levistico.bconstruct.gui.SlotArrangements;
import levistico.bconstruct.parts.BToolParts;
import levistico.bconstruct.parts.EToolPart;
import levistico.bconstruct.gui.texture.TexturedToolBitBreakable;
import levistico.bconstruct.gui.texture.TexturedToolBitReliable;
import levistico.bconstruct.tools.actions.HarvestLogics;
import levistico.bconstruct.tools.actions.ToolActions;
import levistico.bconstruct.utils.Pair;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Item;
import net.minecraft.core.block.material.Material;

public class ToolPickaxe extends BTool {

    public ToolPickaxe(int id) {
        super(id,"pickaxe", HarvestLogics.pickaxeHarvestLogic, false, new Pair<>(4,0));
        composition.add(BToolParts.pickaxeHead);
        texturedParts.add(new TexturedToolBitBreakable(EToolBit.Pickaxe_Head, EToolBit.Pickaxe_Head_Broken));
        composition.add(BToolParts.binding);
        texturedParts.add(new TexturedToolBitReliable(EToolBit.Pickaxe_Binding));
        composition.add(BToolParts.rod);
        texturedParts.add(new TexturedToolBitReliable(EToolBit.BasicHandle));
        renderOrder.add(2); //rod
        renderOrder.add(1); //binding
        renderOrder.add(0); //head
        slotArrangement = SlotArrangements.line;
    }
}
