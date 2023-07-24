package levistico.bconstruct.gui.panels;

import levistico.bconstruct.crafting.ContainerToolStation;
import levistico.bconstruct.gui.containers.GUIContainerWithPanels;
import levistico.bconstruct.tools.BTool;
import levistico.bconstruct.tools.ToolStack;
import levistico.bconstruct.utils.Utils;
import net.minecraft.src.ItemStack;
import net.minecraft.src.command.ChatColor;
import net.minecraft.src.helper.Listener;

import java.util.ArrayList;

public class PanelToolStatistics extends PanelText {

    public PanelToolStatistics(GUIContainerWithPanels guiContainer, float zLevel) {
        super(guiContainer, 120, 85, zLevel);
        centerOffsetX = 149;
        centerOffsetY = -41;
        ((ContainerToolStation)guiContainer.inventorySlots).subscribeToOnCraftMatrixChange((stack) ->
            updateToolDescriptionText(stack));
    }

    private void updateToolDescriptionText(ItemStack stack) {
        if(stack == null || stack.getItem() == null || ! (stack.getItem() instanceof BTool)){
            super.clearTextLines();
            return;
        }
        ArrayList<TextWithTooltip> detailLines = new ArrayList<>();
        detailLines.add(new TextWithTooltip(Utils.translateKey((BTool) stack.getItem()), ""));
        int maxDurability = ToolStack.getMaxDurability(stack);
        StringBuilder durabilityText = new StringBuilder();
        durabilityText.append(ChatColor.white).append("\nDurability: ")
                .append(ChatColor.green).append(maxDurability - stack.getMetadata())
                .append(ChatColor.lightGray).append("/")
                .append(ChatColor.green).append(maxDurability);
        detailLines.add(new TextWithTooltip( durabilityText.toString(), ""));
        detailLines.add(new TextWithTooltip( "Attack Damage: " + ChatColor.red + ToolStack.getAttackDamage(stack), ""));
        detailLines.add(new TextWithTooltip( "Harvest Tier: " + Utils.getHarvestTier(ToolStack.getMiningLevel(stack)), ""));
        detailLines.add(new TextWithTooltip( "Mining Speed: " + ChatColor.lightBlue + ToolStack.getMiningSpeed(stack), ""));
        this.setLines(detailLines);
    }
}
