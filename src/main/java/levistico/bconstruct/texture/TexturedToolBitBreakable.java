package levistico.bconstruct.texture;

import levistico.bconstruct.tools.EToolBit;

public class TexturedToolBitBreakable implements ITexturedPart {
    public Integer[] iconIndexWorkingArray;
    public Integer[] iconIndexBrokenArray;
    public final EToolBit workingBit;
    public final EToolBit brokenBit;


    public TexturedToolBitBreakable(EToolBit workingBit, EToolBit brokenBit) {
        this.workingBit = workingBit;
        this.brokenBit = brokenBit;
//        this.iconIndexWorkingList = GBitsUtil.generateMaterialdBit(workingTexture, true);
//        this.iconIndexBrokenList = GBitsUtil.generateMaterialdBit(brokenTexture, true);
    }

    @Override
    public int getIconIndex(int material, boolean broken) {
        if(broken) return iconIndexBrokenArray[material];
        else return iconIndexWorkingArray[material];
    }
}
