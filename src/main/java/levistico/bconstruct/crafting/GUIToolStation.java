package levistico.bconstruct.crafting;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.InventoryPlayer;

public final class GUIToolStation extends GUIContainerWithButtons {

    public GUIToolStation(InventoryPlayer inventoryplayer, CraftingTileEntity tileEntity) {
        super("Tool Station", new ContainerToolStation(inventoryplayer, tileEntity));
        ContainerToolStation container = (ContainerToolStation) this.inventorySlots;

        textBox = new GuiTextField(this, Minecraft.getMinecraft().fontRenderer, 90, 6, 80, 10, "");
        container.nameField = textBox;
        container.initializeNameField();
    }
}
