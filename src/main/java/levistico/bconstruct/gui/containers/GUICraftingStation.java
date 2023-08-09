package levistico.bconstruct.gui.containers;

import levistico.bconstruct.crafting.ContainerCraftingStation;
import levistico.bconstruct.crafting.CraftingTileEntity;
import levistico.bconstruct.gui.panels.IPanel;
import levistico.bconstruct.gui.panels.PanelCrafting;
import levistico.bconstruct.gui.panels.PanelPlayerInventory;
import net.minecraft.core.player.inventory.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public final class GUICraftingStation extends GUIContainerWithPanels {
    public GUICraftingStation(InventoryPlayer inventoryplayer, CraftingTileEntity tileEntity) {
        super(new ContainerCraftingStation(inventoryplayer, tileEntity));
        ContainerCraftingStation container = (ContainerCraftingStation) this.inventorySlots;
        IPanel craftingPanel = new PanelCrafting(this, "Crafting", zLevel, container.craftingSlots, container.resultSlot);
        IPanel inventoryPanel = new PanelPlayerInventory(this, zLevel, container.lowerSlots);
        panels.add(craftingPanel);
        panels.add(inventoryPanel);
    }

    protected void drawGuiContainerBackgroundLayer(float f) {
        int i = this.mc.renderEngine.getTexture("/gui/crafting.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(i);
        int j = (this.width - this.xSize) / 2;
        int k = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
    }
}
