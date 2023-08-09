package levistico.bconstruct.mixin;

import levistico.bconstruct.crafting.CraftingTileEntity;
import net.minecraft.core.block.entity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = TileEntity.class, remap = false)
public class MixinTileEntity {
    @Shadow
    private static void addMapping(Class class1, String s) {}

    static {
        addMapping(CraftingTileEntity.class, "Crafting Table");
    }
}
