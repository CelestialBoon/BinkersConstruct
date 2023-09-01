package levistico.bconstruct.gadgets;

import levistico.bconstruct.utils.Utils;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;

public class SlimeSling extends Item {
    public SlimeSling(int i) {
        super(i);
//        setMaxDamage(101);
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityPlayer, World world, int x, int y, int z, int sideHit, double heightPlaced) {

        float yaw = entityPlayer.limbYaw * Utils.degToRad;
        float pitch = entityPlayer.cameraPitch * Utils.degToRad;

//        float factor = 3.0f * itemstack.getMetadata() / 100f;
        float factor = 2f;
        float pitchSin = MathHelper.sin(pitch);
        entityPlayer.yd += factor * pitchSin;

        float yawFactor = Utils.yawFactor(pitchSin) * factor;
        entityPlayer.zd -= MathHelper.cos(yaw) * yawFactor;
        entityPlayer.xd += MathHelper.sin(yaw) * yawFactor;
//        itemstack.setMetadata(101);
        return true;
    }
}
