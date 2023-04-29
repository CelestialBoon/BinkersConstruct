package levistico.bconstruct.crafting;

import levistico.bconstruct.texture.GraphicsUtils;
import levistico.bconstruct.utils.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;
import org.lwjgl.opengl.GL11;

public class BGuiButton extends GuiButton
{
    private final Pair<Integer, Integer> textureUV;
    public boolean isSelected;
    public BGuiButton(int id, int xPosition, int yPosition, int width, int height, Pair<Integer, Integer> textureUV) {
        super(id, xPosition, yPosition, width, height, "test");
        this.textureUV = textureUV;
    }
    
    @Override
    public void drawButton(Minecraft minecraft, int i, int j) {
        if (this.visible) {
            FontRenderer fontrenderer = minecraft.fontRenderer;
            GL11.glBindTexture(3553, GraphicsUtils.GUI_ICONS_INDEX);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean isHover = i >= this.xPosition && j >= this.yPosition && i < this.xPosition + this.width && j < this.yPosition + this.height;
            int k = this.getButtonState(isHover);
            if(isSelected) k = 0;
            GUIUtils.drawGUITexture(xPosition, yPosition, zLevel,8+k*2,13);
            //drawing the specific image on top
            GUIUtils.drawGUITexture(xPosition, yPosition, zLevel, textureUV.first, textureUV.second);

            this.mouseDragged(minecraft, i, j);
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
