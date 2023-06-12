package levistico.bconstruct.gui.texture;


import levistico.bconstruct.BConstruct;
import levistico.bconstruct.materials.BToolMaterial;
import levistico.bconstruct.materials.BToolMaterials;
import levistico.bconstruct.materials.EToolMaterial;
import levistico.bconstruct.parts.BToolParts;
import levistico.bconstruct.parts.EToolPart;
import levistico.bconstruct.tools.BTool;
import levistico.bconstruct.tools.BTools;
import levistico.bconstruct.tools.EToolBit;
import levistico.bconstruct.utils.Utils;
import net.minecraft.src.TextureFX;
import net.minecraft.src.helper.Color;
import net.minecraft.src.helper.Textures;
import turniplabs.halplibe.util.TextureHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static levistico.bconstruct.BConstruct.mc;
import static net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES;


public class TextureUtils {
    private static final String baseFolder = "/assets/" + BConstruct.MOD_ID + "/item";
    public static final ArrayList<String> bitsLocation = new ArrayList<>();

    // TODO here go all the disparate graphical bits of the tools (but not really, see below)
    // except that we have to handle effects too. And that would get really fucky with individual things like here
    // so the alternative is to implement an automated scraping of the resources to find the congruent parts, much like 3.6 tinkers does. Yeah that can do
    // each tool generates its own set of graphical bits by asking some utils class to fetch the various parts, which are kept cached
    // in turn, each part is a collection of links to the various possible textures for each different material, which takes quite a bit
    // and which has to select on render the index to give depending on the itemstack's data (material data)
    // this seems like it could work

    // use public int RenderEngine::allocateAndSetupTexture(BufferedImage bufferedimage)

    // BufferedImage::public void setRGB(int x, int y, int rgb)
//    public static Map<String, List<Integer>> textureMap = new HashMap<>();

//    public static final String TOOL_PARTS_TEXTURE = "binkerstoolparts";
//    public static final String TOOL_BITS_TEXTURE = "binkerstoolbits";

    public static int GUI_ICONS_INDEX;
    public static int GUI_BASE_CRAFTING_INDEX;
//    public static int GUI_INVENTORY_INDEX;

    public static int TOOL_PARTS_TEXTURE_INDEX;
    public static int TOOL_BITS_TEXTURE_INDEX;

    public static final float LARGE_TEXTURE_FACTOR = (float) 1 / (18*14);
    public static final float REGULAR_TEXTURE_FACTOR = (float) 1.0F / (float)(TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthItems);
    public static final int MC_ITEMS_TEXTURE_INDEX = BConstruct.mc.renderEngine.getTexture("/gui/items.png");

    //TODO eventually there's gonna be too many bits for the required square 32x32 image, and we're gonna have to split it into multiple images,
    // and have some functions that deal with alla that (including some smart splitting so that the same bits don't get split across different images)
    public static Integer[][] toolBitsIndexArray = new Integer[EToolBit.values().length][EToolMaterial.values().length];

    public static void importGUITextures() {
        String guiFolder = String.format("/assets/%s/gui/", BConstruct.MOD_ID);
        BufferedImage texture = Textures.readImage(TextureHandler.class.getResourceAsStream(guiFolder + "icons.png"));
        GUI_ICONS_INDEX = mc.renderEngine.allocateAndSetupTexture(texture);

        texture = Textures.readImage(TextureHandler.class.getResourceAsStream(guiFolder + "bcrafting.png"));
        GUI_BASE_CRAFTING_INDEX = mc.renderEngine.allocateAndSetupTexture(texture);
    }

    public static void generateToolPartsTexture() {
        int tplen = EToolPart.values().length;
        int resolution = 16;
        //generate whole texture
        BufferedImage texture = new BufferedImage(resolution * TEXTURE_ATLAS_WIDTH_TILES,
                resolution * TEXTURE_ATLAS_WIDTH_TILES,
                BufferedImage.TYPE_INT_ARGB);

//        BufferedImage guiTexture = ((AccessorRenderEngine)mc.renderEngine).getTextureNameToImageMap().get(GraphicsUtils.GUI_TEXTURE_INDEX);

        int texi = 0;
        Color bColor = new Color();
        List<Integer> iconIndexList = new ArrayList<>();
        for(int tpi = 0; tpi < tplen; tpi++) {
            String path = String.format("%s/toolpart/%s.png", baseFolder, BToolParts.baseTextureArray.get(tpi));
            BufferedImage baseImage = Textures.readImage(TextureHandler.class.getResourceAsStream(path));

//            int tpx = guiCounter % TEXTURE_ATLAS_WIDTH_TILES;
//            int tpy = guiCounter / TEXTURE_ATLAS_WIDTH_TILES;
//            int tpsx = tpx * resolution;
//            int tpsy = tpy * resolution;
//            int[] rgbArray = new int[resolution * resolution];
//            guiTexture.setRGB(tpsx, tpsy, resolution, resolution, baseImage.getRGB(0,0,resolution, resolution, rgbArray, 0, resolution), 0, resolution);
//            BToolParts.partArray[tpi].baseTextureUV = new Pair<>(tpx, tpy);
//            guiCounter++;

            for (BToolMaterial mat : BToolMaterials.matArray) {
                int ix = texi % TEXTURE_ATLAS_WIDTH_TILES;
                int iy = texi / TEXTURE_ATLAS_WIDTH_TILES;
                int sx = ix * resolution;
                int sy = iy * resolution;

                Color matColor = mat.color;

                //transform the base image into final material image
                for(int x = 0; x < resolution; ++x) {
                    for (int y = 0; y < resolution; ++y) {
                        bColor.setARGB(baseImage.getRGB(x, y));
                        int red = bColor.getRed() * matColor.getRed() / 255;
                        int green = bColor.getGreen() * matColor.getGreen() / 255;
                        int blue = bColor.getBlue() * matColor.getBlue() / 255;
                        int alpha = bColor.getAlpha() * matColor.getAlpha() / 255;
                        Color rColor = new Color().setRGBA(red, green, blue, alpha);
                        texture.setRGB(sx + x, sy + y, rColor.value);
                    }
                }
                iconIndexList.add(texi);
                texi++;
            }
            BToolParts.partArray.get(tpi).texturedPart = new TexturedToolPart(iconIndexList);
            iconIndexList = new ArrayList<>();
        }

        TOOL_PARTS_TEXTURE_INDEX = mc.renderEngine.allocateAndSetupTexture(texture);

        File outputFile = new File("testToolPartTextures.png");
        try {
            ImageIO.write(texture, "png", outputFile);
        } catch (IOException e) {
            BConstruct.LOGGER.error("couldn't write file");
        }
    }

    public static void generateToolBitsTexture() {
        int tblen = EToolBit.values().length;
        int resolution = 16;
        //generate whole texture
        BufferedImage texture = new BufferedImage(resolution * TEXTURE_ATLAS_WIDTH_TILES,
                resolution * TEXTURE_ATLAS_WIDTH_TILES,
                BufferedImage.TYPE_INT_ARGB);


        int texi = 0;
        Color bColor = new Color();
        for(int tpi = 0; tpi < tblen; tpi++) {
            String textureSource = String.format("%s/tool/%s.png",baseFolder, bitsLocation.get(tpi));
            BufferedImage baseImage = Textures.readImage(TextureHandler.class.getResourceAsStream(textureSource));
            for (BToolMaterial mat : BToolMaterials.matArray) {
                int ix = texi % TEXTURE_ATLAS_WIDTH_TILES;
                int iy = texi / TEXTURE_ATLAS_WIDTH_TILES;
                int sx = ix * resolution;
                int sy = iy * resolution;

                Color matColor = mat.color;

                //transform the base image into final material image
                for(int x = 0; x < resolution; ++x) {
                    for (int y = 0; y < resolution; ++y) {
                        bColor.setARGB(baseImage.getRGB(x, y));
                        int red = bColor.getRed() * matColor.getRed() / 255;
                        int green = bColor.getGreen() * matColor.getGreen() / 255;
                        int blue = bColor.getBlue() * matColor.getBlue() / 255;
                        int alpha = bColor.getAlpha() * matColor.getAlpha() / 255;
                        if(red == 0 && green == 0 && blue == 0) alpha = 0; //TODO what the fuck why is this necessary ON SOME COMPUTERS?
                        Color rColor = new Color().setRGBA(red, green, blue, alpha);
                        texture.setRGB(sx + x, sy + y, rColor.value);
                    }
                }
                toolBitsIndexArray[tpi][mat.eNumber] = texi;
                texi++;
            }
        }

        TOOL_BITS_TEXTURE_INDEX = mc.renderEngine.allocateAndSetupTexture(texture);

        File outputFile = new File("testToolBitsTextures.png");
        try {
            ImageIO.write(texture, "png", outputFile);
        } catch (IOException e) {
            BConstruct.LOGGER.error("couldn't write file");
        }
        //now for assigning the bits to each tool
        for(BTool tool : BTools.toolList) {
            for(ITexturedPart bit : tool.texturedParts) {
                if (bit instanceof TexturedToolBitReliable) {
                    TexturedToolBitReliable relbit = (TexturedToolBitReliable) bit;
                    relbit.iconIndexArray = toolBitsIndexArray[relbit.bit.ordinal()];
                } else if (bit instanceof TexturedToolBitBreakable) {
                    TexturedToolBitBreakable brbit = (TexturedToolBitBreakable) bit;
                    brbit.iconIndexWorkingArray = toolBitsIndexArray[brbit.workingBit.ordinal()];
                    brbit.iconIndexBrokenArray = toolBitsIndexArray[brbit.brokenBit.ordinal()];
                } else throw new UnsupportedOperationException();
            }
        }
    }

    /*public static void generateToolBaseTexture() {
        int resolution = 16;
        BufferedImage guiTexture = ((AccessorRenderEngine)mc.renderEngine).getTextureNameToImageMap().get(GraphicsUtils.GUI_TEXTURE_INDEX);
        for(BTool tool: BTools.toolList) {
            String path = String.format("%s/tool/%s/base.png", baseFolder, tool.toolFolder);
            BufferedImage baseImage = Textures.readImage(TextureHandler.class.getResourceAsStream(path));

            int tpx = guiCounter % TEXTURE_ATLAS_WIDTH_TILES;
            int tpy = guiCounter / TEXTURE_ATLAS_WIDTH_TILES;
            int tpsx = tpx * resolution;
            int tpsy = tpy * resolution;
            int[] rgbArray = new int[resolution * resolution];
            guiTexture.setRGB(tpsx, tpsy, resolution, resolution, baseImage.getRGB(0, 0, resolution, resolution, rgbArray, 0, resolution), 0, resolution);
            tool.baseTextureIndex = new Pair<>(tpx, tpy);
            guiCounter++;
        }
    }*/

/*
    public static List<Integer> generateEffects(String toolName) {
        throw new UnsupportedOperationException();
    }
    */

    static {
        Utils.setAt(bitsLocation, EToolBit.BasicHandle.ordinal(), "basic_handle");

        Utils.setAt(bitsLocation, EToolBit.Hatchet_Binding.ordinal(), "hatchet/binding");
        Utils.setAt(bitsLocation, EToolBit.Hatchet_Head.ordinal(), "hatchet/head");
        Utils.setAt(bitsLocation, EToolBit.Hatchet_Head_Broken.ordinal(), "hatchet/head_broken");

        Utils.setAt(bitsLocation, EToolBit.Mattock_BackHead.ordinal(), "mattock/back_head");
        Utils.setAt(bitsLocation, EToolBit.Mattock_BackHead_Broken.ordinal(), "mattock/back_head_broken");
        Utils.setAt(bitsLocation, EToolBit.Mattock_FrontHead.ordinal(), "mattock/front_head");
        Utils.setAt(bitsLocation, EToolBit.Mattock_FrontHead_Broken.ordinal(), "mattock/front_head_broken");

        Utils.setAt(bitsLocation, EToolBit.Pickaxe_Binding.ordinal(), "pickaxe/binding");
        Utils.setAt(bitsLocation, EToolBit.Pickaxe_Head.ordinal(), "pickaxe/head");
        Utils.setAt(bitsLocation, EToolBit.Pickaxe_Head_Broken.ordinal(), "pickaxe/head_broken");

        Utils.setAt(bitsLocation, EToolBit.Broadsword_Blade.ordinal(), "sword/blade");
        Utils.setAt(bitsLocation, EToolBit.Broadsword_Blade_Broken.ordinal(), "sword/blade_broken");
        Utils.setAt(bitsLocation, EToolBit.Broadsword_Guard.ordinal(), "sword/guard");
        Utils.setAt(bitsLocation, EToolBit.Broadsword_Handle.ordinal(), "sword/handle");
    }
}
