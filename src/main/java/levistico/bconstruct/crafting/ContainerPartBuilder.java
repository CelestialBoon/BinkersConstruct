package levistico.bconstruct.crafting;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.gui.BGuiButton;
import levistico.bconstruct.gui.BSlotCustomizable;
import levistico.bconstruct.parts.BToolPart;
import levistico.bconstruct.parts.BToolParts;
import levistico.bconstruct.parts.EToolPart;
import levistico.bconstruct.recipes.BRecipe;
import levistico.bconstruct.recipes.BToolPartRecipe;
import levistico.bconstruct.utils.AcceptRule;
import levistico.bconstruct.utils.Pair;
import levistico.bconstruct.utils.Utils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.util.helper.Listener;

import java.util.ArrayList;

public final class ContainerPartBuilder extends BContainerWithRecipe {

    public ArrayList<BGuiButton> buttons = new ArrayList<>();
    public BGuiButton selectedButton;

    public ContainerPartBuilder(InventoryPlayer inventoryplayer, CraftingTileEntity tileEntity) {
        super(inventoryplayer, tileEntity);
        //TODO maybe change the arrangement of slots a bit
//        this.addSlot(new BSlotCrafting(this, 0, inventoryplayer.player, this.craftResult, 124, 35)); //craft result
//        maxSlot = 2;
//        int j1;
//        int l1;
//        for(j1 = 0; j1 < 3; ++j1) { //crafting slots
//            for(l1 = 0; l1 < 3; ++l1) {
//                this.addSlot(new BSlotActivatable(this.tileEntity.inventoryCrafting, l1 + j1 * 3, l1 + j1 * 3 < maxSlot, 30 + l1 * 18, 17 + j1 * 18));
//            }
//        }
        resizeCraftingSlots(2);

        BSlotCustomizable cslot = craftingSlots.get(0);
        cslot.tooltipString = I18n.getInstance().translateKey(BConstruct.blankPattern.getKey() + ".name");
        cslot.acceptsOnly = AcceptRule.acceptsOnlyIds(BConstruct.blankPattern.id);
        cslot.textureUV = new Pair<>(0,12);

        cslot = craftingSlots.get(1);
        cslot.tooltipString = I18n.getInstance().translateKey("material.bconstruct.anything.name");
        cslot.textureUV = new Pair<>(2,12);

        //TODO set custom slot textures here

        Listener<GuiButton> listener = button -> {
            recipe.result = BToolParts.partList.get(button.id);
            if(selectedButton != null) selectedButton.isSelected = false;
            selectedButton = (BGuiButton) button;
            ((BGuiButton)button).isSelected = true;
            onCraftMatrixChanged();
        };

        recipe = new BToolPartRecipe(BToolParts.partList.get(EToolPart.repairKit.ordinal()));
        for(Integer i : Utils.range(0, BToolParts.partList.size())) {
            BToolPart toolPart = BToolParts.partList.get(i);
            BGuiButton button = new BGuiButton(i, Utils.translateKey(toolPart), (i%5)*20, (i/5)*20, 13, toolPart.baseTextureUV);
            button.setListener(listener);
            buttons.add(button);
        }
        listener.listen(buttons.get(0));
    }


    @Override
    public BRecipe getRecipe() {
        if(recipe == null) {
            recipe = new BToolPartRecipe(BToolParts.partList.get(EToolPart.repairKit.ordinal()));
        }
        return recipe;
    }

}
