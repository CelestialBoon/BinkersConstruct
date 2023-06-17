package levistico.bconstruct.gui;

import levistico.bconstruct.gui.texture.TextureUtils;
import levistico.bconstruct.utils.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;
import org.lwjgl.opengl.GL11;

public class BGuiButton extends GuiButton
{
    private final Pair<Integer, Integer> textureUV;
    public boolean isSelected;

    public BGuiButton(int id, String displayString, int xPosition, int yPosition, int width, int height, Pair<Integer, Integer> textureUV) {
        super(id, xPosition, yPosition, width, height, displayString);
        this.textureUV = textureUV;
    }


    EButtonState getEButtonState(boolean isHovered) {
        if (!this.visible) return EButtonState.Invisible;
        if (!this.enabled) return EButtonState.Disabled;
        if (isSelected) return EButtonState.Pressed;
        if (isHovered) return EButtonState.Hovered;
        return EButtonState.Enabled;
    }

    public void drawButton(Minecraft minecraft, int offsetX, int offsetY, int relativeMouseX, int relativeMouseY) {
        if (this.visible) {
            FontRenderer fontrenderer = minecraft.fontRenderer;
            GL11.glBindTexture(3553, TextureUtils.GUI_ICONS_INDEX);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean isHover = relativeMouseX >= this.xPosition && relativeMouseY >= this.yPosition && relativeMouseX < this.xPosition + this.width && relativeMouseY < this.yPosition + this.height;
            int k = this.getButtonState(isHover);
            if(isSelected) k = 0;
            GUIUtils.drawLargeGUITexture(xPosition + offsetX, yPosition + offsetY, 8+k*2,13, zLevel);
            //drawing the specific image on top
            GUIUtils.drawLargeGUITexture(xPosition + offsetX, yPosition + offsetY, textureUV.first, textureUV.second, zLevel);

            this.mouseDragged(minecraft, relativeMouseX + offsetX, relativeMouseY + offsetY);
//            if (!this.enabled) {
//                this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, -6250336);
//            } else if (isHover) {
//                this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 16777120);
//            } else {
//                this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 14737632);
//            }
        }
    }


}
