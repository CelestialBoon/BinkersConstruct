package levistico.bconstruct.tools.modifiers;

import levistico.bconstruct.tools.stats.EToolStat;
import levistico.bconstruct.tools.stats.EMiningLevel;
import levistico.bconstruct.tools.stats.StatBoosts;

public class Modifiers {
    public static final Modifier EMPTY_1 = new ModifierEmpty(1);

    public static final Modifier ECOLOGICAL = new ModifierEcological();
    public static final ModifierStats STATS_DIAMOND = new ModifierStats(1, new StatBoosts()
            .add(EToolStat.durability.ordinal(), 500)
            .add(EToolStat.attackDamage.ordinal(), 1)
            .add(EToolStat.miningSpeed.ordinal(),2)
            .changeMiningLevel(EMiningLevel.Diamond.ordinal()));
    public static final ModifierStats STATS_REINFORCED = new ModifierStats(5, new StatBoosts()
            .multiplyBase(EToolStat.reinforced.ordinal(), 0.65f)
    );
}
