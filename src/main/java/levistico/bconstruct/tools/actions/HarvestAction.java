package levistico.bconstruct.tools.actions;

import levistico.bconstruct.tools.ToolStack;
import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class HarvestAction extends ToolAction {

    /*public ToolAction setHarvestLogic(HarvestLogic harvestLogic) {
        this.harvestLogic = harvestLogic;
        return this;
    }*/

    //can harvest block
    public boolean canHarvestBlock(ItemStack itemstack, HarvestLogic harvestLogic, Block block) {
        //TODO handle extra properties that allow to go above... for a price
        return harvestLogic.howEffectiveOn(block, ToolStack.getMiningLevel(itemstack)).map(i -> i >= 0).orElse(false);
    }
    //get str vs block (we are assuming not broken)
    public float getStrVsBlock(@NotNull ItemStack itemstack, HarvestLogic harvestLogic, Block block) {
        return harvestLogic.getEffectiveness(block.blockMaterial);
    }
    //on block destroyed?
    public boolean onBlockDestroyed(ItemStack itemstack, int i, int j, int k, int l, EntityLiving player) {
        //do cascading things for veinmine and megachop?
        return false;
    }


}
