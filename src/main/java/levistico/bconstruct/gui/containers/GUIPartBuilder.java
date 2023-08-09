package levistico.bconstruct.gui.containers;

import levistico.bconstruct.crafting.ContainerPartBuilder;
import levistico.bconstruct.crafting.CraftingTileEntity;
import levistico.bconstruct.gui.panels.*;
import net.minecraft.core.player.inventory.InventoryPlayer;

import java.util.ArrayList;

public final class GUIPartBuilder extends GUIContainerWithPanels {

    public GUIPartBuilder(InventoryPlayer inventoryplayer, CraftingTileEntity tileEntity) {
        super(new ContainerPartBuilder(inventoryplayer, tileEntity));
        ContainerPartBuilder container = (ContainerPartBuilder) this.inventorySlots;

//        ArrayList<TextWithTooltip> lines = new ArrayList<>();
//        for(Integer i : Utils.range(0, 30)) {
//            lines.add(new TextWithTooltip("text"+i, "tooltip"+i));
//        }

        panels.add(new PanelCrafting(this, "Part Builder", zLevel, container.craftingSlots, container.resultSlot));
        panels.add(new PanelPlayerInventory(this, zLevel, container.lowerSlots));
        panels.add(new PanelPartBuilderButtons(this, zLevel, container.buttons));
//        panels.add(new PanelText(this, 100, 100, zLevel).setLines(lines)
//                .setOffsetX((176+100)/2));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float renderPartialTicksApparently) {
        //TODO background
    }
}
