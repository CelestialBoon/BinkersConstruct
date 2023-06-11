package levistico.bconstruct.gui.texture;

import java.util.List;

public class TexturedToolPart implements ITexturedPart {

    public final List<Integer> iconIndexList;

    public TexturedToolPart(List<Integer> iconIndexList) {
        this.iconIndexList = iconIndexList;
    }

    @Override
    public int getIconIndex(int material, boolean broken) {
        return iconIndexList.get(material);
    }
}