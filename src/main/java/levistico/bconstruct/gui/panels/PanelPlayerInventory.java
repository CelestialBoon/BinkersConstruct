package levistico.bconstruct.gui.panels;

import levistico.bconstruct.gui.GUIUtils;
import levistico.bconstruct.gui.containers.GUIContainerWithPanels;
import levistico.bconstruct.gui.texture.TextureUtils;
import net.minecraft.src.Slot;
import net.minecraft.src.SlotCrafting;
import org.lwjgl.opengl.GL11;

import java.util.List;


import static levistico.bconstruct.BConstruct.mc;

public class PanelPlayerInventory extends BPanelWithSlots {

    public PanelPlayerInventory(GUIContainerWithPanels guiContainer, float zLevel, List<Slot> slots) {
        super(guiContainer,176, 83, zLevel);
        textureV = 83;
        centerOffsetY = 41;
        this.slots = slots;
    }

    @Override
    public void drawPanel(int screenWidth, int screenHeight, int mouseX, int mouseY) {
        int topX = getTopX(screenWidth);
        int topY = getTopY(screenHeight);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(TextureUtils.GUI_BASE_CRAFTING_INDEX);
        guiContainer.drawTexturedModalRect(topX, topY, textureU, textureV, this.width, this.height);

        wind(topX, topY);
        for(Slot slot : slots) {
            boolean isMouseOver = getIsMouseOverSlot(slot, getInternalMouseX(screenWidth, mouseX), getInternalMouseY(screenHeight, mouseY));
            guiContainer.drawSlotInventory(slot, isMouseOver);
        }
        unwind();
    }

    @Override
    public void drawTooltip(int screenWidth, int screenHeight, int mouseX, int mouseY) {
        int mouseRelX = getInternalMouseX(screenWidth, mouseX);
        int mouseRelY = getInternalMouseY(screenHeight, mouseY);
        slots.stream().filter(slot -> GUIUtils.isMouseOverSlot(slot, mouseRelX, mouseRelY) && slot.hasStack()).forEach(slot -> {
            guiContainer.drawItemTooltip(slot.getStack(), mouseX, mouseY, slot.discovered, slot instanceof SlotCrafting);
        });
    }

    @Override
    public void mouseClicked(int screenWidth, int screenHeight, int mouseX, int mouseY, int button) {
        int relativeMouseX = getInternalMouseX(screenWidth, mouseX);
        int relativeMouseY = getInternalMouseY(screenHeight, mouseY);
        super.inventoryMouseClicked(relativeMouseX, relativeMouseY, button);
    }

    @Override
    public boolean keyTyped(char c, int i) {
        return false; //TODO later this can capture number hotkeys
    }

    @Override
    void drawForegroundLayer() {

    }
}
