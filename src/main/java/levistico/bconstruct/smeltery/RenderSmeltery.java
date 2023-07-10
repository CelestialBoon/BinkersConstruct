package levistico.bconstruct.smeltery;

import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.fluidapi.api.FluidStack;
import sunsetsatellite.fluidapi.render.RenderFluid;
import sunsetsatellite.fluidapi.template.tiles.TileEntityMultiFluidTank;
import sunsetsatellite.sunsetutils.util.Direction;
import sunsetsatellite.sunsetutils.util.RenderBlockSimple;
import sunsetsatellite.sunsetutils.util.Vec3f;
import sunsetsatellite.sunsetutils.util.Vec3i;
import sunsetsatellite.sunsetutils.util.multiblocks.IMultiblock;
import sunsetsatellite.sunsetutils.util.multiblocks.RenderMultiblock;
import sunsetsatellite.sunsetutils.util.multiblocks.Structure;

import java.util.Collection;

public class RenderSmeltery extends RenderMultiblock {
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double d2, double d4, double d6, float f8) {
        super.renderTileEntityAt(tileEntity,d2,d4,d6,f8);
        TileEntitySmelteryController tile = (TileEntitySmelteryController) tileEntity;
        if(tile.multiblockValid){
            float fluidAmount = 0;
            float fluidMaxAmount = 1;
            int fluidId = 0;
            Direction dir = Direction.getDirectionFromSide(tileEntity.getBlockMetadata());

            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    float l = 0;
                    for (FluidStack fluidStack : tile.fluidContents) {
                        fluidMaxAmount = tile.fluidCapacity;
                        fluidAmount = fluidStack.amount;
                        fluidId = fluidStack.getLiquid().blockID;
                        float amount = Math.abs((fluidAmount / fluidMaxAmount) - 0.01f);
                        if(fluidId != 0){
                            Vec3f pos;
                            switch (dir){
                                case X_POS:
                                    pos = new Vec3f(z + d2 - 2, l + d4, x + d6 + 1);
                                    break;
                                case X_NEG:
                                    pos = new Vec3f(-z + d2 + 2, l + d4, -x + d6 - 1);
                                    break;
                                case Z_NEG:
                                    pos = new Vec3f(-x + d2-1, l + d4, -z + d6+2);
                                    break;
                                default:
                                    pos = new Vec3f(x + d2+1, l + d4, z + d6-2);
                                    break;
                            }
                            GL11.glPushMatrix();
                            GL11.glTranslatef((float)pos.x, (float)pos.y, (float)pos.z);
                            GL11.glRotatef(0.0f, 0.0F, 1.0F, 0.0F);
                            GL11.glScalef(1,amount,1);
                            Block block = Block.blocksList[fluidId];
                            int color = block.getRenderColor(0);
                            if (color != 16777215) {
                                float r = (float)(color >> 16 & 255) / 255.0F;
                                float g = (float)(color >> 8 & 255) / 255.0F;
                                float b = (float)(color & 255) / 255.0F;
                                GL11.glColor3f(r, g, b);
                            } else {
                                GL11.glColor3f(1.0F, 1.0F, 1.0F);
                            }
                            l+=1*amount;
                            GL11.glDisable(GL11.GL_LIGHTING);
                            drawBlock(this.getFontRenderer(), this.tileEntityRenderer.renderEngine.minecraft.renderEngine, fluidId, 0,0, 0, 0, tileEntity);
                            GL11.glEnable(GL11.GL_LIGHTING);

                            GL11.glPopMatrix();
                        }
                    }
                }
            }
        }
    }


    public void drawBlock(FontRenderer fontrenderer, RenderEngine renderengine, int i, int j, int k, int l, int i1, TileEntity tile) {
        renderengine.bindTexture(renderengine.getTexture("/terrain.png"));
        Block f1 = Block.blocksList[i];
        GL11.glPushMatrix();
        this.blockRenderer.renderBlock(f1, j, renderengine.minecraft.theWorld, tile.xCoord, tile.yCoord, tile.zCoord);
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_CULL_FACE);
    }

    private final RenderBlockSimple blockRenderer = new RenderBlockSimple();
}
