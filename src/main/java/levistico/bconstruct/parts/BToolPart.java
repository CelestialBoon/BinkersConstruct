package levistico.bconstruct.parts;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.materials.BToolMaterial;
import levistico.bconstruct.materials.BToolMaterials;
import levistico.bconstruct.gui.texture.TexturedToolPart;
import levistico.bconstruct.utils.IHasTranslateKey;
import levistico.bconstruct.utils.Pair;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;


public class BToolPart extends Item implements IHasTranslateKey {
//    public static final String MATERIAL = "material";
    public final String name;
    public final String translateKey;
    public final Integer weight;
    public final Pair<Integer, Integer> baseTextureUV;
    public final boolean isNeedsPattern;
    public final int partFlag;
    public final EToolPart eToolPart;
    public TexturedToolPart texturedPart;

    public BToolPart(int i, String name, EToolPart eToolPart, int partFlag, Integer weight, boolean isNeedsPattern, Pair<Integer, Integer> baseTextureUV) {
        super(i);
        this.name = name;
        this.translateKey = String.format("toolpart.%s.%s", BConstruct.MOD_ID, name);
        this.eToolPart = eToolPart;
        this.partFlag = partFlag;
        this.weight = weight;
        this.isNeedsPattern = isNeedsPattern;
        this.baseTextureUV = baseTextureUV;
        this.notInCreativeMenu = true;
    }

    public static BToolMaterial getToolMaterial(ItemStack stack) {
        return BToolMaterials.matList.get(stack.getMetadata());
    }
    public void setToolMaterial(ItemStack stack, BToolMaterial material) {
        stack.setMetadata(material.eNumber);
    }


    @Override
    public String getTranslateKey() {
        return translateKey;
    }
}
