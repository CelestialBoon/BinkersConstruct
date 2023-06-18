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
                //turbo dirty hack until it's fixed lmaoooo
                world.setBlockAndMetadata(i-1,j,k,this.blockID,0);
                world.setBlockMetadata(i-1,j,k,world.getBlockMetadata(i,j,k));
                //TODO: Fix multiblock validation
                if(tile.getMultiblock().isValidAt(world,new BlockInstance(this,new Vec3i(i,j,k),tile), Direction.Z_POS)){
                    world.setBlockAndMetadata(i-1,j,k,0,0);
                    BConstruct.displayGui(entityplayer,new GUISmelteryController(entityplayer.inventory, tile),new ContainerSmeltery(entityplayer.inventory,tile),tile,i,j,k);
                }
            }
            return true;
        }
    }
}
