package levistico.bconstruct.crafting;

import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class BSlotActivatable extends Slot {
    public boolean isActive;
    public BSlotActivatable(IInventory iinventory, int id, boolean isActive, int x, int y) {
        super(iinventory, id, x, y);
        this.isActive = isActive;
    }
    @Override
    public boolean canPutStackInSlot(ItemStack itemstack) {
        return isActive;
    }
}
