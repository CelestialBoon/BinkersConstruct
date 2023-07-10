package levistico.bconstruct.smeltery;

import net.minecraft.src.*;

public class BlockSmelteryDrain extends BlockContainerRotatable {
    public BlockSmelteryDrain(int i, Material material) {
        super(i, material);
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntitySmelteryDrain();
    }

    @Override
    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
        super.blockActivated(world,i,j,k,entityplayer);
        TileEntitySmelteryDrain tile = (TileEntitySmelteryDrain) world.getBlockTileEntity(i,j,k);
        if(tile != null){
            tile.activated = !tile.activated;
        }
        return true;
    }
}
