package levistico.bconstruct.crafting;

import net.minecraft.src.InventoryPlayer;

public final class GUIPartBuilder extends GUIContainerWithButtons {

    public GUIPartBuilder(InventoryPlayer inventoryplayer, CraftingTileEntity tileEntity) {
        super("Part Builder", new ContainerPartBuilder(inventoryplayer, tileEntity));
    }
}
