package levistico.bconstruct.smeltery;

import levistico.bconstruct.BConstruct;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.sunsetutils.SunsetUtils;
import sunsetsatellite.sunsetutils.util.Direction;
import sunsetsatellite.sunsetutils.util.RenderBlockSimple;
import sunsetsatellite.sunsetutils.util.Vec3f;

public class RenderSmelteryDrain extends TileEntitySpecialRenderer {

    private final RenderBlockSimple blockRenderer = new RenderBlockSimple();
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float w) {
        if(tileEntity.getBlockType() != null){
            TileEntitySmelteryDrain drain = (TileEntitySmelteryDrain) tileEntity;
            if(drain.activated && drain.drainingStack != null && drain.drainingStack.getLiquid() != null){
                Direction dir = Direction.getDirectionFromSide(drain.getBlockMetadata());
                GL11.glPushMatrix();
                Vec3f scale = new Vec3f(0.33,1.5,0.33);
                Vec3f position = new Vec3f(x,y,z).add(scale).add(dir.getOpposite().getVecF().multiply(0.66)).add(new Vec3f(0,-2.5,0));//.add(new Vec3f(1).multiply(dir.getOpposite().getVecF()));
                GL11.glTranslated(position.x,position.y,position.z);
                GL11.glScaled(scale.x,scale.y,scale.z);
                int color = drain.drainingStack.getLiquid().getRenderColor(0);
                if (color != 16777215) {
                    float r = (float)(color >> 16 & 255) / 255.0F;
                    float g = (float)(color >> 8 & 255) / 255.0F;
                    float b = (float)(color & 255) / 255.0F;
                    GL11.glColor3f(r, g, b);
                } else {
                    GL11.glColor3f(1.0F, 1.0F, 1.0F);
                }
                //BConstruct.LOGGER.info(String.valueOf(position));
                drawBlock(this.getFontRenderer(),this.tileEntityRenderer.renderEngine, drain.drainingStack.getLiquid().blockID, 0, drain );
                GL11.glPopMatrix();
            }
        }
    }

    public void drawBlock(FontRenderer fontrenderer, RenderEngine renderengine, int id, int meta, TileEntity tile) {
        renderengine.bindTexture(renderengine.getTexture("/terrain.png"));
        Block block = Block.blocksList[id];
        GL11.glPushMatrix();
        this.blockRenderer.renderBlock(block, meta, renderengine.minecraft.theWorld, tile.xCoord, tile.yCoord, tile.zCoord);
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_CULL_FACE);
    }
}
