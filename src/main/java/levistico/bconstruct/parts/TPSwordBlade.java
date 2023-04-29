package levistico.bconstruct.parts;

import levistico.bconstruct.utils.Pair;

public class TPSwordBlade extends BToolPart {

    public TPSwordBlade(int i) {
        super(i, "Sword Blade", EToolPart.swordBlade, PartFlags.HEAD, 3, true, new Pair<>(1,2));
    }
}
