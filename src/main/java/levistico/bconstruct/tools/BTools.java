package levistico.bconstruct.tools;

import levistico.bconstruct.BConstruct;
import turniplabs.halplibe.helper.ItemHelper;

import java.util.ArrayList;
import java.util.List;

public class BTools {
    public static ToolBroadsword broadsword;
    public static ToolHatchet hatchet;
    public static ToolPickaxe pickaxe;
    public static ToolMattock mattock;

    public static final ArrayList<BTool> toolList = new ArrayList<>();

    public static void InitializeTools(String MOD_ID) {
        pickaxe = (ToolPickaxe) ItemHelper.createItem(MOD_ID, new ToolPickaxe(BConstruct.itemIdInc++), "pickaxe");
        toolList.add(pickaxe);
        hatchet = (ToolHatchet) ItemHelper.createItem(MOD_ID, new ToolHatchet(BConstruct.itemIdInc++), "hatchet");
        toolList.add(hatchet);
        mattock = (ToolMattock) ItemHelper.createItem(MOD_ID, new ToolMattock(BConstruct.itemIdInc++), "mattock");
        toolList.add(mattock);
        broadsword = (ToolBroadsword) ItemHelper.createItem(MOD_ID, new ToolBroadsword(BConstruct.itemIdInc++), "broadsword");
        toolList.add(broadsword);
    }
}
