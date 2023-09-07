package levistico.bconstruct.materials;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.parts.PartsFlag;
import levistico.bconstruct.tools.properties.Properties;
import levistico.bconstruct.tools.properties.Property;
import levistico.bconstruct.utils.IHasTranslateKey;
import levistico.bconstruct.utils.Utils;
import net.minecraft.core.net.command.TextFormatting;

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
    public final TextFormatting chatcolor;
    public final int possiblePartsFlag;

    public List<Property> headProperties = new ArrayList<>();
    public List<Property> bindingProperties = new ArrayList<>();
    public List<Property> handleProperties = new ArrayList<>();

    public final net.minecraft.core.util.helper.Color color;

    public BToolMaterial(String name, String hexColor, Integer eNumber, int durability, float efficiency, int mobDamage, int miningLevel, int possiblePartsFlag) {
        this.name = name;
        this.translateKey = String.format("material.%s.%s", BConstruct.MOD_ID, name);
        this.eNumber = eNumber;
        this.durability = durability;
        this.efficiency = efficiency;
        this.mobDamage = mobDamage;
        this.miningLevel = miningLevel;
        this.chatcolor = TextFormatting.CYAN;
        this.possiblePartsFlag = possiblePartsFlag;
        this.color = Utils.colorFromString(hexColor);
        Utils.setAt(BToolMaterials.matList, eNumber, this);
    }

    public BToolMaterial addHeadProperty(String name, Integer level) {
        headProperties.add(Properties.generate(name, level));
        return this;
    }
    public BToolMaterial addBindingProperty(String name, Integer level) {
        bindingProperties.add(Properties.generate(name, level));
        return this;
    }
    public BToolMaterial addHandleProperty(String name, Integer level) {
        handleProperties.add(Properties.generate(name, level));
        return this;
    }
    public BToolMaterial addAllProperty(String name, Integer level) {
        headProperties.add(Properties.generate(name, level));
        bindingProperties.add(Properties.generate(name, level));
        handleProperties.add(Properties.generate(name, level));
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
