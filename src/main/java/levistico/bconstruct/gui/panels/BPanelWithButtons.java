package levistico.bconstruct.gui.panels;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.gui.BGuiButton;
import levistico.bconstruct.gui.containers.GUIContainerWithPanels;
import levistico.bconstruct.utils.Utils;

import java.util.List;

public abstract class BPanelWithButtons extends BasePanel {

    List<BGuiButton> buttons;
    public BPanelWithButtons(GUIContainerWithPanels guiContainer, int width, int height, float zLevel, List<BGuiButton> buttons) {
        super(guiContainer, width, height, zLevel);
        this.buttons = buttons;
    }

    public void mouseClicked(int screenWidth, int screenHeight, int mouseX, int mouseY, int mouseButton) {
        clickButtons(getInternalMouseX(screenWidth, mouseX), getInternalMouseY(screenHeight, mouseY), mouseButton);
    }

    void drawButtons(int topX, int topY, int mouseRelX, int mouseRelY) {
        for(BGuiButton button : buttons) {
            button.drawButton(BConstruct.mc, topX, topY, mouseRelX, mouseRelY);
        }
    }
    
    void clickButtons(int relativeMouseX, int relativeMouseY, int mouseButton) {
        if (mouseButton == 0) {
            buttons.stream().filter(b -> b.mousePressed(BConstruct.mc, relativeMouseX, relativeMouseY)).findAny().ifPresent(guiButton -> {
                BConstruct.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                if (guiButton.listener != null) {
                    guiButton.listener.listen(guiButton);
                } else {
                    this.actionPerformed(guiButton);
                }
            });
        }
    }

    public void drawTooltip(int screenWidth, int screenHeight, int mouseX, int mouseY) {
        int topX = getTopX(screenWidth);
        int topY = getTopY(screenHeight);
        int relativeMouseX = getInternalMouseX(screenWidth, mouseX);
        int relativeMouseY = getInternalMouseY(screenHeight, mouseY);
        buttons.stream().filter(b -> b.isHovered(relativeMouseX, relativeMouseY)).findAny().ifPresent(b -> {
            if(! Utils.isStringEmpty(b.displayString))
                guiContainer.drawTooltip(b.displayString, topX, topY, 0, -15, false);
        });
    }

    void actionPerformed(BGuiButton guiButton) {
    }
}
