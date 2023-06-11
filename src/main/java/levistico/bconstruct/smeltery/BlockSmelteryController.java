package levistico.bconstruct.smeltery;

import net.minecraft.src.BlockContainerRotatable;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;

public class BlockSmelteryController extends BlockContainerRotatable {
    public BlockSmelteryController(int i) {
        super(i, Material.rock);
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntitySmelteryController();
    }
}
