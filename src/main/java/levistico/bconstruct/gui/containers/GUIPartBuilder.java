package levistico.bconstruct.gui.containers;

import net.minecraft.src.InventoryPlayer;

public final class GUIPartBuilder extends GUIContainerWithPanels {

    public GUIPartBuilder(InventoryPlayer inventoryplayer, CraftingTileEntity tileEntity) {
        super(new ContainerPartBuilder(inventoryplayer, tileEntity));
    }
}
