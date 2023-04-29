package levistico.bconstruct.crafting;

import levistico.bconstruct.parts.BToolPart;
import levistico.bconstruct.texture.GraphicsUtils;
import levistico.bconstruct.tools.BTool;
import net.minecraft.shared.Minecraft;
import net.minecraft.src.*;
import net.minecraft.src.command.ChatColor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.List;

public abstract class GUIContainerWithButtons extends GuiContainer {
    static final RenderItem itemRenderer = new RenderItem();
    String invLabel;
    GuiButton selectedButton;
    GuiTextField textBox;

    public GUIContainerWithButtons(String invLabel, ContainerCraftingWithButtons container) {
        super(container);
        selectedButton = getButtons().get(0);
        this.invLabel = invLabel;
    }

    public List<GuiButton> getButtons() {return ((ContainerCraftingWithButtons)inventorySlots).buttons; }


    public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
        this.drawDefaultBackground();
        int centerX = (this.width - this.xSize) / 2;
        int centerY = (this.height - this.ySize) / 2;
        this.drawGuiContainerBackgroundLayer(renderPartialTicks);
        GL11.glPushMatrix();
        GL11.glRotatef(120.0F, 1.0F, 0.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)centerX, (float)centerY, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(32826);
        Slot mouseOverSlot = null;

        for(Slot slot : this.inventorySlots.inventorySlots) {
            if(slot instanceof BSlotActivatable && !((BSlotActivatable)slot).isActive) continue;
            this.drawSlotInventory(slot);
            if (this.getIsMouseOverSlot(slot, mouseX, mouseY)) {
                mouseOverSlot = slot;
                GL11.glDisable(2896);
                GL11.glDisable(2929);
                int j1 = slot.xDisplayPosition;
                int l1 = slot.yDisplayPosition;
                this.drawGradientRect(j1, l1, j1 + 16, l1 + 16, -2130706433, -2130706433);
                GL11.glEnable(2896);
                GL11.glEnable(2929);
            }
        }

        InventoryPlayer inventoryplayer = this.mc.thePlayer.inventory;
        if (inventoryplayer.getHeldItemStack() != null) {
            GL11.glTranslatef(0.0F, 0.0F, 32.0F);
            itemRenderer.renderItemIntoGUI(this.fontRenderer, this.mc.renderEngine, inventoryplayer.getHeldItemStack(), mouseX - centerX - 8, mouseY - centerY - 8, 1.0F);
            itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, inventoryplayer.getHeldItemStack(), mouseX - centerX - 8, mouseY - centerY - 8, 1.0F);
        }

        if(textBox != null) {
            textBox.drawTextBox();
        }

        GL11.glDisable(32826);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        this.drawGuiContainerForegroundLayer();
        GL11.glPopMatrix();

        for(GuiButton button : this.controlList) {
            button.drawButton(this.mc, mouseX, mouseY);
        }

        for(GuiButton button : getButtons()) {
            button.drawButton(mc, mouseX, mouseY);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (inventoryplayer.getHeldItemStack() == null && mouseOverSlot != null && mouseOverSlot.hasStack()) {
            StringTranslate trans = StringTranslate.getInstance();
            StringBuilder text = new StringBuilder();
            boolean multiLine = false;
            boolean control = Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
            boolean shift = Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
            boolean showDescription = control;
            if (mouseOverSlot.discovered) {
                boolean isCrafting = mouseOverSlot instanceof SlotCrafting;
                if (isCrafting) {
                    showDescription = false;
                    if (this.mc.gameSettings.swapCraftingButtons.value) {
                        boolean a = shift;
                        shift = control;
                        control = a;
                    }
                }
                ItemStack stack = mouseOverSlot.getStack();
                Item item = stack.getItem();
                if(item instanceof BToolPart) {
                    text = GUIUtils.getToolPartTooltip(text, stack);
                } else if(item instanceof BTool) {
                    text = GUIUtils.getToolTooltip(text, stack, control, shift);
                } else {
                    String itemName = trans.translateKey(stack.getItemName() + ".name");
                    String itemNick = stack.getItem().getItemNickname(stack);
                    if (itemNick != null && itemNick.length() > 0 && stack.tag.getBoolean("overrideName")) {
                        itemName = itemNick;
                    }

                    if (stack.tag.getBoolean("overrideColor")) {
                        text.append(ChatColor.get(stack.getItem().getItemNameColor(stack)));
                    }

                    text.append(itemName);
                    boolean debug = (Boolean) this.mc.gameSettings.showDebugScreen.value;
                    if (debug) {
                        text.append(" #" + stack.itemID + ":" + stack.getMetadata());
                    }

                    if (debug) {
                        multiLine = true;
                        text.append('\n').append(ChatColor.lightGray + stack.getItemName());
                    }

                    if (stack.isItemStackDamageable() && !showDescription && mouseOverSlot.discovered && (Boolean) this.mc.gameSettings.showItemDurability.value) {
                        multiLine = true;
                        int durability = stack.getMaxDamage();
                        int remainingUses = durability - stack.getMetadata();
                        text.append('\n').append(ChatColor.lightGray + "" + remainingUses + " / " + durability);
                    }

                    if (showDescription) {
                        multiLine = true;
                        text.append('\n').append(formatDescription(trans.translateKey(stack.getItemName() + ".desc"), 16));
                    }

                    if (isCrafting) {
                        if (shift && !control) {
                            text.append('\n').append(ChatColor.lightGray).append("Craft Stack");
                        }

                        if (control && !shift) {
                            text.append('\n').append(ChatColor.lightGray).append("Craft Once");
                        }

                        if (control && shift) {
                            text.append('\n').append(ChatColor.lightGray).append("Craft All");
                        }
                    }
                }
            } else {
                text.append("???");
                if (control) {
                    multiLine = true;
                    text.append("\n").append(trans.translateKey("item.unknown.desc"));
                }
            }



            String str = text.toString();
            if (str.length() > 0) {
                this.drawTooltip(str, mouseX, mouseY, 8, -8, multiLine);
            }
        }

        GL11.glEnable(2929);
    }

    @Override
    public void mouseClicked(int x, int y, int button) {
        super.mouseClicked(x, y, button);
        if(textBox != null) {
            int centerX = (this.width - this.xSize) / 2;
            int centerY = (this.height - this.ySize) / 2;
            textBox.mouseClicked(x - centerX, y - centerY, button);
        }
        if (button == 0) {
            for(GuiButton guibutton : getButtons()) {
                if (guibutton.mousePressed(this.mc, x, y)) {
                    this.selectedButton = guibutton;
                    this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    if (guibutton.listener != null) {
                        guibutton.listener.listen(guibutton);
                    } else {
                        this.actionPerformed(guibutton);
                    }
                }
            }
        }
    }
    @Override
    public void keyTyped(char c, int i) {
        if (i == 1 || i == 14) {
            this.mc.thePlayer.closeScreen();
        } else if(textBox != null && textBox.isEnabled && textBox.isFocused) {
            textBox.textboxKeyTyped(c, i);
        } else if (this.mc.gameSettings.keyInventory.isKey(i)) {
            this.mc.thePlayer.closeScreen();
        }
    }

    void drawSlotInventory(Slot slot) {
//        if (slot instanceof BSlotActivatable && !((BSlotActivatable)slot).isActive) return;
        int i = slot.xDisplayPosition;
        int j = slot.yDisplayPosition;
        this.mc.renderEngine.bindTexture(GraphicsUtils.GUI_ICONS_INDEX);
        if(!(slot instanceof BSlotCrafting))
            GUIUtils.drawGUITexture(i-1, j-1, zLevel, 8, 12);
        ItemStack itemstack = slot.getStack();
        if (itemstack == null) {
            int k = slot.getBackgroundIconIndex();
            if (k >= 0) {
                GL11.glDisable(2896);
                this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture("/gui/items.png"));
                this.drawTexturedModalRect(i, j, k % Minecraft.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthItems, k / Minecraft.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthItems, 16, 16, TextureFX.tileWidthItems, 1.0F / (float)(Minecraft.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthItems));
                GL11.glEnable(2896);
                return;
            }
        }

        itemRenderer.renderItemIntoGUI(this.fontRenderer, this.mc.renderEngine, itemstack, i, j, slot.discovered ? 1.0F : 0.0F, 1.0F);
        itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, itemstack, i, j, slot.discovered);
    }

    boolean getIsMouseOverSlot(Slot slot, int i, int j) {
        if (slot instanceof BSlotActivatable && !((BSlotActivatable)slot).isActive) return false;
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        i -= k;
        j -= l;
        return i >= slot.xDisplayPosition - 1 && i < slot.xDisplayPosition + 16 + 1 && j >= slot.yDisplayPosition - 1 && j < slot.yDisplayPosition + 16 + 1;
    }

    @Override
    protected void actionPerformed(GuiButton guibutton) {
    }


    public void onGuiClosed() {
        super.onGuiClosed();
        this.inventorySlots.onCraftGuiClosed(this.mc.thePlayer);
    }

    protected void drawGuiContainerForegroundLayer() {
        this.fontRenderer.drawString(invLabel, 28, 6, 4210752);
        this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float f) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(GraphicsUtils.GUI_CRAFTING_INDEX);
        int j = (this.width - this.xSize) / 2;
        int k = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
    }
}
