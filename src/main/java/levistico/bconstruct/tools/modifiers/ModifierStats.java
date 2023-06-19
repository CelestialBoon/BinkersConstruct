package levistico.bconstruct.tools.modifiers;


import levistico.bconstruct.tools.stats.StatBoosts;
import levistico.bconstruct.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ModifierStats extends Modifier {
    public StatBoosts boost;
    public ModifierStats(int maxLevel, StatBoosts boost) {
        super(maxLevel);
        this.boost = boost;
    }

    public List<StatBoosts> getBoosts(int level) { //destructive operations follow so copy here
        List<StatBoosts> boosts = new ArrayList<>();
        for(Integer i : Utils.range(0, level)) {
            boosts.add(boost.copy());
        }
        return boosts;
    }
}
