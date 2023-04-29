package levistico.bconstruct.materials;

import org.apache.commons.lang3.math.Fraction;

public final class ValueResult {

    public ValueResult(BToolMaterial material, Fraction value, int liquidValue) {
        this.material = material;
        this.value = value;
        this.liquidValue = liquidValue;
    }
    private Fraction value;
    private BToolMaterial material;

    private Integer liquidValue;

    public Fraction getValue() {
        return value;
    }
    public BToolMaterial getMaterial() {
        return material;
    }

    public Integer getLiquidValue() {
        return liquidValue;
    }
}
