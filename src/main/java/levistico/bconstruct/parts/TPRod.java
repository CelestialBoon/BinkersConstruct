package levistico.bconstruct.parts;

import levistico.bconstruct.utils.Pair;

public class TPRod extends BToolPart {

    public TPRod(int i) {
        super(i, "Tool Rod", EToolPart.rod, PartFlags.HANDLE, 2, true, new Pair<>(0,3));
    }
}
