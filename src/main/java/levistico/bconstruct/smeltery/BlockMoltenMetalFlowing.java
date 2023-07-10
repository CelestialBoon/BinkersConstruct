package levistico.bconstruct.smeltery;

import net.minecraft.src.*;

public class BlockMoltenMetalFlowing extends BlockFluidFlowing {

    public int color;
    public BlockMoltenMetalFlowing(int i, Material material, int color) {
        super(i, material);
        this.color = color;
    }

    @Override
    public int colorMultiplier(World world, IBlockAccess iblockaccess, int i, int j, int k) {
        return color;
    }

    @Override
    public int getRenderColor(int i) {
        return color;
    }
}
