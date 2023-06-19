package levistico.bconstruct.crafting;

import levistico.bconstruct.utils.Utils;
import net.minecraft.src.*;

import java.util.Random;

public abstract class BlockCraftingTable extends BlockContainer {
    Random random = new Random();
    public BlockCraftingTable(int i, Material m) {
        super(i, m);
    }

    public abstract boolean blockActivated(World world, int x, int y, int z, EntityPlayer player);
//    {
//        TileEntityCrafting tileEntity = (TileEntityCrafting)world.getBlockTileEntity(x, y, z);
//        if (world.isMultiplayerAndNotHost) {
//        } else if(player instanceof EntityPlayerMP) {
//            //Multiplayer
//            ((IBinkersEntityPlayerMP)player).displayGUICraftingStation(tileEntity);
//        } else {
//            //Singleplayer
//            ((IBinkersEntityPlayerSP)player).displayGUICraftingStation(tileEntity);
//        }
//        return true;
//    }

    public void onBlockRemoval(World world, int i, int j, int k) {
        CraftingTileEntity tileentitycrafting = (CraftingTileEntity) world.getBlockTileEntity(i, j, k);

        for(Integer l : Utils.range(0, tileentitycrafting.inventoryCrafting.getSizeInventory())) {
            ItemStack itemstack = tileentitycrafting.inventoryCrafting.getStackInSlot(l);
            if (itemstack != null) {
                float f = this.random.nextFloat() * 0.8F + 0.1F;
                float f1 = this.random.nextFloat() * 0.8F + 0.1F;
                float f2 = this.random.nextFloat() * 0.8F + 0.1F;

                while(itemstack.stackSize > 0) {
                    int i1 = this.random.nextInt(21) + 10;
                    if (i1 > itemstack.stackSize) {
                        i1 = itemstack.stackSize;
                    }

                    itemstack.stackSize -= i1;
                    EntityItem entityitem = new EntityItem(world, ((float)i + f), ((float)j + f1), ((float)k + f2), new ItemStack(itemstack.itemID, i1, itemstack.getMetadata()));
                    float f3 = 0.05F;
                    entityitem.motionX = ((float)this.random.nextGaussian() * f3);
                    entityitem.motionY = ((float)this.random.nextGaussian() * f3 + 0.2F);
                    entityitem.motionZ = ((float)this.random.nextGaussian() * f3);
                    world.entityJoinedWorld(entityitem);
                }
            }
        }

        super.onBlockRemoval(world, i, j, k);
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new CraftingTileEntity();
    }
}
