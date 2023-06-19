package levistico.bconstruct.tools.modifiers;

public class ModifierEcological extends Modifier {
    public ModifierEcological() {
        super(10);
    }

    @Override
    public float getRepairFactor(int level) {
        return (float) Math.pow(1.5, level);
    }
}
