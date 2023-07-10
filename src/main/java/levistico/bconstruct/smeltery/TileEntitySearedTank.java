package levistico.bconstruct.smeltery;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFluid;
import sunsetsatellite.fluidapi.FluidRegistry;
import sunsetsatellite.fluidapi.api.FluidStack;
import sunsetsatellite.fluidapi.template.tiles.TileEntityFluidTank;
import sunsetsatellite.sunsetutils.util.Connection;
import sunsetsatellite.sunsetutils.util.Direction;

import java.util.ArrayList;

public class TileEntitySearedTank extends TileEntityFluidTank {

    public TileEntitySearedTank(){
        this.fluidCapacity[0] = 4000;
        this.transferSpeed = 50;
        this.connections.replace(Direction.Y_POS, Connection.INPUT);
        this.connections.replace(Direction.Y_NEG, Connection.OUTPUT);
        this.acceptedFluids.get(0).add((BlockFluid) Block.fluidLavaFlowing);
    }

    @Override
    public String getInvName() {
        return "Seared Tank";
    }

}
