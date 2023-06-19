package levistico.bconstruct.parts;

import levistico.bconstruct.utils.Pair;

public class TPRod extends BToolPart {

    public TPRod(int i) {
        super(i, "rod", EToolPart.rod, PartsFlag.HANDLE, 1, true, new Pair<>(0,4));
    }
}
