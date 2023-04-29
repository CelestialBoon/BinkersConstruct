package levistico.bconstruct.parts;

import levistico.bconstruct.utils.Pair;

public class TPLargeGuard extends BToolPart {

    public TPLargeGuard(int i) {
        super(i, "Large Guard", EToolPart.largeGuard, PartFlags.OTHER, 1, true, new Pair<>(2,3));
    }
}
