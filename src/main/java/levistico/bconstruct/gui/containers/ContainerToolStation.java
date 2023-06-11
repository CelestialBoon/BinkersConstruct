package levistico.bconstruct.gui.containers;

import levistico.bconstruct.gui.BGuiButton;
import levistico.bconstruct.gui.BSlotActivatable;
import levistico.bconstruct.gui.BSlotCrafting;
import levistico.bconstruct.recipes.BRecipe;
import levistico.bconstruct.recipes.BToolRecipe;
import levistico.bconstruct.tools.BTool;
import levistico.bconstruct.tools.BTools;
import levistico.bconstruct.utils.Utils;
import net.minecraft.src.*;

import java.util.ArrayList;

public final class ContainerToolStation extends ContainerCraftingWithPanels {


    ArrayList<GuiButton> toolSelectButtons = new ArrayList<>();
    GuiTextField nameField;
    String chosenName = "";
    BGuiButton selectedButton;
    private GuiTextField.TextChangeListener nameChangeListener;

    //TODO everything about the arrangement has to be done here first, all the nits and grits, and then this data can be passed to panel generation by having accessible fields
    public ContainerToolStation(InventoryPlayer inventoryplayer, CraftingTileEntity tileEntity) {
       super(inventoryplayer, tileEntity);
        //TODO maybe change the arrangement of slots a bit and make it so that each tool has its own positions and set of icons

        recipe = new BToolRecipe(BTools.toolList.get(0));

        maxSlot = ((BTool)recipe.result).composition.size();
        //TODO reduce active slots

        int i = 0;
        for(BTool tool : BTools.toolList) {
            GuiButton button = new BGuiButton(i, (i%5)*20, (i/5)*20, 18, 18, tool.baseTextureUV);
            button.setListener(button1 -> {
                recipe.result = tool;
                Integer newmax = tool.composition.size();
                if(newmax > maxSlot) {
                    //open slots
                    for(int j = maxSlot+1; j <= newmax; j++) {
                        ((BSlotActivatable)getSlot(j)).isActive = true;
                    }
                } else if (maxSlot > newmax) {
                    //return items and close slots
                    for(int j = maxSlot; j > newmax; j--) {
                        quickMoveItems(j, inventoryplayer.player, true, true);
                        ((BSlotActivatable) getSlot(j)).isActive = false;
                    }
                }
                if(selectedButton != null) selectedButton.isSelected = false;
                ((BGuiButton)button1).isSelected = true;
                selectedButton = (BGuiButton) button1;
                onCraftMatrixChanged();
            });
            toolSelectButtons.add(button);
            i++;
        }
        selectedButton = (BGuiButton) toolSelectButtons.get(0);
        ((BGuiButton) toolSelectButtons.get(0)).isSelected = true;

        nameChangeListener = new GuiTextField.TextChangeListener() {
            @Override
            public void textChanged(GuiTextField guiTextField) {
                chosenName = guiTextField.getText();
                onCraftMatrixChanged(null);
            }
        };
    }

    @Override
    public void onCraftMatrixChanged(IInventory noUse) {
        ItemStack resultStack = getRecipe().getCraftingResult(tileEntity.inventoryCrafting);
        if(!Utils.isStringEmpty(chosenName) && resultStack != null) {
            BTool.setCustomName(resultStack, chosenName);
        }
        this.craftResult.setInventorySlotContents(0, resultStack);
    }

    public void initializeNameField() {
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
