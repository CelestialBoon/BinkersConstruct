package levistico.bconstruct.smeltery;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.gui.containers.GUISmelteryController;
import net.minecraft.src.*;
import sunsetsatellite.fluidapi.template.containers.ContainerMultiFluidTank;
import sunsetsatellite.sunsetutils.util.BlockInstance;
import sunsetsatellite.sunsetutils.util.Direction;
import sunsetsatellite.sunsetutils.util.Vec3i;

public class BlockSmelteryController extends BlockContainerRotatable {
    public BlockSmelteryController(int i) {
        super(i, Material.rock);
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntitySmelteryController();
    }

    @Override
    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        if(world.isMultiplayerAndNotHost)
        {
            return true;
        } else
        {
            TileEntitySmelteryController tile = (TileEntitySmelteryController) world.getBlockTileEntity(i, j, k);
            if(tile != null) {
                //TODO: Fix multiblock validation
                if(tile.getMultiblock().isValidAt(world,new BlockInstance(this,new Vec3i(i,j,k),tile),Direction.getDirectionFromSide(world.getBlockMetadata(i,j,k)))){
                    BConstruct.displayGui(entityplayer,new GUISmelteryController(entityplayer.inventory, tile),new ContainerSmeltery(entityplayer.inventory,tile),tile,i,j,k);
                }
            }
            return true;
        }
    }
}
