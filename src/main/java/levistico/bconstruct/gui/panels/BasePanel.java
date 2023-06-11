package levistico.bconstruct.gui.panels;

import levistico.bconstruct.gui.containers.GUIContainerWithPanels;

public abstract class BasePanel implements IPanel {
    public int width;
    public int height;
    public int centerOffsetX;
    public int centerOffsetY;

    public GUIContainerWithPanels container;

    int getTopX(int screenWidth) {
        return (screenWidth - width)/2 + centerOffsetX;
    }
    int getTopY(int screenHeight) {
        return (screenHeight - height)/2 + centerOffsetY;
    }
    int getInternalMouseX(int screenWidth, int mouseX) {
        return mouseX - getTopX(screenWidth);
    }
    int getInternalMouseY(int screenHeight, int mouseY) {
        return mouseY - getTopY(screenHeight);
    }
}
