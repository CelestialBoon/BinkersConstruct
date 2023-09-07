package levistico.bconstruct.crafting;

import levistico.bconstruct.gui.containers.GUICraftingStation;
import levistico.bconstruct.mixinInterfaces.IBinkersEntityPlayerMP;
import levistico.bconstruct.mixinInterfaces.IBinkersEntityPlayerSP;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import net.minecraft.server.entity.player.EntityPlayerMP;

public final class BlockCraftingStation extends BlockCraftingTable {

    public BlockCraftingStation(int i) {
        super("craftingstation", i, Material.wood);
    }

    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        CraftingTileEntity tileEntity = (CraftingTileEntity)world.getBlockTileEntity(x, y, z);
        if (world.isClientSide) {
        } else if(player instanceof EntityPlayerMP) {
            //Multiplayer
            ((IBinkersEntityPlayerMP)player).displayGUICraftingStation(tileEntity);
        } else {
            //Singleplayer
            ((IBinkersEntityPlayerSP)player).displayGUIScreen(new GUICraftingStation(player.inventory, tileEntity));
        }
        return true;
    }
}