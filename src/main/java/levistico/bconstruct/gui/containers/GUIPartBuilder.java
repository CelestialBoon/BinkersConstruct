package levistico.bconstruct.gui.containers;

import levistico.bconstruct.crafting.ContainerPartBuilder;
import levistico.bconstruct.crafting.CraftingTileEntity;
import levistico.bconstruct.gui.panels.PanelCrafting;
import levistico.bconstruct.gui.panels.PanelPartBuilderButtons;
import levistico.bconstruct.gui.panels.PanelPlayerInventory;
import net.minecraft.src.InventoryPlayer;

public final class GUIPartBuilder extends GUIContainerWithPanels {

    public GUIPartBuilder(InventoryPlayer inventoryplayer, CraftingTileEntity tileEntity) {
        super(new ContainerPartBuilder(inventoryplayer, tileEntity));
        ContainerPartBuilder container = (ContainerPartBuilder) this.inventorySlots;

        panels.add(new PanelCrafting(this, "Part Builder", zLevel, container.craftingSlots, container.resultSlot));
        panels.add(new PanelPlayerInventory(this, zLevel, container.lowerSlots));
        panels.add(new PanelPartBuilderButtons(this, zLevel, container.buttons));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float renderPartialTicksApparently) {
        //TODO background
    }
}
