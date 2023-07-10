package levistico.bconstruct.smeltery;

import levistico.bconstruct.BConstruct;
import net.minecraft.src.Block;
import net.minecraft.src.BlockFluid;
import net.minecraft.src.EntityItem;
import net.minecraft.src.ItemStack;
import sunsetsatellite.fluidapi.FluidRegistry;
import sunsetsatellite.fluidapi.api.FluidStack;
import sunsetsatellite.fluidapi.template.tiles.TileEntityFluidTank;
import sunsetsatellite.sunsetutils.util.Connection;
import sunsetsatellite.sunsetutils.util.Direction;

import java.util.HashMap;

public class TileEntityCastingBasin extends TileEntityFluidTank {

    public static final HashMap<FluidStack,ItemStack> basinRecipes = new HashMap<>();
    public int progress = 0;
    public int maxProgress = 600;

    public TileEntityCastingBasin(){
        this.fluidCapacity[0] = 1008;
        this.transferSpeed = 10;
        this.connections.replace(Direction.Y_POS, Connection.INPUT);
        this.connections.replace(Direction.Y_NEG, Connection.OUTPUT);
        this.acceptedFluids.get(0).addAll(FluidRegistry.getAllFluids());
        basinRecipes.put(new FluidStack((BlockFluid) BConstruct.moltenIronFlowing,112*9),new ItemStack(Block.blockIron,1));
    }

    @Override
    public void updateEntity() {
        if(fluidContents[0] != null) {
            basinRecipes.forEach((K,V)->{
                if(fluidContents[0] != null && K.isFluidEqual(fluidContents[0])){
                    if(fluidContents[0].amount >= K.amount){
                        progress++;
                        if(progress >= maxProgress){
                            progress = 0;
                            EntityItem item = new EntityItem(worldObj,xCoord,yCoord+1,zCoord,V.copy());
                            worldObj.entityJoinedWorld(item);
                            fluidContents[0] = null;
                        }
                    }
                }
            });
            //this.fluidContents[0] = new FluidStack((BlockFluid) BConstruct.moltenIronFlowing,1008);
        }
    }

    @Override
    public String getInvName() {
        return "Casting Basin";
    }
}
