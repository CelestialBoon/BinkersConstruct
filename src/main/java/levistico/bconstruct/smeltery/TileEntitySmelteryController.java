package levistico.bconstruct.smeltery;

import levistico.bconstruct.BConstruct;
import net.minecraft.src.Block;
import net.minecraft.src.BlockFluid;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import sunsetsatellite.fluidapi.FluidRegistry;
import sunsetsatellite.fluidapi.api.FluidStack;
import sunsetsatellite.fluidapi.template.tiles.TileEntityMultiFluidTank;
import sunsetsatellite.sunsetutils.util.BlockInstance;
import sunsetsatellite.sunsetutils.util.Connection;
import sunsetsatellite.sunsetutils.util.Direction;
import sunsetsatellite.sunsetutils.util.Vec3i;
import sunsetsatellite.sunsetutils.util.multiblocks.IMultiblock;
import sunsetsatellite.sunsetutils.util.multiblocks.Multiblock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TileEntitySmelteryController extends TileEntityMultiFluidTank implements IMultiblock {

    public Multiblock multiblock;
    public TileEntitySearedTank tank;
    public static final HashMap<ArrayList<FluidStack>,FluidStack> alloyRecipes = new HashMap<>();
    public static final HashMap<ItemStack,FluidStack> smeltingRecipes = new HashMap<>();
    public HashMap<Integer,Integer> progress = new HashMap<>(); //HashMap<SlotId,TicksCooking>
    public boolean multiblockValid = false;

    public TileEntitySmelteryController(){
        multiblock = Multiblock.multiblocks.get("smeltery");
        itemContents = new ItemStack[9];
        fluidCapacity = 9072;
        acceptedFluids.addAll(FluidRegistry.getAllFluids());
        itemConnections.clear();
        for (Direction direction : Direction.values()) {
            itemConnections.put(direction, Connection.NONE);
            connections.put(direction, Connection.NONE);
        }
        for (int i = 0; i < itemContents.length; i++) {
            progress.put(i,0);
        }

        ArrayList<FluidStack> stacks = new ArrayList<>();
        stacks.add(new FluidStack((BlockFluid) BConstruct.moltenCopperFlowing,1));
        stacks.add(new FluidStack((BlockFluid) BConstruct.moltenTinFlowing,1));
        alloyRecipes.put(stacks,new FluidStack((BlockFluid) BConstruct.moltenBronzeFlowing,2));
        smeltingRecipes.put(new ItemStack(Item.ingotIron,1),new FluidStack((BlockFluid) BConstruct.moltenIronFlowing,112));
        smeltingRecipes.put(new ItemStack(Block.oreIronStone,1),new FluidStack((BlockFluid) BConstruct.moltenIronFlowing,112*2));
        smeltingRecipes.put(new ItemStack(Block.oreIronBasalt,1),new FluidStack((BlockFluid) BConstruct.moltenIronFlowing,112*2));
        smeltingRecipes.put(new ItemStack(Block.oreIronGranite,1),new FluidStack((BlockFluid) BConstruct.moltenIronFlowing,112*2));
        smeltingRecipes.put(new ItemStack(Block.oreIronLimestone,1),new FluidStack((BlockFluid) BConstruct.moltenIronFlowing,112*2));
        smeltingRecipes.put(new ItemStack(Block.blockIron,1),new FluidStack((BlockFluid) BConstruct.moltenIronFlowing,112*9));

    }

    @Override
    public void updateEntity() {
        //TODO: By god somebody optimize this later
        if(getMultiblock().isValidAt(worldObj,new BlockInstance(this.getBlockType(),new Vec3i(xCoord,yCoord,zCoord),this),Direction.getDirectionFromSide(worldObj.getBlockMetadata(xCoord,yCoord,zCoord)))) {
            multiblockValid = true;
        } else {
            multiblockValid = false;
        }
        if(multiblockValid){
            if(fluidContents.isEmpty()){
                fluidContents.add(new FluidStack((BlockFluid) BConstruct.moltenCopperFlowing,9072/4));
                fluidContents.add(new FluidStack((BlockFluid) BConstruct.moltenTinFlowing,9072/4));
            }
            getTank();
            processAlloys();
            processSmeltables();
            super.updateEntity();
        }
    }

    @Override
    public Multiblock getMultiblock() {
        return multiblock;
    }


    public void getTank(){
        if(tank == null){
            for (int x = xCoord-1; x < xCoord+4; x++) {
                for (int z = zCoord; z > zCoord-4; z--) {
                    if(worldObj.getBlockTileEntity(x,yCoord,z) instanceof TileEntitySearedTank){
                        tank = (TileEntitySearedTank) worldObj.getBlockTileEntity(x,yCoord,z);
                    }
                }
            }
        } else if(!(worldObj.getBlockTileEntity(tank.xCoord, tank.yCoord, tank.zCoord) instanceof TileEntitySearedTank)) {
            tank = null;
        }
    }

    public void processSmeltables(){
        for (int i = 0; i < itemContents.length; i++) {
            if(itemContents[i] == null){
                progress.put(i,0);
            } else {
                boolean found = false;
                for (ItemStack inputStack : smeltingRecipes.keySet()) {
                    if(inputStack.isItemEqual(itemContents[i]) && itemContents[i].stackSize >= inputStack.stackSize){
                        found = true;
                        if(tank != null && tank.fluidContents[0] != null && tank.fluidContents[0].amount >= 20){
                            if(progress.get(i) < 1000){
                                progress.put(i,progress.get(i)+1);
                                break;
                            }
                        }
                    }
                }
                if(!found){
                    progress.put(i,0);
                }
            }
        }
        for (Map.Entry<Integer, Integer> entry : progress.entrySet()) {
            Integer slotId = entry.getKey();
            Integer progressValue = entry.getValue();
            if (progressValue >= 1000) {
                if(tank.fluidContents[0] != null && tank.fluidContents[0].amount >= 20) {
                    if (itemContents[slotId] != null) {
                        for (Map.Entry<ItemStack, FluidStack> e : smeltingRecipes.entrySet()) {
                            ItemStack I = e.getKey();
                            FluidStack O = e.getValue().copy();
                            if (itemContents[slotId].isItemEqual(I)) {
                                if (canInsertFluid(O)) {
                                    insertFluid(O);
                                    itemContents[slotId].stackSize -= I.stackSize;
                                    if(itemContents[slotId].stackSize <= 0){
                                        itemContents[slotId] = null;
                                    }
                                    tank.fluidContents[0].amount -= 20;
                                    progress.put(slotId,0);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void processAlloys(){
        Map.Entry<ArrayList<FluidStack>, FluidStack> validEntry = null;
        for (FluidStack fluidStack : fluidContents) {
            for (Map.Entry<ArrayList<FluidStack>, FluidStack> entry : alloyRecipes.entrySet()) {
                ArrayList<FluidStack> I = entry.getKey();
                FluidStack O = entry.getValue();
                boolean found = false;
                for (FluidStack inputStack : I) {
                    if(inputStack.isFluidEqual(fluidStack)) found = true;
                    break;
                }
                if(found){
                    boolean allFound = true;
                    for (FluidStack inputStack : I) {
                        FluidStack stack = findStack(inputStack.liquid);
                        if(stack == null || stack.amount < inputStack.amount){
                            allFound = false;
                            break;
                        }
                    }
                    if(allFound){
                        validEntry = entry;
                    }
                }
            }
        }
        if(validEntry != null){
            FluidStack outputCopy = validEntry.getValue().copy();
            if(canInsertFluid(outputCopy)){
                insertFluid(outputCopy);
                for (FluidStack inputStack : validEntry.getKey()) {
                    FluidStack stack = findStack(inputStack.liquid);
                    stack.amount -= inputStack.amount;
                }
            }
        }
    }

}
