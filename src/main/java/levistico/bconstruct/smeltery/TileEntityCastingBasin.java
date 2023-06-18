package levistico.bconstruct.smeltery;

import levistico.bconstruct.BConstruct;
import net.minecraft.src.Block;
import net.minecraft.src.BlockFluid;
import sunsetsatellite.fluidapi.FluidRegistry;
import sunsetsatellite.fluidapi.api.FluidStack;
import sunsetsatellite.fluidapi.template.tiles.TileEntityFluidTank;
import sunsetsatellite.sunsetutils.util.Connection;
import sunsetsatellite.sunsetutils.util.Direction;

public class TileEntityCastingBasin extends TileEntityFluidTank {

    public TileEntityCastingBasin(){
        this.fluidCapacity[0] = 1008;
        this.transferSpeed = 10;
        this.connections.replace(Direction.Y_POS, Connection.INPUT);
        this.connections.replace(Direction.Y_NEG, Connection.OUTPUT);
        this.acceptedFluids.get(0).addAll(FluidRegistry.getAllFluids());
    }

    @Override
    public void updateEntity() {
        if(fluidContents[0] == null){
            //this.fluidContents[0] = new FluidStack((BlockFluid) BConstruct.moltenBronzeFlowing,1008/2);
        }
    }

    @Override
    public String getInvName() {
        return "Casting Basin";
    }
}
