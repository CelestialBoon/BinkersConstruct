package levistico.bconstruct.mixinInterfaces;

import levistico.bconstruct.crafting.CraftingTileEntity;

public interface IBinkersEntityPlayerMP {
    public void displayGUICraftingStation(CraftingTileEntity tileEntity);
    public void displayGUIPartBuilder(CraftingTileEntity tileEntity);
    public void displayGUIToolStation(CraftingTileEntity tileEntity);
}
