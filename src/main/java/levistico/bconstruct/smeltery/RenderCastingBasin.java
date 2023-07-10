package levistico.bconstruct.smeltery;

import net.minecraft.src.RenderHelper;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TileEntity;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.fluidapi.render.RenderFluid;
import sunsetsatellite.fluidapi.render.RenderFluidInBlock;
import sunsetsatellite.sunsetutils.util.Direction;
import sunsetsatellite.sunsetutils.util.RenderBlockSimple;

public class RenderCastingBasin extends RenderFluidInBlock {

    private final RenderBlockSimple sideRenderer = new RenderBlockSimple();
    @Override
    public void renderTileEntityAt(TileEntity tileEntity1, double d2, double d4, double d6, float f8) {
        super.renderTileEntityAt(tileEntity1, d2, d4, d6, f8);
        if(tileEntity1.getBlockType() != null){
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            sideRenderer.renderTopFace(tileEntity1.getBlockType(), d2, d4-0.99, d6, tileEntity1.getBlockType().getBlockTextureFromSideAndMetadata(Direction.getFromName("UP").getOpposite().getSide(), 0));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, -1.0F);
            sideRenderer.renderEastFace(tileEntity1.getBlockType(), d2, d4, d6+0.99, tileEntity1.getBlockType().getBlockTextureFromSideAndMetadata(Direction.getFromName("EAST").getOpposite().getSide(), 0));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, 1.0F);
            sideRenderer.renderWestFace(tileEntity1.getBlockType(), d2, d4, d6-0.99, tileEntity1.getBlockType().getBlockTextureFromSideAndMetadata(Direction.getFromName("WEST").getOpposite().getSide(), 0));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(-1.0F, 0.0F, 0.0F);
            sideRenderer.renderNorthFace(tileEntity1.getBlockType(), d2+0.99, d4, d6, tileEntity1.getBlockType().getBlockTextureFromSideAndMetadata(Direction.getFromName("NORTH").getOpposite().getSide(), 0));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            sideRenderer.renderSouthFace(tileEntity1.getBlockType(), d2-0.99, d4, d6, tileEntity1.getBlockType().getBlockTextureFromSideAndMetadata(Direction.getFromName("SOUTH").getOpposite().getSide(), 0));
            tessellator.draw();
            GL11.glColor3f(1,1,1);
        }
    }
}
