package levistico.bconstruct.parts;

import levistico.bconstruct.utils.Pair;

public class TPPickaxeHead extends BToolPart {

    public TPPickaxeHead(int i) {
        super(i, "pickaxeHead", EToolPart.pickaxeHead, PartsFlag.HEAD, 3, true, new Pair<>(0,2));
    }
}
