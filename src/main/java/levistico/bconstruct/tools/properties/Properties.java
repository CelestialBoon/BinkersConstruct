package levistico.bconstruct.tools.properties;

import levistico.bconstruct.tools.modifiers.ModifierEcological;
import levistico.bconstruct.tools.modifiers.Modifiers;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Stream;

public class Properties {
    public static final String SILKTOUCH = "silkTouch";
    public static final String ECOLOGICAL = "ecological";
    public static final String REINFORCED = "reinforced";
    public static final String DIAMOND = "diamond";

    public static Collection<Property> collapse(Stream<Property> properties) {
        HashMap<String, Property> newProps = new HashMap<>();
        properties.filter(prop -> prop.level != 0).forEach(prop -> {
            if(newProps.containsKey(prop.name)) {
                Property theProp = newProps.get(prop.name);
                theProp.addLevel(prop.level);
            } else newProps.put(prop.name, prop);
        });
        return newProps.values();
    }
    public static Property generate(String name, int level) {
        switch (name) {
            case SILKTOUCH:
                return new Property(SILKTOUCH, Modifiers.EMPTY_1, level);
            case ECOLOGICAL:
                return new Property(ECOLOGICAL, Modifiers.ECOLOGICAL, level);
            case REINFORCED:
                return new PropertyStats(REINFORCED, Modifiers.STATS_REINFORCED, level);
            case DIAMOND:
                return new PropertyStats(DIAMOND, Modifiers.STATS_DIAMOND, level);
            default:
                throw new IllegalStateException("generating Property with wrong name: " + name);
        }
    }
}
