package levistico.bconstruct.parts;

import levistico.bconstruct.utils.Pair;

public class TPShovelHead extends BToolPart {

    public TPShovelHead(int i) {
        super(i, "Shovel Head", EToolPart.shovelHead, PartFlags.HEAD, 2, true, new Pair<>(3,2));
    }
}
