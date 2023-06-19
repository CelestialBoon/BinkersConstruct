package levistico.bconstruct.gadgets;

import levistico.bconstruct.utils.Utils;
import net.minecraft.src.*;

public class SlimeSling extends Item {
    public SlimeSling(int i) {
        super(i);
//        setMaxDamage(101);
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int sideHit, double heightPlaced) {

        float yaw = entityplayer.rotationYaw * Utils.degToRad;
        float pitch = entityplayer.rotationPitch * Utils.degToRad;

//        float factor = 3.0f * itemstack.getMetadata() / 100f;
        float factor = 2f;
        float pitchSin = MathHelper.sin(pitch);
        entityplayer.motionY += factor * pitchSin;

        float yawFactor = Utils.yawFactor(pitchSin) * factor;
        entityplayer.motionZ -= MathHelper.cos(yaw) * yawFactor;
        entityplayer.motionX += MathHelper.sin(yaw) * yawFactor;
//        itemstack.setMetadata(101);
        return true;
    }
}
