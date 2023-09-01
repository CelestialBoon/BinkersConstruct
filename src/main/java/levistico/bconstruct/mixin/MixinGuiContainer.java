package levistico.bconstruct.mixin;


import levistico.bconstruct.gui.GUIUtils;
import levistico.bconstruct.parts.BToolPart;
import levistico.bconstruct.tools.BTool;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.player.inventory.slot.Slot;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;

@Mixin(value = GuiContainer.class, remap = false)
public abstract class MixinGuiContainer extends GuiScreen {

    /*@Shadow
    private static ItemEntityRenderer itemRenderer;
    @Shadow
    public Container inventorySlots;
    @Shadow
    public int xSize;
    @Shadow
    public int ySize;
    @Shadow
    protected abstract void drawGuiContainerBackgroundLayer(float f);
    @Shadow
    private void drawSlotInventory(Slot slot) {}
    @Shadow
    private boolean getIsMouseOverSlot(Slot slot, int i, int j) {return false;}
    @Shadow
    protected abstract void drawGuiContainerForegroundLayer();
    @Shadow
    public static String formatDescription(String description, int preferredLineLength) {return null;}*/

    @Inject(method = "drawScreen(IIF)V", cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glColor4f (FFFF)V", shift = At.Shift.AFTER))
    private void bconsctruct_drawScreenInject(int x, int y, float renderPartialTicks, CallbackInfo ci, int centerX, int centerY, Slot slot, InventoryPlayer inventoryplayer, ItemStack grabbedItem, ItemStack var9) {
        if (inventoryplayer.getHeldItemStack() != null || slot == null || !slot.hasStack()) return;
        //here we already know the slot is good from that if before the inject
        //boolean showDescription = Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
        ItemStack stack = slot.getStack();
        if (stack.getItem() instanceof BToolPart) {
            String str = GUIUtils.getToolPartTooltip(new StringBuilder(),stack).toString();

            if (str.length() > 0) {
                GUIUtils.tooltip.render(str, x, y, 8, -8);
            }
            GL11.glEnable(GL_DEPTH_TEST);
            ci.cancel();
        } else if (stack.getItem() instanceof BTool) {
            boolean control = Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
            boolean shift = Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);

            String str = GUIUtils.getToolTooltip(new StringBuilder(), stack, control, shift).toString();
            if (str.length() > 0) {
                GUIUtils.tooltip.render(str, x, y, 8, -8);
            }
            GL11.glEnable(GL_DEPTH_TEST);
            ci.cancel();
        }
    }
}