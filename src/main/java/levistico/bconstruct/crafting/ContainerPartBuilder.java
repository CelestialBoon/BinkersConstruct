package levistico.bconstruct.crafting;

import levistico.bconstruct.parts.BToolPart;
import levistico.bconstruct.parts.BToolParts;
import levistico.bconstruct.parts.EToolPart;
import levistico.bconstruct.recipes.BToolPartRecipe;

import levistico.bconstruct.recipes.BRecipe;
import net.minecraft.src.*;

public final class ContainerPartBuilder extends ContainerCraftingWithButtons {

    BGuiButton selectedButton;

    public ContainerPartBuilder(InventoryPlayer inventoryplayer, CraftingTileEntity tileEntity) {
        super(inventoryplayer, tileEntity);
        //TODO maybe change the arrangement of slots a bit
        this.addSlot(new BSlotCrafting(this, inventoryplayer.player, this.craftResult, 0, 124, 35)); //craft result
        maxSlot = 2;
        int j1;
        int l1;
        for(j1 = 0; j1 < 3; ++j1) { //crafting slots
            for(l1 = 0; l1 < 3; ++l1) {
                this.addSlot(new BSlotActivatable(this.tileEntity.inventoryCrafting, l1 + j1 * 3, l1 + j1 * 3 < maxSlot, 30 + l1 * 18, 17 + j1 * 18));
            }
        }

        ItemStack itemstack;

        recipe = new BToolPartRecipe(BToolParts.partArray.get(EToolPart.repairKit.ordinal()));
        int i = 0;
        for(BToolPart toolPart : BToolParts.partArray) {
            GuiButton button = new BGuiButton(i, (i%5)*20, (i/5)*20, 18, 18, toolPart.baseTextureUV);
            button.setListener(button1 -> {
                recipe.result = toolPart;
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
    }

    @Override
    public BRecipe getRecipe() {
        if(recipe == null) {
            recipe = new BToolPartRecipe(BToolParts.partArray.get(EToolPart.repairKit.ordinal()));
        }
        return recipe;
    }
}
