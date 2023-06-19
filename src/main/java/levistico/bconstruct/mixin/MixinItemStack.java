package levistico.bconstruct.mixin;

import levistico.bconstruct.tools.BTool;
import levistico.bconstruct.tools.ToolStack;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemStack.class, remap = false)
public class MixinItemStack {

    @Shadow()
    public int itemID;

    @Inject(method = "getDamageVsEntity (Lnet/minecraft/src/Entity;)I", cancellable = true, at = @At("HEAD"))
    public void bconsctruct_getDamageVsEntityInject(Entity entity, CallbackInfoReturnable<Integer> cir) {
        Item item = Item.itemsList[this.itemID];
        if(item instanceof BTool) cir.setReturnValue(ToolStack.getDamageVsEntity((ItemStack)(Object)this, (BTool)item, entity));
    }

    @Inject(method = "canHarvestBlock (Lnet/minecraft/src/Block;)Z", cancellable = true, at = @At("HEAD"))
    public void bconsctruct_canHarvestBlockInject(Block block, CallbackInfoReturnable<Boolean> cir) {
        Item item = Item.itemsList[this.itemID];
        if(item instanceof BTool) cir.setReturnValue(ToolStack.canHarvestBlock((ItemStack)(Object)this, (BTool) item, block));
    }

    @Inject(method = "getMaxDamage ()I", cancellable = true, at = @At("HEAD"))
    public void bconsctruct_getMaxDamageInject(CallbackInfoReturnable<Integer> cir) {
        Item item = Item.itemsList[this.itemID];
        if(item instanceof BTool) cir.setReturnValue(ToolStack.getMaxDurability((ItemStack)(Object)this));
    }
}
