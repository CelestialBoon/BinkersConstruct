package levistico.bconstruct.gui.panels;

import levistico.bconstruct.gui.GUIUtils;
import levistico.bconstruct.gui.texture.TextureUtils;
import net.minecraft.src.Slot;
import net.minecraft.src.SlotCrafting;
import org.lwjgl.opengl.GL11;

import java.util.List;


import static levistico.bconstruct.BConstruct.mc;

public class PanelPlayerInventory extends BasePanel {
    List<Slot> inventorySlots;
    @Override
    public void drawPanel(int screenWidth, int screenHeight, int mouseX, int mouseY) {
        int topX = getTopX(screenWidth);
        int topY = getTopY(screenHeight);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(TextureUtils.GUI_INVENTORY_INDEX);
        container.drawTexturedModalRect(topX, topY, 0, 0, this.width, this.height);
    }

    @Override
    public void drawTooltip(int screenWidth, int screenHeight, int mouseX, int mouseY) {
        int mouseRelX = getInternalMouseX(screenWidth, mouseX);
        int mouseRelY = getInternalMouseY(screenHeight, mouseY);
        inventorySlots.stream().filter(slot -> GUIUtils.isMouseOverSlot(slot, mouseRelX, mouseRelY) && slot.hasStack()).forEach(slot -> {
            container.drawItemTooltip(slot.getStack(), mouseX, mouseY, slot.discovered, slot instanceof SlotCrafting);
        });
    }

    @Override
    public void mouseClicked(int screenWidth, int screenHeight, int mouseX, int mouseY, int button) {
        // this is covered in GuiContainer::mouseClicked
    }

    @Override
    public boolean keyTyped(char c, int i) {
        return false; //TODO later this can capture number hotkeys
    }
}
