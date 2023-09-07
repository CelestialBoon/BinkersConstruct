package levistico.bconstruct.gui.panels;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.gui.BSlotCustomizable;
import levistico.bconstruct.gui.BSlotCraftingResult;
import levistico.bconstruct.gui.GUIUtils;
import levistico.bconstruct.gui.containers.GUIContainerWithPanels;
import levistico.bconstruct.gui.texture.TextureUtils;
import levistico.bconstruct.utils.Utils;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.core.player.inventory.slot.Slot;
import net.minecraft.core.player.inventory.slot.SlotCrafting;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static levistico.bconstruct.BConstruct.mc;

public class PanelCrafting extends BPanelWithSlots {

    String panelName;
    List<BSlotCustomizable> craftingSlots;
    BSlotCraftingResult resultSlot;
    net.minecraft.client.gui.GuiTextField textBox;

    public PanelCrafting(GUIContainerWithPanels guiContainer, String panelName, float zLevel, List<BSlotCustomizable> craftingSlots, BSlotCraftingResult resultSlot) {
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
            if(!(slot instanceof BSlotCustomizable) || ((BSlotCustomizable)slot).isActive) {
                boolean isMouseOver = getIsMouseOverSlot(slot, relativeMouseX, relativeMouseY);
                guiContainer.drawSlotInventory(slot, isMouseOver);
            }
        }
        if(textBox != null) textBox.drawTextBox();
        unwind();
    }

    @Override
    public void drawTooltip(int topX, int topY, int mouseX, int mouseY, int relativeMouseX, int relativeMouseY) {
        Optional<Slot> mslot = slots.stream().filter(slot -> GUIUtils.isMouseOverSlot(slot, relativeMouseX, relativeMouseY)).findAny();
        if(mslot.isPresent()) {
            Slot slot = mslot.get();
            if(slot.hasStack())
                guiContainer.drawItemTooltip(slot.getStack(), mouseX, mouseY, slot instanceof SlotCrafting);
            else if (slot instanceof BSlotCustomizable) {
                BSlotCustomizable cslot = (BSlotCustomizable) slot;
                if(! Utils.isStringEmpty(cslot.tooltipString))
                    guiContainer.drawTooltipAtMouse(cslot.tooltipString, mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int relativeMouseX, int relativeMouseY, int mouseButton) {
        if(textBox != null) {
            textBox.mouseClicked(relativeMouseX, relativeMouseY, mouseButton);
        }
        this.guiContainer.doMouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int internalMouseX, int internalMouseY, int button) {
        this.guiContainer.doMouseMovedOrUp(mouseX, mouseY, button);
    }

    @Override
    public boolean tryKeyTyped(char c, int i, int width, int height, int mouseX, int mouseY) {
        if(textBox != null && textBox.isFocused) {
            textBox.textboxKeyTyped(c, i);
            return true;
        }
        return false; 
    }

    @Override
    void drawForegroundLayer() {
        BConstruct.mc.fontRenderer.drawString(panelName, 28, 6, 4210752);
        BConstruct.mc.fontRenderer.drawString("Inventory", 8, 72, 4210752);
    }
}
