package levistico.bconstruct.gui.containers;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.crafting.ContainerToolStation;
import levistico.bconstruct.crafting.CraftingTileEntity;
import levistico.bconstruct.gui.panels.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.core.player.inventory.InventoryPlayer;

public final class GUIToolStation extends GUIContainerWithPanels {

    public GUIToolStation(InventoryPlayer inventoryplayer, CraftingTileEntity tileEntity) {
        super(new ContainerToolStation(inventoryplayer, tileEntity));
        ContainerToolStation container = (ContainerToolStation) this.inventorySlots;

        GuiTextField textBox = new GuiTextField(this, BConstruct.mc.fontRenderer, 90, 6, 80, 10, "", "");
        container.initializeNameField(textBox);
        panels.add(new PanelCrafting(this, "Tool Station", zLevel, container.craftingSlots, container.resultSlot).addTextBox(textBox));
        panels.add(new PanelPlayerInventory(this, zLevel, container.lowerSlots));
        panels.add(new PanelToolStationButtons(this, zLevel, container.toolSelectButtons));
        panels.add(new PanelToolStatistics(this, zLevel));
        container.onCraftMatrixChanged();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float renderPartialTicksApparently) {
        //TODO background
    }
}
