package levistico.bconstruct.mixin;

import levistico.bconstruct.gui.GUIUtils;
import levistico.bconstruct.materials.BToolMaterial;
import levistico.bconstruct.parts.BToolPart;
import levistico.bconstruct.tools.BTool;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTooltip;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.slot.Slot;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GuiTooltip.class, remap = false)
public class MixinGuiTooltip extends Gui {
    @Inject(method="getTooltipText (Lnet/minecraft/core/item/ItemStack;ZLnet/minecraft/core/player/inventory/slot/Slot;)Ljava/lang/String;", cancellable = true, at=@At(value="INVOKE", target="Ljava/lang/StringBuilder; append (Ljava/lang/Object;)Ljava/lang/StringBuilder;", ordinal = 0))
    private void BConstruct_getTooltipTextInject(ItemStack itemStack, boolean showDescription, Slot slot, CallbackInfoReturnable<String> cir) {
        if(itemStack==null || itemStack.getItem()==null)
            return;
        Item item = itemStack.getItem();
        boolean ctrlPressed = Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
        boolean shiftPressed = Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
        String result = "";
        if (item instanceof BToolPart){
            result = GUIUtils.getToolPartTooltip(new StringBuilder(),itemStack).toString();
        } else if (item instanceof BTool) {
            result = GUIUtils.getToolTooltip(new StringBuilder(), itemStack, ctrlPressed, shiftPressed).toString();
        } else return;
        cir.setReturnValue(result);
    }
}
