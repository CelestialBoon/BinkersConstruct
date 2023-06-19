package levistico.bconstruct.tools.modifiers;

import levistico.bconstruct.tools.stats.ToolStats;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;

import java.util.List;

public abstract class Modifier {
//    public final String name;
//    public final String translateKey;
    public final int maxLevel;
    public Modifier(int maxLevel) {
//        this.name = name;
//        this.translateKey = String.format("modifier.%s.%s", BConstruct.MOD_ID, name);
        this.maxLevel = maxLevel;
    }

    //Adds raw stats to the tool. Called whenever tool stats are rebuilt.
    public void addToolStats(ToolStats toolStats) {}
    //Called when the tool is damaged. Can be used to cancel, decrease, or increase the damage.
    public float getConditionalReinforced(Block block, int miningEase) { return 1f;}
    //Called when the tool is repair. Can be used to decrease, increase, or cancel the repair.
    public float getRepairFactor(int level) {return 1;}
    //Called on entity or block loot to allow modifying loot
    public List<ItemStack> processLoot(List<ItemStack> loot) {return loot;}
    //TODO see examples for the next ones
    public void beforeBlockUse() {}
    public void afterBlockUse() {}
    public void beforeEntityUse() {}
    public void afterEntityUse() {}
    public float beforeEntityHit(int knockback) {return knockback;} //for knockback
    public void afterEntityHit() {}
    public float getEntityDamage(float damage) {return damage;} //for attack damage
    public boolean canHarvestBlock(final Block block) {return false;}
    public float getStrVsBlock(final Block block, float str) {return str;}
    public boolean onToolUse() {return false;}
    public boolean onToolRightClick() {return false;}

}
