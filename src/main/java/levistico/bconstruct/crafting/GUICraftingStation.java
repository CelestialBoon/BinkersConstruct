package levistico.bconstruct.crafting;

import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public final class GUICraftingStation extends GuiContainer {
    public GUICraftingStation(InventoryPlayer inventoryplayer, CraftingTileEntity tileEntity) {
        super(new ContainerCraftingStation(inventoryplayer, tileEntity));
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        this.inventorySlots.onCraftGuiClosed(this.mc.thePlayer);
    }

    protected void drawGuiContainerForegroundLayer() {
        this.fontRenderer.drawString("Crafting", 28, 6, 4210752);
        this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
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
