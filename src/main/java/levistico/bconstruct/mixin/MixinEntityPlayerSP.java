package levistico.bconstruct.mixin;

import levistico.bconstruct.gui.containers.GUICraftingStation;
import levistico.bconstruct.gui.containers.GUIPartBuilder;
import levistico.bconstruct.gui.containers.GUIToolStation;
import levistico.bconstruct.gui.containers.CraftingTileEntity;
import levistico.bconstruct.mixinInterfaces.IBinkersEntityPlayerSP;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = EntityPlayerSP.class, remap = false)
public class MixinEntityPlayerSP implements IBinkersEntityPlayerSP {
    @Shadow
    protected Minecraft mc;

    @Override
    public void displayGUICraftingStation(CraftingTileEntity tileEntity) {
        this.mc.displayGuiScreen(new GUICraftingStation(((EntityPlayerSP)(Object)this).inventory, tileEntity));
    }

    @Override
    public void displayGUIPartBuilder(CraftingTileEntity tileEntity) {
        this.mc.displayGuiScreen(new GUIPartBuilder(((EntityPlayerSP)(Object)this).inventory, tileEntity));
    }

    @Override
    public void displayGUIToolStation(CraftingTileEntity tileEntity) {
        this.mc.displayGuiScreen(new GUIToolStation(((EntityPlayerSP)(Object)this).inventory, tileEntity));
    }
}
