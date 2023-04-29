package levistico.bconstruct.crafting;

import levistico.bconstruct.recipes.BRecipe;
import levistico.bconstruct.recipes.BToolRecipe;
import levistico.bconstruct.tools.BTool;
import levistico.bconstruct.tools.BTools;
import levistico.bconstruct.utils.Utils;
import net.minecraft.src.*;

import java.util.Objects;

public final class ContainerToolStation extends ContainerCraftingWithButtons {


    GuiTextField nameField;
    String chosenName = "";
    BGuiButton selectedButton;
    private GuiTextField.TextChangeListener nameChangeListener;

    public ContainerToolStation(InventoryPlayer inventoryplayer, CraftingTileEntity tileEntity) {
        super(inventoryplayer, tileEntity);
        //TODO maybe change the arrangement of slots a bit and make it so that each tool has its own positions and set of icons
        this.addSlot(new BSlotCrafting(this, inventoryplayer.player, this.craftResult, 0, 124, 35)); //craft result

        recipe = new BToolRecipe(BTools.toolList.get(0));

        maxSlot = ((BTool)((BToolRecipe) recipe).result).composition.size();
        int j1;
        int l1;
        for(j1 = 0; j1 < 3; ++j1) { //crafting slots
            for(l1 = 0; l1 < 3; ++l1) {
                this.addSlot(new BSlotActivatable(this.tileEntity.inventoryCrafting, l1 + j1 * 3, l1 + j1 * 3 < maxSlot, 30 + l1 * 18, 35 + j1 * 18));
            }
        }

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
            buttons.add(button);
            i++;
        }
        selectedButton = (BGuiButton) buttons.get(0);
        ((BGuiButton) buttons.get(0)).isSelected = true;

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
