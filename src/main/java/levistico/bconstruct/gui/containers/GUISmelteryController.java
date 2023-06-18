package levistico.bconstruct.gui.containers;

import levistico.bconstruct.crafting.BContainer;
import levistico.bconstruct.smeltery.ContainerSmeltery;
import levistico.bconstruct.smeltery.TileEntitySmelteryController;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import net.minecraft.src.helper.Color;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.fluidapi.FluidAPI;
import sunsetsatellite.fluidapi.api.FluidLayer;
import sunsetsatellite.fluidapi.api.FluidStack;
import sunsetsatellite.fluidapi.render.RenderFluid;
import sunsetsatellite.fluidapi.template.gui.GuiMultiFluidTank;
import sunsetsatellite.fluidapi.template.tiles.TileEntityMassFluidItemContainer;

public class GUISmelteryController extends GuiMultiFluidTank {
    public GUISmelteryController(InventoryPlayer inventoryPlayer, TileEntity tile) {
        super(inventoryPlayer, tile);
        name = "Smeltery Controller";
        this.inventorySlots = new ContainerSmeltery((IInventory) inventoryPlayer, (TileEntityMassFluidItemContainer) tile);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f) {
        int i = this.mc.renderEngine.getTexture("assets/bconstruct/gui/smeltery_prototype.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(i);
        int j = (this.width - this.xSize) / 2;
        int k = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
        this.drawFluidTank(j + 90, k + 15, 54, 55);
        TileEntitySmelteryController smeltery = (TileEntitySmelteryController) tile;
        if(smeltery.tank != null){
            FluidStack fluidStack = smeltery.tank.fluidContents[0];
            if (fluidStack.liquid != null) {
                BlockFluid fluid = fluidStack.liquid;
                int fluidBarSize = (int) FluidAPI.map(fluidStack.amount,0,smeltery.tank.fluidCapacity[0],2,54-2);
                RenderFluid.drawFluidIntoGui(fontRenderer, this.mc.renderEngine, fluid.blockID, 0, fluid.getBlockTextureFromSide(0), j+153, k+15+55-fluidBarSize-2, 14-2, fluidBarSize);
            }
        }
        int l = 0;
        for (int m = 0; m < 3; m++) {
            for (int n = 0; n < 3; n++) {
                this.mc.renderEngine.bindTexture(i);
                int scaled = (((TileEntitySmelteryController) tile).progress.get(l) * 16) / 1000;
                drawTexturedModalRect(j+8+(m*22), k+16+(n*18) + 16 - scaled,176,165-scaled,3,scaled);
                l++;
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer() {
        this.fontRenderer.drawString(this.name, 44, 6, -12566464);
    }
}
