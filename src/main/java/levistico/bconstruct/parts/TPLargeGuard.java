package levistico.bconstruct.parts;

import levistico.bconstruct.utils.Pair;

public class TPLargeGuard extends BToolPart {

    public TPLargeGuard(int i) {
        super(i, "largeGuard", EToolPart.largeGuard, PartsFlag.HANDLE, 1, true, new Pair<>(2,4));
    }
}
