package levistico.bconstruct.parts;

import bta.ModLoader;
import levistico.bconstruct.materials.BToolMaterial;
import levistico.bconstruct.materials.BToolMaterials;
import levistico.bconstruct.texture.TexturedToolPart;
import levistico.bconstruct.utils.Pair;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;


public class BToolPart extends Item {
//    public static final String MATERIAL = "material";
    public final String name;
    public final Integer weight;
    public final Pair<Integer, Integer> baseTextureUV;
    public final boolean isNeedsPattern;
    public final int partFlag;
    public final EToolPart eToolPart;
    public TexturedToolPart texturedPart;

    public BToolPart(int i, String name, EToolPart eToolPart, int partFlag, Integer weight, boolean isNeedsPattern, Pair<Integer, Integer> baseTextureUV) {
        super(i);
        this.name = name;
        this.eToolPart = eToolPart;
        this.partFlag = partFlag;
        this.weight = weight;
        this.isNeedsPattern = isNeedsPattern;
        this.baseTextureUV = baseTextureUV;
    }

    public static BToolMaterial getToolMaterial(ItemStack stack) {
        return BToolMaterials.matArray.get(stack.getMetadata());

    }
    public void setToolMaterial(ItemStack stack, BToolMaterial material) {
        stack.setMetadata(material.eNumber);
    }
}
