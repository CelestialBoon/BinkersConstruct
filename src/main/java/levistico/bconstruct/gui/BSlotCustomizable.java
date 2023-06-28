package levistico.bconstruct.gui;

import levistico.bconstruct.utils.AcceptRule;
import levistico.bconstruct.utils.Pair;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class BSlotCustomizable extends Slot {
    public boolean isActive;
    public Pair<Integer, Integer> textureUV;
    public String tooltipString;
    public AcceptRule acceptsOnly;


    public BSlotCustomizable(IInventory iinventory, int id, boolean isActive, int x, int y) {
        super(iinventory, id, x, y);
        this.isActive = isActive;
    }
    @Override
    public boolean canPutStackInSlot(ItemStack itemstack) {
        return isActive && (acceptsOnly == null || acceptsOnly.accepts(itemstack));
    }
    public void changePosition(Pair<Integer, Integer> newPos) {
        xDisplayPosition = newPos.first;
        yDisplayPosition = newPos.second;
    }
}
