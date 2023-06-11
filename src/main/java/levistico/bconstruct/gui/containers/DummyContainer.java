package levistico.bconstruct.gui.containers;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;

public final class DummyContainer extends Container {
    @Override
    public void quickMoveItems(int i, EntityPlayer entityPlayer, boolean bl, boolean bl2) {

    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
        return false;
    }
}
