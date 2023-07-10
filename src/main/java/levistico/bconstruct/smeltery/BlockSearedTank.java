package levistico.bconstruct.smeltery;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.gui.containers.GUISmelteryController;
import net.minecraft.src.*;
import sunsetsatellite.fluidapi.template.containers.ContainerFluidTank;
import sunsetsatellite.fluidapi.template.gui.GuiFluidTank;
import sunsetsatellite.sunsetutils.util.BlockInstance;
import sunsetsatellite.sunsetutils.util.Direction;
import sunsetsatellite.sunsetutils.util.Vec3i;

public class BlockSearedTank extends BlockContainer {
    public BlockSearedTank(int i, Material material) {
        super(i, material);
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntitySearedTank();
    }

    @Override
    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        if(world.isMultiplayerAndNotHost)
        {
            return true;
        } else
        {
            TileEntitySearedTank tile = (TileEntitySearedTank) world.getBlockTileEntity(i, j, k);
            if(tile != null) {
                BConstruct.displayGui(entityplayer,new GuiFluidTank(entityplayer.inventory, tile),new ContainerFluidTank(entityplayer.inventory,tile),tile,i,j,k);
            }
            return true;
        }
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
