package levistico.bconstruct.tools.stats;

import levistico.bconstruct.utils.Utils;

import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class StatBoosts {
    static int len = EToolStat.values().length;
    float[] baseMultipliers = new float[len];
    float[] totalMultipliers = new float[len];
    float[] additions = new float[len];
    int miningLevel = -1;

    public StatBoosts() {
        for(Integer i : Utils.range(0, len)) {
            baseMultipliers[i] = 1f;
            totalMultipliers[i] = 1f;
            additions[i] = 0;
        }
    }
    public StatBoosts multiplyBase(int stat, float amount) {
        baseMultipliers[stat] *= amount;
        return this;
    }
    public StatBoosts multiplyTotal(int stat, float amount) {
        totalMultipliers[stat] *= amount;
        return this;
    }
    public StatBoosts add(int stat, float amount) {
        additions[stat] += amount;
        return this;
    }
    public StatBoosts changeMiningLevel(int level) {
        miningLevel = Math.max(miningLevel, level);
        return this;
    }
    public StatBoosts copy() {
        StatBoosts res = new StatBoosts();
        for(Integer i : Utils.range(0, len)) {
            res.baseMultipliers[i] = baseMultipliers[i];
            res.totalMultipliers[i] = totalMultipliers[i];
            res.additions[i] = additions[i];
        }
        res.miningLevel = miningLevel;
        return res;
    }

    public static StatBoosts merge_destructive(Stream<StatBoosts> boosts) {
        BinaryOperator<StatBoosts> op = (b1, b2) -> {
            for(Integer j : Utils.range(0, len)) {
                b1.baseMultipliers[j] *= b2.baseMultipliers[j];
                b1.totalMultipliers[j] *= b2.totalMultipliers[j];
                b1.additions[j] += b2.additions[j];
            }
            b1.changeMiningLevel(b2.miningLevel);
            return b1;
        };
        return boosts.reduce(new StatBoosts(), op, op);
    }

    public float apply(int eStat, float oldValue) {
        return (baseMultipliers[eStat] * oldValue + additions[eStat]) * totalMultipliers[eStat];
    }
    public int applyMiningLevel(int oldMiningLevel) {
        return Math.max(miningLevel, oldMiningLevel);
    }
}
