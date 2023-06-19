package levistico.bconstruct.materials;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.parts.PartsFlag;
import levistico.bconstruct.tools.properties.Property;
import levistico.bconstruct.utils.IHasTranslateKey;
import levistico.bconstruct.utils.Utils;
import net.minecraft.src.command.ChatColor;
import net.minecraft.src.helper.Color;

import java.util.ArrayList;
import java.util.List;

public final class BToolMaterial implements IHasTranslateKey {

    //TODO extra properties to follow (like base properties, upgradability, etc.)
    public final String name;
    public final String translateKey;
    public final int eNumber;
    private int mobDamage;
    private int miningLevel;
    private int durability;
    private float efficiency;
    public final ChatColor chatcolor;
    public final int possiblePartsFlag;

    public List<Property> headProperties = new ArrayList<>();
    public List<Property> bindingProperties = new ArrayList<>();
    public List<Property> handleProperties = new ArrayList<>();

    public final Color color;

    public BToolMaterial(String name, String hexColor, Integer eNumber, int durability, float efficiency, int mobDamage, int miningLevel, int possiblePartsFlag) {
        this.name = name;
        this.translateKey = String.format("material.%s.%s", BConstruct.MOD_ID, name);
        this.eNumber = eNumber;
        this.durability = durability;
        this.efficiency = efficiency;
        this.mobDamage = mobDamage;
        this.miningLevel = miningLevel;
        this.chatcolor = ChatColor.cyan;
        this.possiblePartsFlag = possiblePartsFlag;
        this.color = Utils.colorFromString(hexColor);
        Utils.setAt(BToolMaterials.matList, eNumber, this);
    }

    public BToolMaterial addHeadProperty(Property p) {
        headProperties.add(p);
        return this;
    }
    public BToolMaterial addBindingProperty(Property p) {
        bindingProperties.add(p);
        return this;
    }
    public BToolMaterial addHandleProperty(Property p) {
        handleProperties.add(p);
        return this;
    }
    public BToolMaterial addAllProperty(Property p) {
        headProperties.add(p);
        bindingProperties.add(p);
        handleProperties.add(p);
        return this;
    }
    public String getName() {
        return name;
    }
    public String getTranslateKey() {
        return translateKey;
    }

    public int getAttackDamage() {
        return mobDamage;
    }
    public int getMiningLevel() {
        return miningLevel;
    }
    public int getDurability() {
        return durability;
    }
    public float getMiningSpeed() {
        return efficiency;
    }

    public List<Property> getProperties(int partFlag) {
        if(partFlag == PartsFlag.HEAD) {
            return headProperties;
        } else if (partFlag == PartsFlag.BINDING) {
            return bindingProperties;
        } else if (partFlag == PartsFlag.HANDLE) {
            return handleProperties;
        } else throw new IllegalStateException("Wrong partflag given to Material::getProperty: " + partFlag);
    }
}
