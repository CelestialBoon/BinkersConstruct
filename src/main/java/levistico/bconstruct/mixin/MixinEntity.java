package levistico.bconstruct.mixin;

import net.minecraft.core.entity.Entity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = Entity.class, remap = false)
public class MixinEntity {

    @Shadow public double yd;
    @Shadow public boolean onGround;
    @Redirect(method = "move (DDD)V", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/Entity;yd:D", opcode = Opcodes.PUTFIELD, ordinal = 1))
    private void bconsctruct_moveInject(Entity instance, double value) {
        if(!instance.onGround || instance.yd < 0) instance.yd = 0;
    }
}
