package levistico.bconstruct.texture;


import levistico.bconstruct.tools.EToolBit;

public class TexturedToolBitReliable implements ITexturedPart {

    public Integer[] iconIndexArray;
    public final EToolBit bit;

    public TexturedToolBitReliable(EToolBit bit) {
        this.bit = bit;
    }

    @Override
    public int getIconIndex(int material, boolean broken) {
        return iconIndexArray[material];
    }
}
