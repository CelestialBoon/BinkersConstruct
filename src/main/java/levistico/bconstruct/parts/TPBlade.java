package levistico.bconstruct.parts;

import levistico.bconstruct.utils.Pair;

public class TPBlade extends BToolPart {

    public TPBlade(int i) {
        super(i, "blade", EToolPart.blade, PartsFlag.HEAD, 3, true, new Pair<>(1,2));
    }
}
