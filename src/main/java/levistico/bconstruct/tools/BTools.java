package levistico.bconstruct.tools;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.tools.actions.HarvestAction;
import levistico.bconstruct.tools.actions.ToolActions;
import levistico.bconstruct.utils.Utils;
import net.minecraft.core.item.Item;;;
import turniplabs.halplibe.helper.ItemHelper;

import java.util.ArrayList;
import java.util.List;

import static levistico.bconstruct.BConstruct.MOD_ID;

public class BTools {
    public static ToolBroadsword broadsword;
    public static ToolHatchet hatchet;
    public static ToolPickaxe pickaxe;
    public static ToolMattock mattock;
    public static ToolKama kama;
    public static ToolPickadze pickadze;

    public static final ArrayList<BTool> toolList = new ArrayList<>();

    public static void InitializeTools(String MOD_ID) {
        pickaxe = (ToolPickaxe) createTool(new ToolPickaxe(BConstruct.idInc++)
                .setBaseDamageBonus(2)
        );
        hatchet = (ToolHatchet) createTool(new ToolHatchet(BConstruct.idInc++)
                .setBaseDamageBonus(3)
        );
        mattock = (ToolMattock) createTool(new ToolMattock(BConstruct.idInc++)
                .setBaseDamageBonus(2)
                .setDurabilityMultiplier(1.25f)
        );
        broadsword = (ToolBroadsword) createTool(new ToolBroadsword(BConstruct.idInc++)
                .setBaseDamageBonus(4).setAttackDamageMultiplier(2)
                .setDurabilityMultiplier(1.1f)
        );
        kama = (ToolKama) createTool(new ToolKama(BConstruct.idInc++)
                .setBaseDamageBonus(3)
        );
        pickadze = (ToolPickadze) createTool(new ToolPickadze(BConstruct.idInc++)
                .setBaseDamageBonus(2)
                .setDurabilityMultiplier(1.3f)
        );
    }

    public static Item createTool(BTool tool) {
        return ItemHelper.createItem(MOD_ID, Utils.add(toolList, tool), tool.name);
    }
}
