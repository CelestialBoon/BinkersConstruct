package levistico.bconstruct.crafting;

import levistico.bconstruct.gui.containers.GUIToolStation;
import levistico.bconstruct.mixinInterfaces.IBinkersEntityPlayerMP;
import levistico.bconstruct.mixinInterfaces.IBinkersEntityPlayerSP;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import net.minecraft.server.entity.player.EntityPlayerMP;

public final class BlockToolStation extends BlockCraftingTable {

    public BlockToolStation(int i) {
        super("toolstation", i, Material.wood);
    }

    //todo just inline them here bro
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        CraftingTileEntity tileEntity = (CraftingTileEntity)world.getBlockTileEntity(x, y, z);
        if (world.isClientSide) {
        } else if(player instanceof EntityPlayerMP) {
            //Multiplayer
            ((IBinkersEntityPlayerMP)player).displayGUIToolStation(tileEntity);
        } else {
            //Singleplayer
            ((IBinkersEntityPlayerSP)player).displayGUIScreen(new GUIToolStation(player.inventory, tileEntity));
        }
        return true;
    }
}