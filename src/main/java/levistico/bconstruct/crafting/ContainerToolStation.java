package levistico.bconstruct.crafting;

import levistico.bconstruct.gui.BGuiButton;
import levistico.bconstruct.gui.BSlotCustomizable;
import levistico.bconstruct.parts.BToolPart;
import levistico.bconstruct.parts.BToolParts;
import levistico.bconstruct.recipes.BRecipe;
import levistico.bconstruct.recipes.BToolRecipe;
import levistico.bconstruct.tools.BTool;
import levistico.bconstruct.tools.BTools;
import levistico.bconstruct.utils.AcceptRule;
import levistico.bconstruct.utils.Pair;
import levistico.bconstruct.utils.Utils;
import net.minecraft.src.*;
import net.minecraft.src.helper.Listener;

import java.util.ArrayList;

public final class ContainerToolStation extends BContainerWithRecipe {

    public ArrayList<BGuiButton> toolSelectButtons = new ArrayList<>();
    BGuiButton selectedButton;
    public GuiTextField nameField;
    String chosenName = "";
    private final GuiTextField.TextChangeListener nameChangeListener;

    //TODO everything about the arrangement has to be done here first, all the nits and grits, and then this data can be passed to panel generation by having accessible fields
    public ContainerToolStation(InventoryPlayer inventoryplayer, CraftingTileEntity tileEntity) {
       super(inventoryplayer, tileEntity);
        recipe = new BToolRecipe(BTools.toolList.get(0));

        super.resizeCraftingSlots(((BTool)recipe.result).composition.size());

        Listener<GuiButton> listener = button -> {
            BTool tool = BTools.toolList.get(button.id);
            recipe.result = tool;
            super.resizeCraftingSlots(tool.composition.size());
            //TODO customize overall background here
            for(Integer j : Utils.range(0, tool.composition.size())) {
                BSlotCustomizable slot = craftingSlots.get(j);
                BToolPart part = tool.composition.get(j);
                slot.textureUV = new Pair<>(part.baseTextureUV.first, part.baseTextureUV.second+1);
                slot.changePosition(tool.slotArrangement[j]);
                if(part.itemID == BToolParts.rod.itemID) {
                    slot.acceptsOnly = AcceptRule.acceptsOnlyIds(BToolParts.rod.itemID, Item.stick.itemID);
                } else slot.acceptsOnly = AcceptRule.acceptsOnlyIds(part.itemID);
                slot.tooltipString = Utils.translateKey(part);
            }
            if(selectedButton != null) selectedButton.isSelected = false;
            selectedButton = (BGuiButton) button;
            ((BGuiButton)button).isSelected = true;
            onCraftMatrixChanged();
        };

        for(Integer i : Utils.range(0, BTools.toolList.size())) {
            BTool tool = BTools.toolList.get(i);
            BGuiButton button = new BGuiButton(i, Utils.translateKey(tool),(i%5)*20, (i/5)*20, 13, tool.baseTextureUV);
            button.setListener(listener);
            toolSelectButtons.add(button);
        }

        nameChangeListener = guiTextField -> {
            chosenName = guiTextField.getText();
            onCraftMatrixChanged(null);
        };

        listener.listen(toolSelectButtons.get(0));
    }

    @Override
    public void onCraftMatrixChanged(IInventory noUse) {
        ItemStack resultStack = getRecipe().getCraftingResult(tileEntity.inventoryCrafting);
        if(!Utils.isStringEmpty(chosenName) && resultStack != null) {
            BTool.setCustomName(resultStack, chosenName);
        }
        this.craftResult.setInventorySlotContents(0, resultStack);
    }

    public void initializeNameField(GuiTextField textBox) {
        this.nameField = textBox;
        nameField.setMaxStringLength(50);
        nameField.setTextChangeListener(this.nameChangeListener);
    }

    @Override
    public BRecipe getRecipe() {
        if(recipe == null)  {
            recipe = new BToolRecipe(BTools.toolList.get(0));
        }
        return recipe;
    }
}
