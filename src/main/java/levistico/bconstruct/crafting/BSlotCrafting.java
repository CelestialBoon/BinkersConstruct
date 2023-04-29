package levistico.bconstruct.crafting;

import net.minecraft.src.*;

public class BSlotCrafting extends Slot {
    private IOnCraftResult container;
    private EntityPlayer thePlayer;

    public BSlotCrafting(IOnCraftResult container, EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k) {
        super(iinventory, i, j, k);
        this.thePlayer = entityplayer;
        this.container = container;
    }

    public boolean canPutStackInSlot(ItemStack itemstack) {
        return false;
    }

    public void onPickupFromSlot(ItemStack itemstack) { //TODO add back the achievements
        itemstack.onCrafting(this.thePlayer.worldObj, this.thePlayer);
        container.onCraftResult();
    }
}
