package levistico.bconstruct.tools;

import levistico.bconstruct.parts.EToolPart;
import levistico.bconstruct.gui.texture.TexturedToolBitBreakable;
import levistico.bconstruct.gui.texture.TexturedToolBitReliable;
import levistico.bconstruct.utils.Pair;
import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;

public final class ToolBroadsword extends BTool {

    private static Material[] materialsEffectiveAgainst = {Material.web};

    public final int baseDamageBonus = 4;
    public final int baseDamageMultiplier = 2;
    public ToolBroadsword(int id) {
        super(id, "Broadsword", "sword", materialsEffectiveAgainst, new Pair<>(0,1));
        composition.add(EToolPart.swordBlade);
        texturedParts.add(new TexturedToolBitBreakable(EToolBit.Broadsword_Blade, EToolBit.Broadsword_Blade_Broken));
        composition.add(EToolPart.largeGuard);
        texturedParts.add(new TexturedToolBitReliable(EToolBit.Broadsword_Guard));
        composition.add(EToolPart.rod);
        texturedParts.add(new TexturedToolBitReliable(EToolBit.Broadsword_Handle));
        renderOrder.add(2); //handle
        renderOrder.add(0); //blade
        renderOrder.add(1); //guard
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemstack, int i, int j, int k, int l, EntityLiving player) {
        Block block = Block.blocksList[i];
        if (!(block != null && block.getHardness() > 0.0F)) {
            stressTool(2, itemstack, player);
        }
        return true;
    }

    @Override
    public float getStrVsBlock(ItemStack itemstack, Block block) {
        if(isToolBroken(itemstack)) return 0.1f;
        return block.blockID != Block.cobweb.blockID ? 1.5F : 15.0F;
    }
}
