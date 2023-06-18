package levistico.bconstruct.smeltery;

import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.fluidapi.api.FluidStack;
import sunsetsatellite.fluidapi.render.RenderFluid;
import sunsetsatellite.fluidapi.template.tiles.TileEntityMultiFluidTank;
import sunsetsatellite.sunsetutils.util.RenderBlockSimple;
import sunsetsatellite.sunsetutils.util.multiblocks.IMultiblock;
import sunsetsatellite.sunsetutils.util.multiblocks.Structure;

import java.util.Collection;

public class RenderSmeltery extends TileEntitySpecialRenderer {
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double d2, double d4, double d6, float f8) {
        boolean complete = true;
        int i = tileEntity.xCoord;
        int j = tileEntity.yCoord;
        int k = tileEntity.zCoord;
        World world = this.tileEntityRenderer.renderEngine.minecraft.theWorld;
        if(tileEntity instanceof IMultiblock){
            Collection blocks = ((IMultiblock) tileEntity).getMultiblock().data.getCompoundTag("Data").func_28110_c();
            Collection subs = ((IMultiblock) tileEntity).getMultiblock().data.getCompoundTag("Substitutions").func_28110_c();

            for (Object block : blocks) {
                int x = ((NBTTagCompound) block).getInteger("x");
                int y = ((NBTTagCompound) block).getInteger("y");
                int z = ((NBTTagCompound) block).getInteger("z");
                int id = Structure.getBlockId((NBTTagCompound) block);
                int meta = ((NBTTagCompound) block).getInteger("meta");
                if((Structure.getBlockId((NBTTagCompound) block) != tileEntity.getBlockType().blockID)){
                    if(world.getBlockId(i+x,j+y,k+z) != id || (world.getBlockId(i+x,j+y,k+z) == id && world.getBlockMetadata(i+x,j+y,k+z) != meta)){
                        boolean foundSub = false;
                        for (Object sub : subs) {
                            int subX = ((NBTTagCompound) sub).getInteger("x");
                            int subY = ((NBTTagCompound) sub).getInteger("y");
                            int subZ = ((NBTTagCompound) sub).getInteger("z");
                            int subId = Structure.getBlockId((NBTTagCompound) sub);
                            int subMeta = ((NBTTagCompound) sub).getInteger("meta");
                            if(subX == x && subY == y && subZ == z){
                                if(world.getBlockId(i+x,j+y,k+z) == subId && world.getBlockMetadata(i+x,j+y,k+z) == subMeta){
                                    foundSub = true;
                                }
                            }
                        }
                        if(!foundSub){
                            complete = false;
                            GL11.glPushMatrix();
                            GL11.glDisable(GL11.GL_LIGHTING);
                            GL11.glColor4f(1f,0,0,1.0f);
                            GL11.glTranslatef((float)d2+x, (float)d4+y, (float)d6+z);
                            drawBlock(this.getFontRenderer(),
                                    this.tileEntityRenderer.renderEngine,
                                    id,
                                    meta,
                                    i,
                                    j,
                                    k,
                                    tileEntity);
                            GL11.glEnable(GL11.GL_LIGHTING);
                            GL11.glPopMatrix();
                        }
                    }
                }
            }
        }

        if(complete){
            TileEntityMultiFluidTank tile = (TileEntityMultiFluidTank) tileEntity;
            float fluidAmount = 0;
            float fluidMaxAmount = 1;
            int fluidId = 0;

            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    float l = 0;
                    for (FluidStack fluidStack : tile.fluidContents) {
                        fluidMaxAmount = tile.fluidCapacity;
                        fluidAmount = fluidStack.amount;
                        fluidId = fluidStack.getLiquid().blockID;
                        float amount = Math.abs((fluidAmount / fluidMaxAmount) - 0.01f);
                        if(fluidId != 0){
                            GL11.glPushMatrix();
                            GL11.glTranslatef((float)d2+x+1, (float)d4+l, (float)d6+z-2);
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
