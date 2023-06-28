package levistico.bconstruct.parts;

import levistico.bconstruct.utils.Pair;

public class TPRepairKit extends BToolPart {

    public TPRepairKit(int i) {
        super(i, "repairKit", EToolPart.repairKit, PartsFlag.REPAIR_KIT, 1, false, new Pair<>(12,2));
    }
}
