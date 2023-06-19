package levistico.bconstruct.tools;

import levistico.bconstruct.gui.SlotArrangements;
import levistico.bconstruct.gui.texture.TexturedToolBitBreakable;
import levistico.bconstruct.gui.texture.TexturedToolBitReliable;
import levistico.bconstruct.parts.BToolParts;
import levistico.bconstruct.parts.EToolPart;
import levistico.bconstruct.tools.actions.HarvestLogic;
import levistico.bconstruct.tools.actions.HarvestLogics;
import levistico.bconstruct.tools.actions.RightClickActions;
import levistico.bconstruct.utils.Pair;
import org.spongepowered.include.com.google.common.collect.ImmutableList;

public class ToolKama extends BTool{
    protected ToolKama(int id) {
        super(id,"kama", HarvestLogics.shearsHarvestLogic, false, new Pair<>(7,0));

        composition.add(BToolParts.blade);
        texturedParts.add(new TexturedToolBitBreakable(EToolBit.Kama_Head, EToolBit.Kama_Head_Broken));
        composition.add(BToolParts.binding);
        texturedParts.add(new TexturedToolBitReliable(EToolBit.Kama_Binding));
        composition.add(BToolParts.rod);
        texturedParts.add(new TexturedToolBitReliable(EToolBit.BasicHandle));
        renderOrder.addAll(ImmutableList.of(2,1,0)); //handle, binding, head
        slotArrangement = SlotArrangements.bend;

        baseRightClickActions.add(RightClickActions.SHEAR);
        baseRightClickActions.add(RightClickActions.REPLANT);
    }
}
