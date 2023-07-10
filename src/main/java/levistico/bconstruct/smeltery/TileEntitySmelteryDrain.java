package levistico.bconstruct.smeltery;

import levistico.bconstruct.BConstruct;
import net.minecraft.src.BlockFluid;
import net.minecraft.src.TileEntity;
import sunsetsatellite.fluidapi.api.FluidStack;
import sunsetsatellite.fluidapi.api.IFluidInventory;
import sunsetsatellite.fluidapi.api.IFluidTransfer;
import sunsetsatellite.fluidapi.template.tiles.TileEntityFluidItemContainer;
import sunsetsatellite.sunsetutils.util.Connection;
import sunsetsatellite.sunsetutils.util.Direction;
import sunsetsatellite.sunsetutils.util.Vec3i;

import java.util.ArrayList;

public class TileEntitySmelteryDrain extends TileEntity {
    public boolean activated = false;
    public FluidStack drainingStack;

    @Override
    public void updateEntity() {
        if(drainingStack != null && drainingStack.amount < 0){
            drainingStack = null;
        }
        if(activated && drainingStack != null){
            Vec3i pos = new Vec3i(xCoord,yCoord,zCoord).add(Direction.getDirectionFromSide(getBlockMetadata()).getOpposite().getVec()).subtract(new Vec3i(0,1,0));
            TileEntityFluidItemContainer tile = (TileEntityFluidItemContainer) worldObj.getBlockTileEntity(pos.x,pos.y,pos.z);
            if(!(tile instanceof TileEntityCastingBasin)){
                activated = false;
            } else {
                if (tile.connections.get(Direction.Y_POS) == Connection.INPUT || tile.connections.get(Direction.Y_POS) == Connection.BOTH) {
                    int maxFlow = Math.min(Math.min(50,drainingStack.amount), ((IFluidInventory) tile).getTransferSpeed());
                    if (tile.canInsertFluid(tile.activeFluidSlots.get(Direction.Y_POS), new FluidStack(drainingStack.liquid, maxFlow))) {
                        FluidStack transferablePortion = drainingStack.splitStack(maxFlow);
                        if (tile.fluidContents[tile.activeFluidSlots.get(Direction.Y_POS)] == null) {
                            tile.fluidContents[tile.activeFluidSlots.get(Direction.Y_POS)] = transferablePortion;
                        } else {
                            tile.fluidContents[tile.activeFluidSlots.get(Direction.Y_POS)].amount += transferablePortion.amount;
                        }
                    } else {
                        activated = false;
                    }
                }
            }
        }
    }
}