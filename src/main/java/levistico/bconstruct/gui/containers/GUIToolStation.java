package levistico.bconstruct.gui.containers;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.crafting.ContainerToolStation;
import levistico.bconstruct.crafting.CraftingTileEntity;
import levistico.bconstruct.gui.panels.PanelCrafting;
import levistico.bconstruct.gui.panels.PanelPartBuilderButtons;
import levistico.bconstruct.gui.panels.PanelPlayerInventory;
import levistico.bconstruct.gui.panels.PanelToolStationButtons;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.InventoryPlayer;

public final class GUIToolStation extends GUIContainerWithPanels {

    public GUIToolStation(InventoryPlayer inventoryplayer, CraftingTileEntity tileEntity) {
        super(new ContainerToolStation(inventoryplayer, tileEntity));
        ContainerToolStation container = (ContainerToolStation) this.inventorySlots;

        GuiTextField textBox = new GuiTextField(this, BConstruct.mc.fontRenderer, 90, 6, 80, 10, "");
        container.initializeNameField(textBox);
        panels.add(new PanelCrafting(this, "Tool Station", zLevel, container.craftingSlots, container.resultSlot).addTextBox(textBox));
        panels.add(new PanelPlayerInventory(this, zLevel, container.lowerSlots));
        panels.add(new PanelToolStationButtons(this, zLevel, container.toolSelectButtons));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float renderPartialTicksApparently) {
        //TODO background
    }
}
