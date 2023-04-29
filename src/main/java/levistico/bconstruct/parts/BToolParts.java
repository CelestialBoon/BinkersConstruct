package levistico.bconstruct.parts;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.utils.Utils;
import turniplabs.halplibe.HalpLibe;

import java.util.ArrayList;

public class BToolParts {
    public static TPRepairKit repairKit;
    public static TPRod rod;
    public static TPBinding binding;
    public static TPAxeHead axeHead;
    public static TPPickaxeHead pickaxeHead;
    public static TPShovelHead shovelHead;
    public static TPLargeGuard largeGuard;
    public static TPSwordBlade swordBlade;
    public static ArrayList<BToolPart> partArray = new ArrayList<>();
    public static ArrayList<String> baseTextureArray = new ArrayList<>();

    public static void InitializeToolParts(String MOD_ID) {

        repairKit = (TPRepairKit) createToolPart(MOD_ID, new TPRepairKit(BConstruct.itemIdInc++), EToolPart.repairKit, "repairKit", "repair_kit");
        rod = (TPRod) createToolPart(MOD_ID, new TPRod(BConstruct.itemIdInc++), EToolPart.rod, "rod", "rod");
        binding = (TPBinding) createToolPart(MOD_ID, new TPBinding(BConstruct.itemIdInc++), EToolPart.binding, "binding", "binding");
        axeHead = (TPAxeHead) createToolPart(MOD_ID, new TPAxeHead(BConstruct.itemIdInc++), EToolPart.axeHead, "axeHead", "axe_head");
        pickaxeHead = (TPPickaxeHead) createToolPart(MOD_ID, new TPPickaxeHead(BConstruct.itemIdInc++), EToolPart.pickaxeHead, "pickaxeHead", "pickaxe_head");
        shovelHead = (TPShovelHead) createToolPart(MOD_ID, new TPShovelHead(BConstruct.itemIdInc++), EToolPart.shovelHead, "shovelHead", "shovel_head");
        largeGuard = (TPLargeGuard) createToolPart(MOD_ID, new TPLargeGuard(BConstruct.itemIdInc++), EToolPart.largeGuard, "largeGuard", "large_guard");
        swordBlade = (TPSwordBlade) createToolPart(MOD_ID, new TPSwordBlade(BConstruct.itemIdInc++), EToolPart.swordBlade, "swordBlade", "sword_blade");
    }

    static BToolPart createToolPart(String modId, BToolPart toolPart, EToolPart enumToolPart, String translationKey, String partTexture) {
        toolPart.setItemName(HalpLibe.addModId(modId, translationKey));
        Utils.setGrow(partArray, enumToolPart.ordinal(), toolPart);
        Utils.setGrow(baseTextureArray, enumToolPart.ordinal(), partTexture);
        return toolPart;
    }
}
