package levistico.bconstruct.properties;

public class Property {
    public final String name;
    public final Effect effect;
    public final int maxLevel;

    public Property(String name, Effect effect, int maxLevel) {
        this.name = name;
        this.effect = effect;
        this.maxLevel = maxLevel;
    }
}
