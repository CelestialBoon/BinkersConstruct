package levistico.bconstruct.mixinInterfaces;

import levistico.bconstruct.crafting.CraftingTileEntity;
import net.minecraft.src.Container;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;

public interface IBinkersEntityPlayerMP {
    public void displayGUICraftingStation(CraftingTileEntity tileEntity);
    public void displayGUIPartBuilder(CraftingTileEntity tileEntity);
    public void displayGUIToolStation(CraftingTileEntity tileEntity);

    void displayBinkersGuiScreen(GuiScreen guiScreen, Container container, IInventory tile, int x, int y, int z);

    void displayBinkersGuiScreen(GuiScreen guiScreen, Container container, IInventory tile, ItemStack stack);
}
