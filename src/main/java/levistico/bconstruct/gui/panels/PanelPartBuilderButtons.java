package levistico.bconstruct.gui.panels;

import levistico.bconstruct.gui.BGuiButton;
import levistico.bconstruct.gui.containers.GUIContainerWithPanels;

import java.util.List;

public class PanelPartBuilderButtons extends BPanelWithButtons {
    public PanelPartBuilderButtons(GUIContainerWithPanels guiContainer, float zLevel, List<BGuiButton> buttons) {
        super(guiContainer, 100, 166, zLevel, buttons);
        centerOffsetX = -(100 + 176) /2;
    }

    @Override
    public void drawPanel(int screenWidth, int screenHeight, int mouseX, int mouseY) {
        int topX = getTopX(screenWidth);
        int topY = getTopY(screenHeight);
        int mouseRelX = getInternalMouseX(screenWidth, mouseX);
        int mouseRelY = getInternalMouseY(screenHeight, mouseY);
        //TODO draw cool bg stuff here

        super.drawButtons(topX, topY, mouseRelX, mouseRelY);
    }



    //drawTooltip is handled by super
    //mouseClicked is handled by super
    @Override
    void drawForegroundLayer() {

    }

}
