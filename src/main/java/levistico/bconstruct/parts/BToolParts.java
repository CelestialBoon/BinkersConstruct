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
    public static TPBlade blade;
    public static ArrayList<BToolPart> partList = new ArrayList<>();
    public static ArrayList<String> baseTextureArray = new ArrayList<>();

    public static void InitializeToolParts(String MOD_ID) {

        repairKit = (TPRepairKit) createToolPart(MOD_ID, new TPRepairKit(BConstruct.idInc++), EToolPart.repairKit, "repair_kit");
        rod = (TPRod) createToolPart(MOD_ID, new TPRod(BConstruct.idInc++), EToolPart.rod, "rod");
        binding = (TPBinding) createToolPart(MOD_ID, new TPBinding(BConstruct.idInc++), EToolPart.binding, "binding");
        axeHead = (TPAxeHead) createToolPart(MOD_ID, new TPAxeHead(BConstruct.idInc++), EToolPart.axeHead, "axe_head");
        pickaxeHead = (TPPickaxeHead) createToolPart(MOD_ID, new TPPickaxeHead(BConstruct.idInc++), EToolPart.pickaxeHead, "pickaxe_head");
        shovelHead = (TPShovelHead) createToolPart(MOD_ID, new TPShovelHead(BConstruct.idInc++), EToolPart.shovelHead, "shovel_head");
        largeGuard = (TPLargeGuard) createToolPart(MOD_ID, new TPLargeGuard(BConstruct.idInc++), EToolPart.largeGuard, "large_guard");
        blade = (TPBlade) createToolPart(MOD_ID, new TPBlade(BConstruct.idInc++), EToolPart.blade, "blade");
    }

    static BToolPart createToolPart(String modId, BToolPart toolPart, EToolPart enumToolPart, String partTexture) {
        toolPart.setKey(HalpLibe.addModId(modId, toolPart.name));
        Utils.setAt(partList, enumToolPart.ordinal(), toolPart);
        Utils.setAt(baseTextureArray, enumToolPart.ordinal(), partTexture);
        return toolPart;
    }
}
