package levistico.bconstruct.tools.properties;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.tools.modifiers.Modifier;
import levistico.bconstruct.utils.IHasTranslateKey;
import levistico.bconstruct.utils.Utils;

public class Property implements IHasTranslateKey {
    public final String name;
    public final String translateKey;
    public final Modifier modifier;
    public int level;

    public Property(String name, Modifier modifier, int level) {
        this.name = name;
        this.translateKey = String.format("property.%s.%s", BConstruct.MOD_ID, name);
        this.modifier = modifier;
        this.level = Math.min(level, modifier.maxLevel);
    }

    public boolean addLevel(int level) {
        if(this.level == modifier.maxLevel) return false;
        this.level = Math.min(this.level + level, modifier.maxLevel);
        return true;
    }
    @Override
    public String getTranslateKey() {
        return translateKey;
    }
}
