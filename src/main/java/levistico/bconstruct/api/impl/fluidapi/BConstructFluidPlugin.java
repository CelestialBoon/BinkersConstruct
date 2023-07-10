package levistico.bconstruct.api.impl.fluidapi;

import levistico.bconstruct.BConstruct;
import net.minecraft.src.Block;
import net.minecraft.src.BlockFluid;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBucket;
import org.slf4j.Logger;
import sunsetsatellite.fluidapi.FluidAPI;
import sunsetsatellite.fluidapi.FluidAPIPlugin;
import sunsetsatellite.fluidapi.FluidRegistry;
import sunsetsatellite.fluidapi.FluidRegistryEntry;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class BConstructFluidPlugin implements FluidAPIPlugin {
    @Override
    public void initializePlugin(Logger logger) {
        logger.info("Loading fluids from "+ BConstruct.MOD_ID +"..");
        FluidRegistryEntry entry = new FluidRegistryEntry(BConstruct.MOD_ID,BConstruct.bucketMoltenMetal, Item.bucket, (BlockFluid) BConstruct.moltenMetalFlowing);
        FluidRegistry.addToRegistry("moltenMetal",entry);
        for (Field field : BConstruct.class.getDeclaredFields()) {
            try {
                if(field.get(null) instanceof ItemBucket){
                    ItemBucket bucket = (ItemBucket) field.get(null);
                    String name = bucket.getItemName().replace("item.","").replace(BConstruct.MOD_ID+".","").replace("bucket","");
                    int id = (int) FluidAPI.getPrivateValue(bucket.getClass(),bucket,"idToPlace");
                    entry = new FluidRegistryEntry(BConstruct.MOD_ID,bucket, bucket.getContainerItem(), (BlockFluid) Block.blocksList[id]);
                    FluidRegistry.addToRegistry(name,entry);
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
