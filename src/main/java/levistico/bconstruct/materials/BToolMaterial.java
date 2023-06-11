package levistico.bconstruct.materials;

import levistico.bconstruct.utils.Utils;
import net.minecraft.src.command.ChatColor;
import net.minecraft.src.helper.Color;

public final class BToolMaterial {

    //TODO extra properties to follow (like base properties, upgradability, etc.)
    private String name;
    public final int eNumber;
    private int mobDamage;
    private int miningLevel;
    private int durability;
    private float efficiency;
    public final ChatColor chatcolor;
    public final int partsFlag;

    public final Color color;

    public BToolMaterial(String name, String hexColor, Integer eNumber, int durability, float efficiency, int mobDamage, int miningLevel, int partsFlag) {
        this.name = name;
        this.eNumber = eNumber;
        this.durability = durability;
        this.efficiency = efficiency;
        this.mobDamage = mobDamage;
        this.miningLevel = miningLevel;
        this.chatcolor = ChatColor.cyan;
        this.partsFlag = partsFlag;
        this.color = Utils.colorFromString(hexColor);
//        assert (eNumber == BToolMaterials.matArray.size());
        Utils.setAt(BToolMaterials.matArray, eNumber, this);
    }


    public String getName() {
        return name;
    }

    public int getMobDamage() {
        return mobDamage;
    }

    public int getMiningLevel() {
        return miningLevel;
    }

    public int getDurability() {
        return durability;
    }

    public float getEfficiency() {
        return efficiency;
    }

}
