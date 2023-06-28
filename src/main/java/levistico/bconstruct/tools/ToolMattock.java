package levistico.bconstruct.tools;

import levistico.bconstruct.gui.SlotArrangements;
import levistico.bconstruct.gui.texture.TexturedToolBitBreakable;
import levistico.bconstruct.gui.texture.TexturedToolBitReliable;
import levistico.bconstruct.parts.BToolParts;
import levistico.bconstruct.parts.EToolPart;
import levistico.bconstruct.tools.actions.HarvestLogics;
import levistico.bconstruct.tools.actions.RightClickActions;
import levistico.bconstruct.utils.Pair;
import org.spongepowered.include.com.google.common.collect.ImmutableList;

import java.util.Collections;
import java.util.List;

public class ToolMattock extends BTool {
    public ToolMattock(int id) {
        super(id,"mattock", HarvestLogics.mattockHarvestLogic, false, new Pair<>(8,0));
        composition.add(BToolParts.axeHead);
        texturedParts.add(new TexturedToolBitBreakable(EToolBit.Mattock_FrontHead, EToolBit.Mattock_FrontHead_Broken));
        composition.add(BToolParts.shovelHead);
        texturedParts.add(new TexturedToolBitBreakable(EToolBit.Mattock_BackHead, EToolBit.Mattock_BackHead_Broken));
        composition.add(BToolParts.rod);
        texturedParts.add(new TexturedToolBitReliable(EToolBit.BasicHandle));
        renderOrder.addAll(ImmutableList.of(2,0,1)); //handle, axe head, shovel head
        slotArrangement = SlotArrangements.smallT;

        baseRightClickActions.add(RightClickActions.TILL);
    }
}
