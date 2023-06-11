package levistico.bconstruct.mixin;

import levistico.bconstruct.tools.BTool;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ItemStack.class, remap = false)
public class MixinItemStack {

    @Shadow()
    public int itemID;

    /**
     * @author Levistico
     * @reason this fucking method didn't pass the itemstack as parameter; we're doing this to dynamically decide mob damage on custom tools
     */
    @Overwrite()
    public int getDamageVsEntity(Entity entity) {
        Item item = Item.itemsList[this.itemID];
        if(item instanceof BTool) return ((BTool)item).getDamageVsEntity((ItemStack)(Object)this, entity);
        return item.getDamageVsEntity(entity);
    }
    /**
     * @author Levistico
     * @reason this fucking method also didn't allow to dynamically define block harvestability by passing itemstack, and it has been corrected
     */
    @Overwrite()
    public boolean canHarvestBlock(Block block) {
        Item item = Item.itemsList[this.itemID];
        if(item instanceof BTool) return ((BTool)item).canHarvestBlock((ItemStack)(Object)this, block);
        return item.canHarvestBlock(block);
    }

    /**
     * @author Levistico
     * @reason Override of the damage mechanic
     */
    @Overwrite()
    public int getMaxDamage() {
        Item item = Item.itemsList[this.itemID];
        if(item instanceof BTool) return ((BTool)item).getMaxDurability((ItemStack)(Object)this);
        return item.getMaxDamage();
    }
}
