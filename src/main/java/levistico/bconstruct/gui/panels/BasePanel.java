package levistico.bconstruct.gui.panels;

import levistico.bconstruct.gui.GUIUtils;
import levistico.bconstruct.gui.containers.GUIContainerWithPanels;
import org.lwjgl.opengl.GL11;

public abstract class BasePanel implements IPanel {
    public int width;
    public int height;
    public int textureU = 0;
    public int textureV = 0;
    public int centerOffsetX;
    public int centerOffsetY;
    public float zLevel;
    public GUIContainerWithPanels guiContainer;
    public BasePanel(GUIContainerWithPanels guiContainer, int width, int height, float zLevel) {
        this.guiContainer = guiContainer;
        this.width = width;
        this.height = height;
        this.zLevel = zLevel;
    }

    abstract void drawForegroundLayer();

    void wind(int topX, int topY) {
        GL11.glPushMatrix();
        GL11.glTranslatef(topX, topY, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GUIUtils.GL_BLOCK_ITEM_MAGIC_NUMBER);
    }
    void unwind() {
        GL11.glDisable(GUIUtils.GL_BLOCK_ITEM_MAGIC_NUMBER);
        drawForegroundLayer();
        GL11.glPopMatrix();
    }

    public BasePanel setOffsetX(int offsetX) {
        this.centerOffsetX = offsetX;
        return this;
    }
    public BasePanel setOffsetY(int offsetY) {
        this.centerOffsetY = offsetY;
        return this;
    }

    public void tryDrawTooltip(int screenWidth, int screenHeight, int mouseX, int mouseY) {
        int topX = getTopX(screenWidth);
        int topY = getTopY(screenHeight);
        if(isMouseHere(topX, topY, mouseX, mouseY)) {
            drawTooltip(topX, topY, mouseX, mouseY, getInternalMouseX(screenWidth, mouseX), getInternalMouseY(screenHeight, mouseY));
        }
    }

    public boolean tryMouseClicked(int screenWidth, int screenHeight, int mouseX, int mouseY, int button) {
        int topX = getTopX(screenWidth);
        int topY = getTopY(screenHeight);
        if(isMouseHere(topX, topY, mouseX, mouseY)) {
            mouseClicked(mouseX, mouseY, getInternalMouseX(screenWidth, mouseX), getInternalMouseY(screenHeight, mouseY), button);
            return true;
        }
        return false;
    }
    public boolean tryMouseMovedOrUp(int screenWidth, int screenHeight, int mouseX, int mouseY, int button) {
        int topX = getTopX(screenWidth);
        int topY = getTopY(screenHeight);
        if(isMouseHere(topX, topY, mouseX, mouseY)) {
            mouseMovedOrUp(mouseX, mouseY, getInternalMouseX(screenWidth, mouseX), getInternalMouseY(screenHeight, mouseY), button);
            return true;
        }
        return false;
    }

    protected abstract void drawTooltip(int topX, int topY, int mouseX, int mouseY, int relativeMouseX, int relativeMouseY);
    protected abstract void mouseClicked(int mouseX, int mouseY, int relativeMouseX, int relativeMouseY, int mouseButton);
    protected abstract void mouseMovedOrUp(int mouseX, int mouseY, int internalMouseX, int internalMouseY, int button);

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

    boolean isMouseHere(int topX, int topY, int mouseX, int mouseY) {
        return mouseX >= topX && mouseX <= topX + width && mouseY >= topY && mouseY <= topY + height;
    }
}
