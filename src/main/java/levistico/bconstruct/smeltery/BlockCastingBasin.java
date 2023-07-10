package levistico.bconstruct.smeltery;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.BlockContainerRotatable;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import sunsetsatellite.fluidapi.template.tiles.TileEntityFluidTank;

public class BlockCastingBasin extends BlockContainer {
    public BlockCastingBasin(int i, Material material) {
        super(i, material);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityCastingBasin();
    }
}
