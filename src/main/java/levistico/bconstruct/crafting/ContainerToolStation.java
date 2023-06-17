package levistico.bconstruct.crafting;

import levistico.bconstruct.gui.BGuiButton;
import levistico.bconstruct.recipes.BRecipe;
import levistico.bconstruct.recipes.BToolRecipe;
import levistico.bconstruct.tools.BTool;
import levistico.bconstruct.tools.BTools;
import levistico.bconstruct.utils.Utils;
import net.minecraft.src.*;

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
        //TODO change here the arrangement of slots a bit and make it so that each tool has its own positions and set of icons, OR have it done in the individual recipes

        recipe = new BToolRecipe(BTools.toolList.get(0));

        super.resizeCraftingSlots(((BTool)recipe.result).composition.size());

        int i = 0;
        for(BTool tool : BTools.toolList) {
            BGuiButton button = new BGuiButton(i, tool.toolName,(i%5)*20, (i/5)*20, 18, 18, tool.baseTextureUV);
            button.setListener(button1 -> {
                recipe.result = tool;
                super.resizeCraftingSlots(tool.composition.size());
                if(selectedButton != null) selectedButton.isSelected = false;
                ((BGuiButton)button1).isSelected = true;
                selectedButton = (BGuiButton) button1;
                onCraftMatrixChanged();
            });
            toolSelectButtons.add(button);
            i++;
        }
        selectedButton = toolSelectButtons.get(0);
        toolSelectButtons.get(0).isSelected = true;

        nameChangeListener = guiTextField -> {
            chosenName = guiTextField.getText();
            onCraftMatrixChanged(null);
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
