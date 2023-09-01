package levistico.bconstruct.gui.panels;

import levistico.bconstruct.gui.BSlotCustomizable;
import levistico.bconstruct.gui.containers.GUIContainerWithPanels;
import net.minecraft.core.player.inventory.slot.Slot;
import org.lwjgl.input.Keyboard;

import java.util.List;

import static levistico.bconstruct.BConstruct.mc;

public abstract class BPanelWithSlots extends BasePanel {

    List<Slot> slots;
    public BPanelWithSlots(GUIContainerWithPanels guiContainer, int width, int height, float zLevel) {
        super(guiContainer, width, height, zLevel);
    }

   /* void inventoryMouseClicked(int relativeMouseX, int relativeMouseY, int button) {
        if (button == 0 || button == 1 || button == 10) {
            Slot slot = getSlotAtPosition(relativeMouseX, relativeMouseY);
            if (slot == null) return;

            boolean isOffscreen = relativeMouseX < 0 || relativeMouseY < 0 || relativeMouseX > this.width || relativeMouseY > this.height;
            boolean shift = !isOffscreen && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54) || button == 10);
            boolean control = !isOffscreen && (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157));
            if ((Boolean) mc.gameSettings.swapCraftingButtons.value) {
                boolean a = shift;
                shift = control;
                control = a;
            }
            //todo
            //mc.playerController.itemPickUpFromInventory(guiContainer.inventorySlots.windowId, isOffscreen ? -999 : slot.id, button == 10 ? 0 : button, shift, control, mc.thePlayer);
        }
    }*/

    public Slot getSlotAtPosition(int screenWidth, int screenHeight, int mouseX, int mouseY) {
        int topX = getTopX(screenWidth);
        int topY = getTopY(screenHeight);
        if(!isMouseHere(topX, topY, mouseX, mouseY)) return null;
        int relMouseX = getInternalMouseX(screenWidth, mouseX);
        int relMouseY = getInternalMouseY(screenHeight, mouseY);
        return slots.stream().filter(slot -> (!(slot instanceof BSlotCustomizable) || ((BSlotCustomizable) slot).isActive) && getIsMouseOverSlot(slot, relMouseX, relMouseY)).findAny().orElse(null);
    }

    public boolean getIsMouseOverSlot(Slot slot, int relativeMouseX, int relativeMouseY) {
        return relativeMouseX >= slot.xDisplayPosition - 1 && relativeMouseX < slot.xDisplayPosition + 16 + 1 && relativeMouseY >= slot.yDisplayPosition - 1 && relativeMouseY < slot.yDisplayPosition + 16 + 1;
    }
}
