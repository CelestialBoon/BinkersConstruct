package levistico.bconstruct.tools.actions;

import levistico.bconstruct.tools.ToolStack;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;

public class PickadzeHarvestAction extends HarvestAction {
    @Override
    public boolean canHarvestBlock(ItemStack itemstack, HarvestLogic harvestLogic, Block block) {
        return harvestLogic.howEffectiveOn(block, 0).map(i -> i >= 0).orElse(false);
    }
}
