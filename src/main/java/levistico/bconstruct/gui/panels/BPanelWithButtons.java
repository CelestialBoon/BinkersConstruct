package levistico.bconstruct.gui.panels;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.gui.BGuiButton;
import levistico.bconstruct.gui.GUIUtils;
import levistico.bconstruct.gui.containers.GUIContainerWithPanels;
import levistico.bconstruct.utils.Utils;
import net.minecraft.core.sound.SoundType;

import java.util.List;

public abstract class BPanelWithButtons extends BasePanel {

    List<BGuiButton> buttons;
    public BPanelWithButtons(GUIContainerWithPanels guiContainer, int width, int height, float zLevel, List<BGuiButton> buttons) {
        super(guiContainer, width, height, zLevel);
        this.buttons = buttons;
    }

    public void mouseClicked(int mouseX, int mouseY, int relativeMouseX, int relativeMouseY, int mouseButton) {
        clickButtons(relativeMouseX, relativeMouseY, mouseButton);
    }

    void drawButtons(int topX, int topY, int mouseRelX, int mouseRelY) {
        for(BGuiButton button : buttons) {
            button.drawButton(BConstruct.mc, topX, topY, mouseRelX, mouseRelY);
        }
    }
    
    void clickButtons(int relativeMouseX, int relativeMouseY, int mouseButton) {
        if (mouseButton == 0) {
            buttons.stream().filter(b -> b.mousePressed(BConstruct.mc, relativeMouseX, relativeMouseY)).findAny().ifPresent(guiButton -> {
                BConstruct.mc.sndManager.playSound("random.click", SoundType.GUI_SOUNDS, 1.0F, 1.0F);
                if (guiButton.listener != null) {
                    guiButton.listener.listen(guiButton);
                } else {
                    this.actionPerformed(guiButton);
                }
            });
        }
    }

    public void drawTooltip(int topX, int topY, int mouseX, int mouseY, int relativeMouseX, int relativeMouseY) {
        buttons.stream().filter(b -> b.isHovered(relativeMouseX, relativeMouseY)).findAny().ifPresent(b -> {
            if(! Utils.isStringEmpty(b.displayString))
                GUIUtils.tooltip.render(b.displayString, topX, topY, 0, -15);
        });
    }

    void actionPerformed(BGuiButton guiButton) {
    }
}
