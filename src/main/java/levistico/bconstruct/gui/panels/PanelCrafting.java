package levistico.bconstruct.gui.panels;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.gui.BSlotActivatable;
import levistico.bconstruct.gui.BSlotCrafting;
import levistico.bconstruct.gui.GUIUtils;
import levistico.bconstruct.gui.containers.GUIContainerWithPanels;
import levistico.bconstruct.gui.texture.TextureUtils;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.Slot;
import net.minecraft.src.SlotCrafting;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import static levistico.bconstruct.BConstruct.mc;

public class PanelCrafting extends BPanelWithSlots {

    String panelName;
    List<BSlotActivatable> craftingSlots;
    BSlotCrafting resultSlot;
    GuiTextField textBox;

    public PanelCrafting(GUIContainerWithPanels guiContainer, String panelName, float zLevel, List<BSlotActivatable> craftingSlots, BSlotCrafting resultSlot) {
        super(guiContainer,176, 83, zLevel);
        centerOffsetY = -42;
        this.craftingSlots = craftingSlots;
        this.resultSlot = resultSlot;
        this.panelName = panelName;
        slots = new ArrayList<>(craftingSlots);
        slots.add(resultSlot);
    }
    public PanelCrafting addTextBox(GuiTextField textBox) {
        this.textBox = textBox;
        return this;
    }

    @Override
    public void drawPanel(int screenWidth, int screenHeight, int mouseX, int mouseY) {
        int topX = getTopX(screenWidth);
        int topY = getTopY(screenHeight);
        int relativeMouseX = getInternalMouseX(screenWidth, mouseX);
        int relativeMouseY = getInternalMouseY(screenHeight, mouseY);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(TextureUtils.GUI_BASE_CRAFTING_INDEX);
        guiContainer.drawTexturedModalRect(topX, topY, textureU, textureV, this.width, this.height);

        wind(topX, topY);
        for(Slot slot : slots) {
            if(!(slot instanceof BSlotActivatable) || ((BSlotActivatable)slot).isActive) {
                boolean isMouseOver = getIsMouseOverSlot(slot, relativeMouseX, relativeMouseY);
                guiContainer.drawSlotInventory(slot, isMouseOver);
            }
        }
        if(textBox != null) textBox.drawTextBox();
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
        if(textBox != null) {
            textBox.mouseClicked(relativeMouseX, relativeMouseY, button);
        }
    }

    @Override
    public boolean keyTyped(char c, int i) {
        if(textBox != null && textBox.isFocused) {
            textBox.textboxKeyTyped(c, i);
            return true;
        }
        {} //TODO later this can capture number hotkeys
        return false; 
    }

    @Override
    void drawForegroundLayer() {
        BConstruct.mc.fontRenderer.drawString(panelName, 28, 6, 4210752);
        BConstruct.mc.fontRenderer.drawString("Inventory", 8, 72, 4210752);
    }
}
