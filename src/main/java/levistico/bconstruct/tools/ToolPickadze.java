package levistico.bconstruct.tools;

import levistico.bconstruct.gui.SlotArrangements;
import levistico.bconstruct.gui.texture.TexturedToolBitBreakable;
import levistico.bconstruct.gui.texture.TexturedToolBitReliable;
import levistico.bconstruct.parts.BToolParts;
import levistico.bconstruct.tools.actions.HarvestLogic;
import levistico.bconstruct.tools.actions.HarvestLogics;
import levistico.bconstruct.tools.actions.RightClickActions;
import levistico.bconstruct.tools.actions.ToolActions;
import levistico.bconstruct.utils.Pair;
import org.spongepowered.include.com.google.common.collect.ImmutableList;

public class ToolPickadze extends BTool{
    protected ToolPickadze(int id) {
        super(id, "pickadze", HarvestLogics.pickadzeHarvestLogic, false, new Pair<>(1,1));
        composition.add(BToolParts.pickaxeHead);
        texturedParts.add(new TexturedToolBitBreakable(EToolBit.Pickadze_FrontHead, EToolBit.Pickadze_FrontHead_Broken));
        composition.add(BToolParts.shovelHead);
        texturedParts.add(new TexturedToolBitBreakable(EToolBit.Mattock_BackHead, EToolBit.Mattock_BackHead_Broken));
        composition.add(BToolParts.rod);
        texturedParts.add(new TexturedToolBitReliable(EToolBit.BasicHandle));
        renderOrder.addAll(ImmutableList.of(2,0,1)); //handle, pick head, shovel head
        slotArrangement = SlotArrangements.smallT;

        harvestAction = ToolActions.pickadzeMine;
        baseRightClickActions.add(RightClickActions.PATH);
    }
}
