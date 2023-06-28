package levistico.bconstruct.tools;

import levistico.bconstruct.gui.SlotArrangements;
import levistico.bconstruct.parts.BToolParts;
import levistico.bconstruct.parts.EToolPart;
import levistico.bconstruct.gui.texture.TexturedToolBitBreakable;
import levistico.bconstruct.gui.texture.TexturedToolBitReliable;
import levistico.bconstruct.tools.actions.HarvestLogics;
import levistico.bconstruct.tools.actions.ToolActions;
import levistico.bconstruct.utils.Pair;
import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;

public class ToolHatchet extends BTool {

    public ToolHatchet(int id) {
        super(id,"hatchet", HarvestLogics.hatchetHarvestLogic, false, new Pair<>(6,0));
        composition.add(BToolParts.axeHead);
        texturedParts.add(new TexturedToolBitBreakable(EToolBit.Hatchet_Head, EToolBit.Hatchet_Head_Broken));
        composition.add(BToolParts.binding);
        texturedParts.add(new TexturedToolBitReliable(EToolBit.Hatchet_Binding));
        composition.add(BToolParts.rod);
        texturedParts.add(new TexturedToolBitReliable(EToolBit.BasicHandle));
        renderOrder.add(2); //handle
        renderOrder.add(1); //binding
        renderOrder.add(0); //head
        slotArrangement = SlotArrangements.line;
    }
}
