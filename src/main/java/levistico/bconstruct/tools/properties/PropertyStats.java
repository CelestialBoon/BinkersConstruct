package levistico.bconstruct.tools.properties;

import levistico.bconstruct.tools.modifiers.ModifierStats;
import levistico.bconstruct.tools.stats.StatBoosts;

import java.util.List;
import java.util.stream.Stream;

public class PropertyStats extends Property {
    public PropertyStats(String name, ModifierStats modifier, int level) {
        super(name, modifier, level);
    }
    public List<StatBoosts> getBoosts(int level) {
        return ((ModifierStats)modifier).getBoosts(level);
    }

    public static StatBoosts getTotalStatBoosts(Stream<PropertyStats> boostProperties) {
        return StatBoosts.merge_destructive(boostProperties.flatMap(p -> p.getBoosts(p.level).stream()));
    }
}
