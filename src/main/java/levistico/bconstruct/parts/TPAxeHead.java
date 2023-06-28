package levistico.bconstruct.parts;

import levistico.bconstruct.utils.Pair;

public class TPAxeHead extends BToolPart {

    public TPAxeHead(int i) {
        super(i, "axeHead", EToolPart.axeHead, PartsFlag.HEAD, 3, true, new Pair<>(2,2));
    }
}
