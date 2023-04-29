package levistico.bconstruct.texture;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.materials.BToolMaterial;
import levistico.bconstruct.utils.Utils;
import net.minecraft.src.dynamictexture.DynamicTexture;

import net.minecraft.src.helper.Color;
import net.minecraft.src.helper.Textures;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
/*

public class CachedColorableItemTexture extends DynamicTexture {
    private final String textureName;
    private final byte[] baseImage;
    private BToolMaterial currentMaterial;
    private Map<String, byte[]> cacheMap = new HashMap<>();

    private byte log(String s, int i) {
        if(i > 0) {
            BConstruct.LOGGER.info(s + i);
        }
        return (byte)i;
    }

    public void setMaterial(BToolMaterial material) {
        if(material == currentMaterial) return;
        else if(! cacheMap.containsKey(material.getName())) {
            byte[] newImage = new byte[baseImage.length];
            for(int x = 0; x < resolution; ++x) {
                for(int y = 0; y < resolution; ++y) {
                    Color matColor = material.color;

                    int i = (y * resolution + x) * 4;
                    //multiplicative mask
                    newImage[i]     = (byte)(matColor.getRed() * Utils.byteToInt(baseImage[i]) / 255);
                    newImage[i + 1] = (byte)(matColor.getGreen() * Utils.byteToInt(baseImage[i+1]) / 255);
                    newImage[i + 2] = (byte)(matColor.getBlue() * Utils.byteToInt(baseImage[i+2]) / 255);
                    newImage[i + 3] =(byte)(matColor.getAlpha() * Utils.byteToInt(baseImage[i+3]) / 255);
                }
            }
            cacheMap.put(material.getName(), newImage);
        }
        currentMaterial = material;
        System.arraycopy(cacheMap.get(material.getName()), 0, imageData, 0, imageData.length);
    }


    //"/gui/items.png", "/assets/" + modId + "/item/" + itemTexture, Block.texCoordToIndex(x, y), 16, 1
    public CachedColorableItemTexture(String textureName, String textureSource, int textureIndex) {
        super(textureIndex, 16, 1);
        int resolution = 16;
        int width = 1;
        this.textureName = textureName;
        BufferedImage image = Textures.readImage(turniplabs.halplibe.util.TextureHandler.class.getResourceAsStream(textureSource));
        if (image == Textures.missingTexture) {
            throw new RuntimeException("Texture " + textureSource + " couldn't be found!");
        } else if (image.getWidth() != resolution) {
            throw new RuntimeException("Texture " + textureSource + " doesn't have the same width as textures in " + textureName + "!");
        } else if (image.getHeight() % image.getWidth() != 0) {
            throw new RuntimeException("Invalid Height for texture! " + textureSource);
        } else {
            this.baseImage = new byte[resolution * resolution * 4];
            for(int x = 0; x < resolution; ++x) {
                for(int y = 0; y < resolution; ++y) {
                    int c = image.getRGB (x, y);
                    putPixel(this.baseImage, y * resolution + x, c);
                }
            }
            System.arraycopy(baseImage, 0, imageData, 0, baseImage.length);
        }
    }

    public void update() { }
    public String getTextureName() {
        return this.textureName;
    }
}
*/
