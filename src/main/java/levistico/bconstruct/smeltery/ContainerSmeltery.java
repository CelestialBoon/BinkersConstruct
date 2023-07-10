package levistico.bconstruct.smeltery;

import net.minecraft.src.IInventory;
import net.minecraft.src.Slot;
import sunsetsatellite.fluidapi.template.containers.ContainerMultiFluidTank;
import sunsetsatellite.fluidapi.template.tiles.TileEntityMassFluidItemContainer;

public class ContainerSmeltery extends ContainerMultiFluidTank {
    public ContainerSmeltery(IInventory iInventory, TileEntityMassFluidItemContainer tileEntity) {
        super(iInventory, tileEntity);

        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                addSlot(new Slot(tileEntity,k,12+(i*22),16+(j*18)));
                k++;
            }
        }
    }

}
