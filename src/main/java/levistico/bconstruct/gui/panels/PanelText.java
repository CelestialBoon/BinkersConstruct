package levistico.bconstruct.gui.panels;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.gui.containers.GUIContainerWithPanels;
import levistico.bconstruct.utils.Utils;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;

public class PanelText extends BasePanel{

    ArrayList<TextWithTooltip> textLines;
    int textOffsetX;
    int textOffsetY;
    int yBottomBorder;
    int scroll;
    int maxScroll;
    final int textHeight = 11;

    public PanelText(GUIContainerWithPanels guiContainer, int width, int height, float zLevel) {
        super(guiContainer, width, height, zLevel);
        textOffsetX = 5;
        textOffsetY = 5;
        yBottomBorder = 5;
        this.textLines = new ArrayList<>();
    }

    public PanelText setLines(ArrayList<TextWithTooltip> lines) {
        this.textLines = lines;
        calculatemaxScroll();
        return this;
    }

    public PanelText addLine(TextWithTooltip line) {
        this.textLines.add(line);
        calculatemaxScroll();
        return this;
    }

    public void clearTextLines(){
        this.textLines.clear();
    }

    void calculatemaxScroll() {
        maxScroll = Math.min(0, height-textOffsetY-yBottomBorder - textLines.size()*textHeight);
    }


    @Override
    public void drawPanel(int screenWidth, int screenHeight, int mouseX, int mouseY) {
        int topX = getTopX(screenWidth);
        int topY = getTopY(screenHeight);
        if(isMouseHere(topX, topY, mouseX, mouseY)) {
            scroll(Mouse.getDWheel());
        }
        guiContainer.drawRect(topX, topY, width, height, 0x80202020);
        int len = textLines.size();
        //this is where the fun lives
        for(int i = 0; i<len; i++) {
            int y = textOffsetY+3+scroll+i*textHeight; //the 3 is for the tooltip to line up
            if(y<0 || y>height-textHeight) continue;
            BConstruct.mc.fontRenderer.drawString(textLines.get(i).text, topX+ textOffsetX, topY+y, 16777215);
        }
    }

    private void scroll(int dWheel) {
        if(dWheel == 0) return;
        else if (dWheel > 0) scroll += textHeight;
        else scroll -= textHeight;
        scroll = Utils.clamp(maxScroll, scroll, 0);
    }

    @Override
    public void drawTooltip(int topX, int topY, int mouseX, int mouseY, int relativeMouseX, int relativeMouseY) {
        int textLineHovered = (relativeMouseY - textOffsetY - scroll) / textHeight;
        if(textLineHovered >= 0 && textLineHovered < textLines.size() && !Utils.isStringEmpty(textLines.get(textLineHovered).tooltip)) {
            guiContainer.drawTooltipAtMouse(textLines.get(textLineHovered).tooltip, mouseX, mouseY);
        }
    }

    @Override
    void drawForegroundLayer() {
        //this is where the whole panel frame goes, to cover the overflowing text!
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int relativeMouseX, int relativeMouseY, int mouseButton) {
        //nothing to click here
    }

    @Override
    public boolean keyTyped(char c, int i, int mouseX, int mouseY) {
        return false; //nothing to be typed here
    }
}
