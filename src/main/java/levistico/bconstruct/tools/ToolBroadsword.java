package levistico.bconstruct.tools;

import levistico.bconstruct.gui.SlotArrangements;
import levistico.bconstruct.parts.BToolParts;
import levistico.bconstruct.parts.EToolPart;
import levistico.bconstruct.gui.texture.TexturedToolBitBreakable;
import levistico.bconstruct.gui.texture.TexturedToolBitReliable;
import levistico.bconstruct.tools.actions.HarvestLogics;
import levistico.bconstruct.utils.Pair;
import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemStack;

public final class ToolBroadsword extends BTool {
    public ToolBroadsword(int id) {
        super(id, "sword", HarvestLogics.swordHarvestLogic, true, new Pair<>(0,1));
        composition.add(BToolParts.blade);
        texturedParts.add(new TexturedToolBitBreakable(EToolBit.Broadsword_Blade, EToolBit.Broadsword_Blade_Broken));
        composition.add(BToolParts.largeGuard);
        texturedParts.add(new TexturedToolBitReliable(EToolBit.Broadsword_Guard));
        composition.add(BToolParts.rod);
        texturedParts.add(new TexturedToolBitReliable(EToolBit.Broadsword_Handle));
        renderOrder.add(2); //handle
        renderOrder.add(0); //blade
        renderOrder.add(1); //guard
        slotArrangement = SlotArrangements.line;
    }
}
