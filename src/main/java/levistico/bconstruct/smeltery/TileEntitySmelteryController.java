package levistico.bconstruct.smeltery;

import net.minecraft.src.TileEntity;
import sunsetsatellite.sunsetutils.util.multiblocks.IMultiblock;
import sunsetsatellite.sunsetutils.util.multiblocks.Multiblock;

public class TileEntitySmelteryController extends TileEntity implements IMultiblock {

    public Multiblock multiblock;

    public TileEntitySmelteryController(){
        multiblock = Multiblock.multiblocks.get("smeltery");
    }

    @Override
    public Multiblock getMultiblock() {
        return multiblock;
    }
}
