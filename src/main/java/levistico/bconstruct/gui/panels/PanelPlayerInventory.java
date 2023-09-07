package levistico.bconstruct.gui.panels;

import levistico.bconstruct.gui.GUIUtils;
import levistico.bconstruct.gui.containers.GUIContainerWithPanels;
import levistico.bconstruct.gui.texture.TextureUtils;
import net.minecraft.core.player.inventory.slot.Slot;
import net.minecraft.core.player.inventory.slot.SlotCrafting;
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
    public boolean tryKeyTyped(char c, int i, int width, int height, int mouseX, int mouseY) {
        return false;
    }

    @Override
    public void drawTooltip(int topX, int topY, int mouseX, int mouseY, int relativeMouseX, int relativeMouseY) {
        slots.stream().filter(slot -> GUIUtils.isMouseOverSlot(slot, relativeMouseX, relativeMouseY) && slot.hasStack()).forEach(slot -> {
            guiContainer.drawItemTooltip(slot.getStack(), mouseX, mouseY, slot instanceof SlotCrafting);
        });
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int relativeMouseX, int relativeMouseY, int mouseButton) {
        this.guiContainer.doMouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int internalMouseX, int internalMouseY, int button) {
        this.guiContainer.doMouseMovedOrUp(mouseX, mouseY, button);
    }

    @Override
    void drawForegroundLayer() {

    }
}
