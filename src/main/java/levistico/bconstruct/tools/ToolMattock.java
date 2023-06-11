package levistico.bconstruct.tools;

import levistico.bconstruct.parts.EToolPart;
import levistico.bconstruct.texture.TexturedToolBitBreakable;
import levistico.bconstruct.texture.TexturedToolBitReliable;
import levistico.bconstruct.utils.Pair;
import levistico.bconstruct.utils.Utils;
import net.minecraft.src.*;

import java.util.Arrays;

public class ToolMattock extends BTool {
    static Material[] axeMaterials = {Material.wood, Material.leaves, Material.pumpkin, Material.woodWet, Material.cactus};
    static Material[] shovelMaterials = { Material.grass, Material.ground, Material.clay, Material.sand};

    public ToolMattock(int id) {
        super(id,"Mattock", "mattock", Utils.concatTwoArrays(axeMaterials, shovelMaterials), new Pair<>(8,0));
        composition.add(EToolPart.axeHead);
        texturedParts.add(new TexturedToolBitBreakable(EToolBit.Mattock_FrontHead, EToolBit.Mattock_FrontHead_Broken));
        composition.add(EToolPart.shovelHead);
        texturedParts.add(new TexturedToolBitBreakable(EToolBit.Mattock_BackHead, EToolBit.Mattock_BackHead_Broken));
        composition.add(EToolPart.rod);
        texturedParts.add(new TexturedToolBitReliable(EToolBit.BasicHandle));
        renderOrder.add(2); //handle
        renderOrder.add(1); //shovel head
        renderOrder.add(0); //axe head
    }

    @Override
    public float getStrVsBlock(ItemStack itemstack, Block block) {
        if(isToolBroken(itemstack)) return 0.1f;
        if(Arrays.stream(axeMaterials).anyMatch(material -> material == block.blockMaterial)) {
            return getMaterials(itemstack)[0].getEfficiency();
        }
        if(Arrays.stream(shovelMaterials).anyMatch(material -> material == block.blockMaterial)) {
            return getMaterials(itemstack)[1].getEfficiency();
        }
        return 1.0F;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemstack, int id, int x, int y, int z, EntityLiving entityliving) {
        if (!(entityliving != null && !entityliving.worldObj.isMultiplayerAndNotHost && id == Block.tallgrass.blockID)) {
            return super.onBlockDestroyed(itemstack, id, x, y, z, entityliving);
        }
        if (this.isSilkTouch()) {
            entityliving.worldObj.dropItem(x, y, z, new ItemStack(Item.itemsList[id]));
            return super.onBlockDestroyed(itemstack, id, x, y, z, entityliving);
        }
        if (entityliving.worldObj.rand.nextInt(5) == 0) {
            entityliving.worldObj.dropItem(x, y, z, new ItemStack(Item.seedsWheat));
            return super.onBlockDestroyed(itemstack, id, x, y, z, entityliving);
        }
        return super.onBlockDestroyed(itemstack, id, x, y, z, entityliving);
    }

    //you a hoe
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, double heightPlaced) {
        if(isToolBroken(itemstack)) return false;
        int i1 = world.getBlockId(i, j, k);
        int j1 = world.getBlockId(i, j + 1, k);
        if (!(l != 0 && j1 == 0 && (i1 == Block.grass.blockID || i1 == Block.dirt.blockID || i1 == Block.pathDirt.blockID || i1 == Block.grassRetro.blockID))) {
            return false;
        }
        Block block = Block.farmlandDirt;
        world.playSoundEffect((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), block.stepSound.func_1145_d(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
        if (world.isMultiplayerAndNotHost) return true;
        world.setBlockWithNotify(i, j, k, block.blockID);
        stressTool(1, itemstack, entityplayer);
        return true;
    }
}
