package levistico.bconstruct.properties;

import levistico.bconstruct.mixin.AccessorNBTTagCompound;
import levistico.bconstruct.tools.BTool;
import net.minecraft.src.NBTTagCompound;

import java.util.ArrayList;
import java.util.Map;

public class PropertyUtils {
    public static ArrayList<Property> getProperties(NBTTagCompound tags) {
        NBTTagCompound proptags = BTool.getPropertyTags(tags);
        ArrayList<Property> propertyList = new ArrayList<>();
        Map<String, Integer> propMap = ((AccessorNBTTagCompound)proptags).getTagMap();
        for(String s : propMap.keySet()) {
            switch (s) {
                case Properties.SILKTOUCH:
                    propertyList.add(Properties.silkTouch);
                    break;
            }
        }
        return propertyList;
    }
}
