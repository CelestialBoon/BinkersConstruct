package levistico.bconstruct.mixin;

import levistico.bconstruct.gui.containers.GUICraftingStation;
import levistico.bconstruct.gui.containers.GUIPartBuilder;
import levistico.bconstruct.gui.containers.GUIToolStation;
import levistico.bconstruct.crafting.CraftingTileEntity;
import levistico.bconstruct.mixinInterfaces.IBinkersEntityPlayerSP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = EntityPlayerSP.class, remap = false)
public class MixinEntityPlayerSP implements IBinkersEntityPlayerSP {
    @Shadow
    protected Minecraft mc;

    @Override
    public void displayGUIScreen(GuiScreen guiScreen) {
        this.mc.displayGuiScreen(guiScreen);
    }
}
